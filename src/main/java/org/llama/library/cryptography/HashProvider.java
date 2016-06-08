package org.llama.library.cryptography;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 加密工具，将指定的数据加密通过哈希函数加密方式进行加密
 * 
 * @author tonny
 * 
 */

public class HashProvider implements Encryptable {

	/**
	 * 算法名称
	 */
	private String algorithm;

	/**
	 * 字节数组编码器
	 */
	private Coder coder;

	public HashProvider(String algorithm) {
		super();
		this.algorithm = algorithm;
	}

	/**
	 * 将字节数组加密，输出字节数组。可通过hex或base64转换为字符串
	 * 
	 * @param source 待加密的数据
	 * @return 加密后的数据
	 * @throws CryptographyException 加密异常
	 */
	public byte[] encrypt(byte[] source) throws CryptographyException {
		try {
			MessageDigest digest = getDigest();
			return digest.digest(source);
		} catch (NoSuchAlgorithmException e) {
			throw new CryptographyException(e);
		}
	}

	/**
	 * 获取编码算法
	 * 
	 * @return 算法实例
	 * @throws NoSuchAlgorithmException 算法不存在异常
	 */
	protected synchronized MessageDigest getDigest() throws NoSuchAlgorithmException {
		return MessageDigest.getInstance(algorithm);
	}

	/**
	 * 直接将字符串加密，输出为Hex或base64编码的字符串，编码工具在setCoder中指定
	 * 
	 * @param source 待加密的字符串
	 * @return Hex或base64编码后的加密字符串
	 * @throws CryptographyException 加密异常
	 */
	public String encrypt(String source) throws CryptographyException {
		return coder.encode(encrypt(source.getBytes()));
	}

	/**
	 * 设置编码解码器，只有设置了编码解码器，才能直接将数据加密输出为字符串
	 * 
	 * @param coder 编码解码器，支持<code>Hex</code>和Base64
	 */
	public void setCoder(Coder coder) {
		this.coder = coder;
	}

}
