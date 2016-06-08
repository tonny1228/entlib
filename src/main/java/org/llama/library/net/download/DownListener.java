/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.llama.library.net.download;

import java.math.BigDecimal;

import org.llama.library.log.LogFactory;
import org.llama.library.log.Logger;

/**
 * 下载监听器，存储下载进度
 * 
 * @author tonny
 */
public class DownListener extends Thread {

	private Logger log = LogFactory.getLogger(DownListener.class);
	private DownTask dlTask;
	private Recorder recoder;

	DownListener(DownTask dlTask) {
		this.dlTask = dlTask;
		this.recoder = new Recorder(dlTask);
	}

	@Override
	public void run() {
		BigDecimal completeTot = null;
		long start = System.currentTimeMillis();
		long end = start;

		while (!dlTask.isComplete()) {
			int percent = dlTask.getCurPercent();
			completeTot = new BigDecimal(dlTask.getCompletedTot());

			end = System.currentTimeMillis();
			if (end - start > 1000) {
				BigDecimal pos = new BigDecimal(((end - start) / 1000) * 1024);
				log.info("下载速度 :{0} k/s, {1}% 已完成.", completeTot.divide(pos, 0, BigDecimal.ROUND_HALF_EVEN), percent);
			}
			recoder.record();
			try {
				sleep(3000);
			} catch (InterruptedException ex) {
				throw new RuntimeException(ex);
			}

		}
		int costTime = +(int) ((System.currentTimeMillis() - start) / 1000);
		dlTask.setCostTime(costTime);
		String time = DownloadUtils.changeSecToHMS(costTime);
		dlTask.rename();
		recoder.remove();
		log.info("Download finished in {0}. ", time);
	}
}
