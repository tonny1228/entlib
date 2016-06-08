package org.llama.library.cryptography;

/**
 * 字节数组编码解码器
 * 
 * @author tonny
 * 
 */
public interface Coder {
	/**
	 * 将字节数组编码为字符串
	 * 
	 * @param bytes 字节数组
	 * @return 编码后的字符串
	 */
	String encode(byte[] bytes);

	/**
	 * 将字符串解码为字节数组
	 * 
	 * @param str 编码后的字符串
	 * @return 原字节数组
	 */
	byte[] decode(String str) throws CryptographyException;
}
