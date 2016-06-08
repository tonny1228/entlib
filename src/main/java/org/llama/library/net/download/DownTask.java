/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.llama.library.net.download;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.llama.library.log.LogFactory;
import org.llama.library.log.Logger;

/**
 * 下载任务。<br/>
 * 本类对应多个下载任务，每个下载任务包含多个下载线程，默认最多包含十个下载线程
 * 
 * @author RexCJ
 */
public class DownTask extends Thread implements Serializable {

	private static final long serialVersionUID = 126148287461276024L;
	transient private Logger log;

	/**
	 * 下载临时文件后缀，下载完成后将自动被删除
	 */
	public final static String FILE_POSTFIX = ".tmp";
	private URL url; // 下载地址
	private File file;
	private String folder;
	private String filename;
	private int id;
	private int threadQut; // 下载最大线程数量，用户可定制
	private int contentLen; // 下载文件长度
	private long completedTot; // 当前下载完成总数
	private int costTime; // 下载时间计数，记录下载花费的时间
	private int curPercent; // 下载百分比
	private boolean isNewTask = true; // 是否新建下载任务，可能是断点续传任务

	private DownThread[] dlThreads; // 保存当前任务的线程

	transient private DownListener listener; // 当前任务的监听器，用于即时获取相关下载信息

	public DownTask(int threadQut, String url, String folder) {
		this.threadQut = threadQut;
		this.folder = folder;
		try {
			this.url = new URL(url);
		} catch (MalformedURLException ex) {
			throw new RuntimeException(ex);
		}
	}

	@Override
	public void run() {
		log = LogFactory.getLogger(DownTask.class);
		if (isNewTask) {
			newTask();
		} else {
			resumeTask();
		}
	}

	/**
	 * 恢复任务时被调用，用于断点续传时恢复各个线程
	 */
	private void resumeTask() {
		listener = new DownListener(this);
		for (int i = 0; i < threadQut; i++) {
			dlThreads[i].setDlTask(this);
			DownloadEngine.pool.execute(dlThreads[i]);
		}
		DownloadEngine.pool.execute(listener);
	}

	/**
	 * 新建任务时被调用，通过连接资源获取资源相关信息，并根据具体长度创建线程块， 线程创建完毕后，即刻通过线程池进行调用
	 * 
	 * @throws RuntimeException
	 */
	private void newTask() throws RuntimeException {
		try {
			isNewTask = false;
			URLConnection con = url.openConnection();
			Map<String, List<String>> map = con.getHeaderFields();
			Set<String> set = map.keySet();
			for (String key : set) {
				log.info("{0} {1}", StringUtils.stripToEmpty(key), map.get(key));
			}
			contentLen = con.getContentLength();
			getFilename(con);
			autoGenerateThreads();
			createTempFile();
			long subLen = contentLen / dlThreads.length;

			for (int i = 0; i < dlThreads.length - 1; i++) {
				DownThread thread = new DownThread(this, i + 1, subLen * i, subLen * (i + 1) - 1);
				dlThreads[i] = thread;
				DownloadEngine.pool.execute(dlThreads[i]);
			}

			DownThread thread = new DownThread(this, dlThreads.length, subLen * (dlThreads.length - 1), contentLen);
			dlThreads[dlThreads.length - 1] = thread;
			DownloadEngine.pool.execute(thread);

			this.listener = new DownListener(this);
			DownloadEngine.pool.execute(listener);
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}

	/**
	 * 获取文件名
	 * 
	 * @param con
	 */
	private void getFilename(URLConnection con) {
		if (StringUtils.isEmpty(filename)) {
			String disp = con.getHeaderField("Content-disposition");
			if (StringUtils.isNotEmpty(disp)) {
				filename = StringUtils.substringBetween(disp, "filename=\"", "\"");
				if (filename != null) {
					try {
						filename = new String(filename.getBytes("ISO-8859-1"), "GBK");
					} catch (UnsupportedEncodingException e) {
					}
					return;
				}
			}
			filename = StringUtils.substringAfterLast(url.getPath(), "/");

			log.info(filename);
		}
	}

	/**
	 * 创建临时文件，不能覆盖已有文件
	 * 
	 * @throws IOException
	 */
	private void createTempFile() throws IOException {
		file = new File(folder + File.separator + filename + FILE_POSTFIX);
		log.info("创建文件{0}", file);
		int fileCnt = 1;
		while (file.exists()) {
			file = new File(folder + File.separator + filename + "(" + fileCnt + ")" + FILE_POSTFIX);
			fileCnt++;
		}

		long freespace = file.getFreeSpace();
		if (contentLen < freespace) {
			log.error("磁盘空间不够");
			return;
		}
		file.createNewFile();
	}

	/**
	 * 智能分析线程数，长度未知或小于40k采用单线程 每个线程最小下载20k数据，否则减少线程数
	 */
	private void autoGenerateThreads() {
		if (contentLen <= 0) {
			log.warn("无法获取资源长度，将采用单线程下载");
			dlThreads = new DownThread[1];
		} else if (contentLen < 40 * 1024) {
			log.warn("资源过小，将采用单线程下载");
			dlThreads = new DownThread[1];
		} else if (contentLen / threadQut < 20 * 1024) {
			int c = Math.min(threadQut, contentLen / 20 * 1024);
			log.info("采用{0}线程下载", c);
			dlThreads = new DownThread[c];
		} else {
			log.info("采用{0}线程下载", threadQut);
			dlThreads = new DownThread[threadQut];
		}
	}

	/**
	 * 计算当前已经完成的长度并返回下载百分比的字符串表示，目前百分比均为整数
	 * 
	 * @return
	 */
	public int getCurPercent() {
		completeTot();
		curPercent = new BigDecimal(completedTot)
				.divide(new BigDecimal(this.contentLen), 2, BigDecimal.ROUND_HALF_EVEN)
				.divide(new BigDecimal(0.01), 0, BigDecimal.ROUND_HALF_EVEN).intValue();
		return curPercent;
	}

	/**
	 * 当前下载字节数
	 */
	private void completeTot() {
		completedTot = 0;
		for (DownThread t : dlThreads) {
			completedTot += t.getReadByte();
		}
	}

	/**
	 * 判断全部线程是否已经下载完成，如果完成则返回true，相反则返回false
	 * 
	 * @return
	 */
	public boolean isComplete() {
		boolean completed = true;
		for (DownThread t : dlThreads) {
			completed = t.isFinished();
			if (!completed) {
				break;
			}
		}
		return completed;
	}

	public void rename() {
		File f = new File(folder + File.separator + filename);
		int i = 1;
		while (f.exists()) {
			f = new File(folder + File.separator + filename.replace(".", "(" + i++ + ")."));
		}
		this.file.renameTo(f);
	}

	public DownThread[] getDlThreads() {
		return dlThreads;
	}

	public void setDlThreads(DownThread[] dlThreads) {
		this.dlThreads = dlThreads;
	}

	public int getTaskId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public File getFile() {
		return file;
	}

	public URL getUrl() {
		return url;
	}

	public int getContentLen() {
		return contentLen;
	}

	public String getFilename() {
		return folder;
	}

	public int getThreadQut() {
		return threadQut;
	}

	public long getCompletedTot() {
		return completedTot;
	}

	public int getCostTime() {
		return costTime;
	}

	public void setCostTime(int costTime) {
		this.costTime = costTime;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

}
