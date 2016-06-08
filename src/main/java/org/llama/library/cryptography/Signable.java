package org.llama.library.cryptography;

/**
 * 数组签名算法工具
 * 
 * @author tonny
 * 
 */
public interface Signable {
	/**
	 * 将数据进行数字签名
	 * 
	 * @param source 待签名数据
	 * @return 签名后的数据
	 * @throws CryptographyException 签名异常
	 */
	byte[] sign(byte[] source) throws CryptographyException;

	/**
	 * 将数据进行数字签名
	 * 
	 * @param source 待签名数据
	 * @return 签名后的数据，通过编码工具编码
	 * @throws CryptographyException 签名异常
	 */
	String sign(String source) throws CryptographyException;

	/**
	 * 验证签名后的数据
	 * 
	 * @param expect 原始数据
	 * @param sign 签名后的数据
	 * @return 是否一致
	 * @throws CryptographyException 签名验证异常
	 */
	boolean verify(byte[] expect, byte[] sign) throws CryptographyException;

	/**
	 * 验证签名后的数据
	 * 
	 * @param expect 原始数据
	 * @param sign 签名后的数据
	 * @return 是否一致
	 * @throws CryptographyException 签名验证异常
	 */
	boolean verify(String expect, String sign) throws CryptographyException;

	/**
	 * 设置编码解码器，只有设置了编码解码器，才能直接将数据加密输出为字符串
	 * 
	 * @param coder 编码解码器，支持<code>Hex</code>和Base64
	 */
	void setCoder(Coder coder);

}
