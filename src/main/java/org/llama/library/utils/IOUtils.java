/**
 * 
 */
package org.llama.library.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import org.llama.library.log.Log;

/**
 * io工具扩展，commons-io已有的方法不在此工具中
 * 
 * @author tonny
 * @date 2012-8-16
 * @version 1.0.0
 */
public class IOUtils {

	/**
	 * 拷贝指定流的部分数据，拷贝完没关闭流
	 * 
	 * @param inputStream 输入流
	 * @param outputStream 输出流
	 * @param off 略过长度
	 * @param length 拷贝长度，如果总长度不足，以实际长度为准
	 * @throws IOException 读写异常
	 */
	public static void copy(InputStream inputStream, OutputStream outputStream, long off, int length)
			throws IOException {
		byte[] buffer = new byte[1024 * 4];
		int n = 0;
		inputStream.skip(off);
		int count = 0;
		while (-1 != (n = inputStream.read(buffer))) {
			outputStream.write(buffer, 0, Math.min(n, length));
			count += Math.min(n, length);
			if (count >= length) {
				break;
			}
		}
	}

	/**
	 * 将可序列化的对象写到文件
	 * 
	 * @param object 可序列化的对象
	 * @param file 写入的文件
	 * @throws IOException
	 */
	public static boolean write(Object object, File file) throws IOException {
		FileOutputStream out = null;
		ObjectOutputStream objectOutputStream = null;
		try {
			out = new FileOutputStream(file);
			objectOutputStream = new ObjectOutputStream(out);
			objectOutputStream.writeObject(object);
			objectOutputStream.flush();
			objectOutputStream.close();
			return true;
		} finally {
			if (objectOutputStream != null) {
				try {
					objectOutputStream.close();
				} catch (IOException e) {
					Log.error(e);
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					Log.error(e);
				}
			}
		}
	}

	/**
	 * 从文件中读取对象
	 * 
	 * @param file 待读取的文件
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static Object read(File file) throws IOException, ClassNotFoundException {
		Object temp = null;
		FileInputStream in = null;
		ObjectInputStream objIn = null;
		try {
			in = new FileInputStream(file);
			objIn = new ObjectInputStream(in);
			temp = objIn.readObject();
			objIn.close();
		} finally {
			if (objIn != null) {
				try {
					objIn.close();
				} catch (IOException e) {
					Log.error(e);
				}
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					Log.error(e);
				}
			}
		}
		return temp;
	}

	/**
	 * 从文件中读取对象
	 * 
	 * @param file 待读取的文件
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static Object read(byte[] bytes) throws IOException, ClassNotFoundException {
		Object temp = null;
		InputStream in = new ByteArrayInputStream(bytes);
		ObjectInputStream objIn = null;
		try {
			objIn = new ObjectInputStream(in);
			temp = objIn.readObject();
			objIn.close();
		} finally {
			if (objIn != null) {
				try {
					objIn.close();
				} catch (IOException e) {
					Log.error(e);
				}
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					Log.error(e);
				}
			}
		}
		return temp;
	}

	/**
	 * 将可序列化的对象写到文件
	 * 
	 * @param object 可序列化的对象
	 * @param file 写入的文件
	 * @throws IOException
	 */
	public static byte[] write(Object object) throws IOException {
		ByteArrayOutputStream out = null;
		ObjectOutputStream objectOutputStream = null;
		try {
			out = new ByteArrayOutputStream();
			objectOutputStream = new ObjectOutputStream(out);
			objectOutputStream.writeObject(object);
			objectOutputStream.flush();
			objectOutputStream.close();
			return out.toByteArray();
		} finally {
			if (objectOutputStream != null) {
				try {
					objectOutputStream.close();
				} catch (IOException e) {
					Log.error(e);
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					Log.error(e);
				}
			}
		}
	}

}
