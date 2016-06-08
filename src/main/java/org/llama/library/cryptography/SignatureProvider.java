package org.llama.library.cryptography;

import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;

/**
 * 数组签名算法工具
 * 
 * @author tonny
 * 
 */
public class SignatureProvider implements Signable {
	private Key key;

	private String algorithm;

	private Coder coder;

	public SignatureProvider(String algorithm, Key key) {
		this.key = key;
		this.algorithm = algorithm;
	}

	/**
	 * 将数据进行数字签名
	 * 
	 * @param source 待签名数据
	 * @return 签名后的数据
	 * @throws CryptographyException 签名异常
	 */
	public byte[] sign(byte[] source) throws CryptographyException {
		try {
			Signature sig = Signature.getInstance(algorithm);
			sig.initSign((PrivateKey) key);
			sig.update(source);
			return sig.sign();
		} catch (Exception e) {
			throw new CryptographyException(e);
		}
	}

	/**
	 * 验证签名后的数据
	 * 
	 * @param expect 原始数据
	 * @param sign 签名后的数据
	 * @return 是否一致
	 * @throws CryptographyException 签名验证异常
	 */
	public boolean verify(byte[] expect, byte[] sign) throws CryptographyException {
		try {
			Signature validator = Signature.getInstance(algorithm);
			validator.initVerify((PublicKey) key);
			validator.update(expect);
			return validator.verify(sign);
		} catch (Exception e) {
			throw new CryptographyException(e);
		}
	}

	/**
	 * 设置编码解码器，只有设置了编码解码器，才能直接将数据加密输出为字符串
	 * 
	 * @param coder 编码解码器，支持<code>Hex</code>和Base64
	 */
	public void setCoder(Coder coder) {
		this.coder = coder;
	}

	/**
	 * 将数据进行数字签名
	 * 
	 * @param source 待签名数据
	 * @return 签名后的数据，通过编码工具编码
	 * @throws CryptographyException 签名异常
	 */
	public String sign(String source) throws CryptographyException {
		return coder.encode(sign(source.getBytes()));
	}

	/**
	 * 验证签名后的数据
	 * 
	 * @param expect 原始数据
	 * @param sign 签名后的数据
	 * @return 是否一致
	 * @throws CryptographyException 签名验证异常
	 */
	public boolean verify(String expect, String sign) throws CryptographyException {
		return verify(expect.getBytes(), coder.decode(sign));
	}

}
