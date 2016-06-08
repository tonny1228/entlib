package org.llama.library.cryptography;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.llama.library.AbstractComponentContainer;
import org.llama.library.configuration.ConfigurationException;
import org.llama.library.configuration.SimpleConfiguration;


/**
 * 密码工具工厂类，获取部署的密码工具，进行加密解密
 * 
 * @author tonny
 * 
 */
public class CryptographyManagerImpl extends AbstractComponentContainer implements CryptographyManager {
	/**
	 * 算法类型，哈希、对称、非对称
	 */
	private static final Map<String, String> ENCRYPTOR_TYPE;

	/**
	 * 密码工具
	 */
	private final Map<String, Object> ENCRYPTOR = new HashMap<String, Object>();

	/**
	 * 编码解码工具
	 */
	private static final Map<String, Coder> CODERS;

	/**
	 * 部署的默认编码、解码工具
	 */
	static String defaultCoder;

	static {
		ENCRYPTOR_TYPE = new HashMap<String, String>();
		ENCRYPTOR_TYPE.put("MD2", "hash");
		ENCRYPTOR_TYPE.put("MD5", "hash");
		ENCRYPTOR_TYPE.put("SHA-1", "hash");
		ENCRYPTOR_TYPE.put("SHA-256", "hash");
		ENCRYPTOR_TYPE.put("SHA-384", "hash");
		ENCRYPTOR_TYPE.put("SHA-512", "hash");
		ENCRYPTOR_TYPE.put("AES", "symmetric");
		ENCRYPTOR_TYPE.put("DES", "symmetric");
		ENCRYPTOR_TYPE.put("Blowfish", "symmetric");
		ENCRYPTOR_TYPE.put("DESede", "symmetric");
		ENCRYPTOR_TYPE.put("RSA", "asymmetric");
		ENCRYPTOR_TYPE.put("RC2", "asymmetric");
		ENCRYPTOR_TYPE.put("RC4", "asymmetric");
		ENCRYPTOR_TYPE.put("RC5", "asymmetric");
		ENCRYPTOR_TYPE.put("DSA", "sign");
		CODERS = new HashMap<String, Coder>();
		Coder hex = new Hex();
		CODERS.put(null, hex);
		CODERS.put("hex", hex);
		CODERS.put("base64", new Base64());
	}

	/**
	 * <p>
	 * 初始配置
	 * </p>
	 * 
	 * @param configuration
	 */
	public CryptographyManagerImpl(SimpleConfiguration configuration) {
		super(configuration);
		init();
	}

	/**
	 * 载入配置，初始化部署的密码工具
	 * 
	 * @throws ConfigurationException 配置异常或密钥异常
	 */
	public void init() throws ConfigurationException {
		defaultCoder = configuration.getString("security.coder");
		int cryptor = configuration.size("security.cryptography");
		for (int i = 0; i < cryptor; i++) {
			String name = configuration.getString("security.cryptography(" + i + ").name");
			String type = configuration.getString("security.cryptography(" + i + ").type");
			String coder = configuration.getString("security.cryptography(" + i + ").coder");
			if (ENCRYPTOR_TYPE.get(type) == null) {
				throw new ConfigurationException("不支持的加密类型");
			}
			if (ENCRYPTOR_TYPE.get(type).equals("hash")) {
				configHashType(name, type, coder);
			}
			if (ENCRYPTOR_TYPE.get(type).equals("symmetric")) {
				configSymmetricType(configuration, i, name, type, coder);
			}

			if (ENCRYPTOR_TYPE.get(type).equals("asymmetric")) {
				configAsymmetricType(configuration, i, name, type, coder);
			}
			if (ENCRYPTOR_TYPE.get(type).equals("sign")) {
				configSignType(configuration, i, name, type, coder);
			}

		}
	}

	/**
	 * 读取签名工具的配置
	 * 
	 * @param config 配置
	 * @param i 第i条配置
	 * @param name 名称
	 * @param type 算法
	 * @param coder
	 * @throws ConfigurationException 密钥异常
	 */
	protected void configSignType(SimpleConfiguration config, int i, String name, String type, String coder)
			throws ConfigurationException {
		String key = config.getString("security.cryptography(" + i + ").publickey");
		try {
			if (key != null) {
				Signable signable = new SignatureProvider(type, KeyGenerater.encoderedByte2PublicKey(key, type,
						getCoder()));
				signable.setCoder(CODERS.get(StringUtils.defaultIfEmpty(coder, defaultCoder)));
				ENCRYPTOR.put(name, signable);
			} else {
				key = config.getString("security.cryptography(" + i + ").privatekey");
				Signable signable = new SignatureProvider(type, KeyGenerater.encoderedByte2PrivateKey(key, type,
						getCoder()));
				signable.setCoder(CODERS.get(StringUtils.defaultIfEmpty(coder, defaultCoder)));
				ENCRYPTOR.put(name, signable);
			}
		} catch (CryptographyException e) {
			throw new ConfigurationException("非法密钥", e);
		}
	}

