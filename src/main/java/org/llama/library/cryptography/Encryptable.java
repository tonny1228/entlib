package org.llama.library.cryptography;

/**
 * 加密工具，将指定的数据加密通过加密方式进行加密
 * 
 * @author tonny
 * 
 */
public interface Encryptable {
	/**
	 * 将字节数组加密，输出字节数组。可通过hex或base64转换为字符串
	 * 
	 * @param source 待加密的数据
	 * @return 加密后的数据
	 * @throws CryptographyException 加密异常
	 */
	byte[] encrypt(byte[] source) throws CryptographyException;

	/**
	 * 设置编码解码器，只有设置了编码解码器，才能直接将数据加密输出为字符串
	 * 
	 * @param coder 编码解码器，支持<code>Hex</code>和Base64
	 */
	void setCoder(Coder coder);

	/**
	 * 直接将字符串加密，输出为Hex或base64编码的字符串，编码工具在setCoder中指定
	 * 
	 * @param source 待加密的字符串
	 * @return Hex或base64编码后的加密字符串
	 * @throws CryptographyException 加密异常
	 */
	String encrypt(String source) throws CryptographyException;
}
