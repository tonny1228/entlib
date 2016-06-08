package org.llama.library.cryptography;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;

/**
 * 加密、解密工具，支持java的对称和不对称加密算法
 * 
 * @author tonny
 * 
 */
public class CipherProvider implements Encryptable, Decryptable {

	/**
	 * 密钥
	 */
	private Key key;

	/**
	 * 算法
	 */
	private String algorithm;

	/**
	 * 密码算法实例
	 */
	private Cipher cipher;

	/**
	 * 编码、解码器
	 */
	private Coder coder;

	/**
	 * 初始化算法名称和密钥
	 * 
	 * @param algorithm 算法名称
	 * @param key 密钥
	 */
	public CipherProvider(String algorithm, Key key) {
		this.algorithm = algorithm;
		this.key = key;
	}

	/**
	 * 将字节数组解密，输出原始字节数组。
	 * 
	 * @param source 待解密的数据
	 * @return 解密后的数据
	 * @throws CryptographyException 解密异常
	 */
	public byte[] decrypt(byte[] source) throws CryptographyException {
		try {
			Cipher cipher = getCipher();
			cipher.init(Cipher.DECRYPT_MODE, key);
			return cipher.doFinal(source);
		} catch (Exception e) {
			throw new CryptographyException(e);
		}
	}

	/**
	 * 初始化密码算法实例
	 * 
	 * @return 算法实例
	 * @throws NoSuchAlgorithmException 算法不存在异常
	 * @throws NoSuchPaddingException 算法不存在异常
	 * @throws NoSuchProviderException
	 */
	protected synchronized Cipher getCipher() throws NoSuchAlgorithmException, NoSuchPaddingException,
			NoSuchProviderException {
		if (cipher == null) {
			cipher = Cipher.getInstance(algorithm);
		}
		return cipher;
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
			Cipher cipher = getCipher();
			cipher.init(Cipher.ENCRYPT_MODE, key);
			return cipher.doFinal(source);
		} catch (Exception e) {
			throw new CryptographyException(e);
		}
	}

	/**
	 * 直接将字符串通过Hex或base64解码的字符串解密，解码工具在setCoder中指定
	 * 
	 * @param source 待解密的Hex或base64编码的字符串
	 * @return 原始字符串
	 * @throws CryptographyException 解密异常
	 */
	public String decrypt(String source) throws CryptographyException {
		return new String(decrypt(coder.decode(source)));
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
