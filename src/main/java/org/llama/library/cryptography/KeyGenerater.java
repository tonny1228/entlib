package org.llama.library.cryptography;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;

/**
 * 密钥生成工具
 * 
 * @author tonny
 * 
 */
public class KeyGenerater {

	/**
	 * 产生公钥和私钥
	 * 
	 * @param seed 密钥种子
	 * @param keySize 密钥长度
	 * @param algorithm 算法
	 * @return 公钥和私钥
	 * @throws CryptographyException 密钥生成异常
	 */
	public static Key[] generate(String seed, int keySize, String algorithm) throws CryptographyException {
		try {
			KeyPairGenerator keygen = KeyPairGenerator.getInstance(algorithm);
			SecureRandom secrand = new SecureRandom();
			secrand.setSeed(seed.getBytes());
			keygen.initialize(keySize, secrand);
			KeyPair keys = keygen.genKeyPair();
			PublicKey pubkey = keys.getPublic();
			PrivateKey prikey = keys.getPrivate();
			Key[] gkey = new Key[2];
			gkey[0] = pubkey;
			gkey[1] = prikey;
			return gkey;
		} catch (java.lang.Exception e) {
			throw new CryptographyException(e);
		}
	}

	/**
	 * 生成对称加密算法密钥
	 * 
	 * @param size 密钥长度
	 * @param seed 密钥种子
	 * @param algorithm 算法
	 * @return 密钥
	 * @throws CryptographyException 密钥生成异常
	 */
	public static Key generate(int size, String seed, String algorithm) throws CryptographyException {
		try {
			KeyGenerator generator = KeyGenerator.getInstance(algorithm);
			generator.init(size, new SecureRandom(seed.getBytes()));
			return generator.generateKey();
		} catch (java.lang.Exception e) {
			throw new CryptographyException(e);
		}
	}

	/**
	 * 生成对称加密算法密钥
	 * 
	 * @param seed 密钥种子
	 * @param algorithm 算法
	 * @return 密钥
	 * @throws CryptographyException 密钥生成异常
	 */
	public static Key generater(String seed, String algorithm) throws CryptographyException {
		try {
			KeyGenerator generator = KeyGenerator.getInstance(algorithm);
			generator.init(new SecureRandom(seed.getBytes()));
			return generator.generateKey();
		} catch (java.lang.Exception e) {
			throw new CryptographyException(e);
		}
	}

	/**
	 * 转换对称加密算法密钥
	 * 
	 * @param key 密钥的数组编码后的字符串
	 * @param algorithm 算法
	 * @return 密钥
	 * @throws CryptographyException 密钥生成异常
	 */
	public static Key encoderedByte2Key(String key, String algorithm, Coder coder) throws CryptographyException {
		return new SecretKeySpec(coder.decode(key), algorithm);
	}

	/**
	 * 密钥的数组编码后的字符串转换为公钥
	 * 
	 * @param key 密钥的数组编码后的字符串
	 * @param algorithm 算法
	 * @return 公钥
	 * @throws CryptographyException 密钥生成异常
	 */
	public static PublicKey encoderedByte2PublicKey(String key, String algorithm, Coder coder)
			throws CryptographyException {
		try {
			X509EncodedKeySpec keyGenerator = new X509EncodedKeySpec(coder.decode(key));
			KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
			return keyFactory.generatePublic(keyGenerator);
		} catch (java.lang.Exception e) {
			throw new CryptographyException(e);
		}
	}

	/**
	 * 密钥的数组编码后的字符串转换为私钥
	 * 
	 * @param key 密钥的数组编码后的字符串
	 * @param algorithm 算法
	 * @return 私钥
	 * @throws CryptographyException 密钥生成异常
	 */
	public static PrivateKey encoderedByte2PrivateKey(String key, String algorithm, Coder coder)
			throws CryptographyException {
		try {
			PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(coder.decode(key));
			KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
			PrivateKey prikey = keyFactory.generatePrivate(priPKCS8);
			return prikey;
		} catch (java.lang.Exception e) {
			throw new CryptographyException(e);
		}
	}
}
