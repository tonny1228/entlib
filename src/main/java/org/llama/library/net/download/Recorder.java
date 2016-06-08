/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.llama.library.net.download;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * 下载进度管理器
 * 
 * @author tonny
 */
public class Recorder {

	private DownTask dlTask;

	public Recorder(DownTask dlTask) {
		this.dlTask = dlTask;
	}

	/**
	 * 记录下载状态
	 */
	public void record() {
		ObjectOutputStream out = null;
		try {
			File file = new File(dlTask.getFile().getAbsolutePath().replace(DownTask.FILE_POSTFIX, "") + ".tsk");
			if (!file.getParentFile().exists())
				file.getParentFile().mkdirs();
			out = new ObjectOutputStream(new FileOutputStream(file));
			out.writeObject(dlTask);
		} catch (IOException ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		} finally {
			try {
				if (out != null)
					out.close();
			} catch (IOException ex) {
				ex.printStackTrace();
				throw new RuntimeException(ex);
			}
		}

	}

	public void remove() {
		File file = new File(dlTask.getFile().getAbsolutePath().replace(DownTask.FILE_POSTFIX, "") + ".tsk");
		file.delete();
	}
}
