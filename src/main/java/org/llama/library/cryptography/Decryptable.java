package org.llama.library.cryptography;

/**
 * 解密工具，将指定的数据通过解密算法进行解密
 * 
 * @author tonny
 * 
 */
public interface Decryptable {
	/**
	 * 将字节数组解密，输出原始字节数组。
	 * 
	 * @param source 待解密的数据
	 * @return 解密后的数据
	 * @throws CryptographyException 解密异常
	 */
	byte[] decrypt(byte[] source) throws CryptographyException;

	/**
	 * 设置编码解码器，只有设置了编码解码器，才能直接将字符串解密
	 * 
	 * @param coder 编码解码器，支持<code>Hex</code>和Base64
	 */
	void setCoder(Coder coder);

	/**
	 * 直接将字符串通过Hex或base64解码的字符串解密，解码工具在setCoder中指定
	 * 
	 * @param source 待解密的Hex或base64编码的字符串
	 * @return 原始字符串
	 * @throws CryptographyException 解密异常
	 */
	String decrypt(String source) throws CryptographyException;
}
