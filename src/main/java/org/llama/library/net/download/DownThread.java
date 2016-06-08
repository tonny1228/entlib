/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.llama.library.net.download;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.net.URLConnection;

import org.llama.library.log.LogFactory;
import org.llama.library.log.Logger;
import org.llama.library.utils.Formatter;

/**
 * 下载线程
 * 
 * @author tonny
 */
public class DownThread extends Thread implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3317849201046281359L;
	private static final int BUFFER_SIZE = 1024 * 4;

	transient private Logger log;

	transient private DownTask dlTask; // 下载任务
	private int id; // 线程编号
	private long startPos; // 开始下载位置
	private long endPos; // 截止下载位置
	private long curPos; // 当前位置
	private long readByte; // 已读数据
	private boolean finished; // 是否结束
	private boolean isNewThread; // 是否新线程

	public DownThread(DownTask dlTask, int id, long startPos, long endPos) {
		this.dlTask = dlTask;
		this.id = id;
		this.curPos = this.startPos = startPos;
		this.endPos = endPos;
		finished = false;
		readByte = 0;
	}

	public void run() {
		log = LogFactory.getLogger(DownThread.class);
		log.info("线程{0}启动......", id);
		BufferedInputStream bis = null;
		RandomAccessFile fos = null;
		byte[] buf = new byte[BUFFER_SIZE];
		URLConnection con = null;
		try {
			con = dlTask.getUrl().openConnection();
			con.setAllowUserInteraction(true);
			if (isNewThread) {
				con.setRequestProperty("Range", "bytes=" + startPos + "-" + endPos);
				fos = new RandomAccessFile(dlTask.getFile(), "rw");
				fos.seek(startPos);
				log.debug("线程{0}准备下载{1}-{2}/{3}", id, startPos, endPos, dlTask.getContentLen());
			} else {
				con.setRequestProperty("Range", "bytes=" + curPos + "-" + endPos);
				fos = new RandomAccessFile(dlTask.getFile(), "rw");
				fos.seek(curPos);
				log.debug("线程{0}准备下载{1}-{2}/{3}", id, curPos, endPos, dlTask.getContentLen());
			}
			bis = new BufferedInputStream(con.getInputStream());
			while (curPos < endPos) {
				int len = bis.read(buf, 0, BUFFER_SIZE);
				if (len == -1) {
					break;
				}
				fos.write(buf, 0, len);
				curPos += len;
				if (curPos > endPos) {
					readByte += len - (curPos - endPos) + 1; // 获取正确读取的字节数
				} else {
					readByte += len;
				}
			}
			log.info("线程{0}已经下载完毕, 共{1}", id, Formatter.formatByte(readByte));
			this.finished = true;
			bis.close();
			fos.close();
		} catch (IOException ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}

	public DownTask getBelongDLIns() {
		return dlTask;
	}

	public void setBelongDLIns(DownTask belongDLIns) {
		this.dlTask = belongDLIns;
	}

	public boolean isFinished() {
		return finished;
	}

	public long getReadByte() {
		return readByte;
	}

	public void setDlTask(DownTask dlTask) {
		this.dlTask = dlTask;
	}
}
