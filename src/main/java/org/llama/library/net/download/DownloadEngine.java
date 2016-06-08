/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.llama.library.net.download;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.llama.library.log.LogFactory;
import org.llama.library.log.Logger;

/**
 * 基于http协议的下载管理器
 * 
 * @author tonny
 * @date 2012-8-16
 * @version 1.0.0
 */
public class DownloadEngine {
	public static ExecutorService pool = Executors.newCachedThreadPool();
	private Logger log = LogFactory.getLogger(DownloadEngine.class);

	/**
	 * 创建任务
	 * 
	 * @param threadQut 线程数
	 * @param url 下载地址
	 * @param dir 储存目录
	 * @param filename 文件名
	 */
	public void createTask(int threadQut, String url, String dir, String filename) {
		DownTask task = new DownTask(threadQut, url, dir);
		task.setFilename(filename);
		pool.execute(task);
	}

	/**
	 * 继续任务
	 * 
	 * @param threadQut 线程数
	 * @param url 地址
	 * @param path 路径
	 * @param filename 文件名
	 */
	public void resumeTask(File tsk) {
		ObjectInputStream in = null;
		try {
			in = new ObjectInputStream(new FileInputStream(tsk));
			DownTask task = (DownTask) in.readObject();
			pool.execute(task);
		} catch (ClassNotFoundException ex) {
			log.info(null, ex);
		} catch (IOException ex) {
			log.info(null, ex);
		} finally {
			try {
				in.close();
			} catch (IOException ex) {
				log.info(null, ex);
			}
		}
	}

	public static void main(String args[]) {
		DownloadEngine engine = new DownloadEngine();
		engine.createTask(
				5,
				"http://192.168.0.168:8080/download?ticket=c34df290-215e-4aab-a0bd-c6cb03a8c22a&sessionId=1E3EB76A22FF5032C561DC641C1AD30F",
				"g:\\", null);
		// engine.resumeTask(new File("g:\\Kettle-3.0.2.zip.tsk"));
		// engine.createTask(10,
		// "http://218.61.39.43/videos2/comic/20120504/3e1993b8bffab5353a55f71d3376becf.f4v?key=607e38b9c9b55286",
		// "g:\\", null);
		// engine.createTask(5,
		// "http://img1.cache.netease.com/cnews/img/homenews0090424/./img_6.jpg",
		// "g:\\", null);
		// engine.createTask(10,
		// "http://img1.cache.netease.com/cnews/img/homenews0090424/./img_6.jpg",
		// "g:\\", null);
		// engine.resumeTask(10,
		// "http://ardownload.adobe.com/pub/adobe/reader/win/8.x/8.1.2/chs/AdbeRdr812_zh_CN.exe",
		// "D:", "AdbeRdr812_zh_CN.exe");
	}
}