	/**
	 * 读取非对称加密工具的配置
	 * 
	 * @param config 配置
	 * @param i 第i条配置
	 * @param name 名称
	 * @param type 算法
	 * @param coder
	 * @throws ConfigurationException 密钥异常
	 */
	protected void configAsymmetricType(SimpleConfiguration config, int i, String name, String type, String coder)
			throws ConfigurationException {
		String key = config.getString("security.cryptography(" + i + ").publickey");
		String mode = config.getString("security.cryptography(" + i + ").mode");
		String padding = config.getString("security.cryptography(" + i + ").padding");
		try {
			if (key != null) {

				Encryptable encryptor = new CipherProvider(createAlgorithm(type, mode, padding),
						KeyGenerater.encoderedByte2PublicKey(key, type, getCoder()));
				encryptor.setCoder(CODERS.get(StringUtils.defaultIfEmpty(coder, defaultCoder)));
				ENCRYPTOR.put(name, encryptor);
			} else {
				key = config.getString("security.cryptography(" + i + ").privatekey");
				Decryptable decryptor = new CipherProvider(createAlgorithm(type, mode, padding),
						KeyGenerater.encoderedByte2PrivateKey(key, type, getCoder()));
				decryptor.setCoder(CODERS.get(StringUtils.defaultIfEmpty(coder, defaultCoder)));
				ENCRYPTOR.put(name, decryptor);
			}
		} catch (CryptographyException e) {
			throw new ConfigurationException("非法密钥", e);
		}
	}

	/**
	 * 读取对称加密工具的配置
	 * 
	 * @param config 配置
	 * @param i 第i条配置
	 * @param name 名称
	 * @param type 算法
	 * @param coder
	 * @throws ConfigurationException 密钥异常
	 */
	protected void configSymmetricType(SimpleConfiguration config, int i, String name, String type, String coder)
			throws ConfigurationException {
		String key = config.getString("security.cryptography(" + i + ").key");
		String mode = config.getString("security.cryptography(" + i + ").mode");
		String padding = config.getString("security.cryptography(" + i + ").padding");

		try {
			Encryptable encryptor = new CipherProvider(createAlgorithm(type, mode, padding),
					KeyGenerater.encoderedByte2Key(key, type, getCoder()));
			encryptor.setCoder(CODERS.get(StringUtils.defaultIfEmpty(coder, defaultCoder)));
			ENCRYPTOR.put(name, encryptor);
		} catch (CryptographyException e) {
			throw new ConfigurationException("非法密钥", e);
		}
	}

	/**
	 * 读取哈希函数加密工具的配置
	 * 
	 * @param name 名称
	 * @param type 算法
	 * @param coder
	 */
	protected void configHashType(String name, String type, String coder) {
		Encryptable encryptor = new HashProvider(type);
		encryptor.setCoder(CODERS.get(StringUtils.defaultIfEmpty(coder, defaultCoder)));
		ENCRYPTOR.put(name, encryptor);
	}

	/**
	 * 生成算法名称
	 * 
	 * @param type
	 * @param mode
	 * @param padding
	 * @return
	 */
	protected String createAlgorithm(String type, String mode, String padding) {
		if (mode == null && padding == null) {
			return type;
		}

		if (mode == null) {
			mode = "NONE";
		}
		if (padding == null) {
			padding = "NoPadding";
		}

		return type + "/" + mode + "/" + padding;
	}

	/**
	 * 通过名称读取编码工具，支持hex和base64
	 * 
	 * @param name hex或base64
	 * @return 编码工具
	 */
	public Coder getCoder(String name) {
		return CODERS.get(name);
	}

	/**
	 * 获取配置的加密工具
	 * 
	 * @param name 加密工具名称
	 * @return 加密工具
	 */
	public Encryptable getEncryptor(String name) {
		return (Encryptable) ENCRYPTOR.get(name);
	}

	/**
	 * 通过加密工具进行加密字符串
	 * 
	 * @param name 加密工具名称
	 * @param str 字符串
	 * @return 加密后的数据
	 * @throws CryptographyException 加密异常
	 */
	public String encrypt(String name, String str) throws CryptographyException {
		return ((Encryptable) ENCRYPTOR.get(name)).encrypt(str);
	}

	/**
	 * 通过解密工具进行解密字符串
	 * 
	 * @param name 解密工具名称
	 * @param str 字符串
	 * @return 解密后的数据
	 * @throws CryptographyException 解密异常
	 */
	public String decrypt(String name, String str) throws CryptographyException {
		return ((Decryptable) ENCRYPTOR.get(name)).decrypt(str);
	}

	/**
	 * 通过签名工具进行数字签名
	 * 
	 * @param name 签名工具名称
	 * @param str 字符串
	 * @return 签名后的数据
	 * @throws CryptographyException 签名异常
	 */
	public String sign(String name, String str) throws CryptographyException {
		return ((Signable) ENCRYPTOR.get(name)).sign(str);
	}

	/**
	 * 通过解密工具进行签名验证
	 * 
	 * @param name 签名工具名称
	 * @param str 原始字符串
	 * @param sing 签名后的字符串
	 * @return 是否一致
	 * @throws CryptographyException 签名异常
	 */
	public boolean verify(String name, String expect, String sign) throws CryptographyException {
		return ((Signable) ENCRYPTOR.get(name)).verify(expect, sign);
	}

	/**
	 * 获取配置的解密工具
	 * 
	 * @param name 解密工具名称
	 * @return 解密工具
	 */
	public Decryptable getDecryptor(String name) {
		return (Decryptable) ENCRYPTOR.get(name);
	}

	/**
	 * 获取配置的签名工具
	 * 
	 * @param name 签名工具名称
	 * @return 签名工具
	 */
	public Signable getSignature(String name) {
		return (Signable) ENCRYPTOR.get(name);
	}

	/**
	 * @param name
	 * @return
	 * @see org.llama.library.AbstractComponentContainer#get(java.lang.String)
	 */
	@Override
	public Object get(String name) {
		return ENCRYPTOR.get(name);
	}

	/**
	 * @return
	 * @see org.llama.library.cryptography.CryptographyManager#getCoder()
	 */
	public Coder getCoder() {
		return CODERS.get(defaultCoder);
	}

}
