package org.llama.library.cryptography;

import org.llama.library.ComponentContainer;

/**
 * 密码工具工厂类，获取部署的密码工具，进行加密解密
 * 
 * @author tonny
 * 
 */
public interface CryptographyManager {
	/**
	 * 获取默认的编码器
	 * 
	 * @return 默认的编码器
	 */
	public Coder getCoder();

	/**
	 * 通过名称读取编码工具，支持hex和base64
	 * 
	 * @param name hex或base64
	 * @return 编码工具
	 */
	public Coder getCoder(String name);

	/**
	 * 获取配置的加密工具
	 * 
	 * @param name 加密工具名称
	 * @return 加密工具
	 */
	public Encryptable getEncryptor(String name);

	/**
	 * 通过加密工具进行加密字符串
	 * 
	 * @param name 加密工具名称
	 * @param str 字符串
	 * @return 加密后的数据
	 * @throws CryptographyException 加密异常
	 */
	public String encrypt(String name, String str);

	/**
	 * 通过解密工具进行解密字符串
	 * 
	 * @param name 解密工具名称
	 * @param str 字符串
	 * @return 解密后的数据
	 * @throws CryptographyException 解密异常
	 */
	public String decrypt(String name, String str);

	/**
	 * 通过签名工具进行数字签名
	 * 
	 * @param name 签名工具名称
	 * @param str 字符串
	 * @return 签名后的数据
	 */
	public String sign(String name, String str);

	/**
	 * 通过解密工具进行签名验证
	 * 
	 * @param name 签名工具名称
	 * @param str 原始字符串
	 * @param sing 签名后的字符串
	 * @return 是否一致
	 */
	public boolean verify(String name, String expect, String sign);

	/**
	 * 获取配置的解密工具
	 * 
	 * @param name 解密工具名称
	 * @return 解密工具
	 */
	public Decryptable getDecryptor(String name);

	/**
	 * 获取配置的签名工具
	 * 
	 * @param name 签名工具名称
	 * @return 签名工具
	 */
	public Signable getSignature(String name);

}
