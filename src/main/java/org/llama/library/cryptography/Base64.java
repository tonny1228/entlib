package org.llama.library.cryptography;

import java.io.IOException;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * <p>
 * base64 byte array编码解码工具
 * </p>
 * <p>
 * Copyright: Copyright (c) 2008
 * </p>
 * 
 * @version 1.0
 * @Date 2010-03-11
 */
public class Base64 implements Coder {

	private BASE64Encoder encoder;

	private BASE64Decoder decoder;

	/**
	 * 编码
	 * 
	 * @param bytes byte数组
	 * @return base64编码的字符串
	 */
	@SuppressWarnings("restriction")
	public String encode(byte[] bytes) {
		synchronized (this) {
			if (encoder == null) {
				encoder = new BASE64Encoder();
			}
		}
		String code = encoder.encode(bytes);
		return code.replaceAll("\\s", "");
	}

	/**
	 * 编码
	 * 
	 * @param str 编码后字符串
	 * @return 解码后字符串
	 * @throws CryptographyException 解码异常
	 */
	@SuppressWarnings("restriction")
	public byte[] decode(String str) throws CryptographyException {
		synchronized (this) {
			if (decoder == null) {
				decoder = new BASE64Decoder();
			}
		}
		byte[] bytes;
		try {
			bytes = decoder.decodeBuffer(str);
		} catch (IOException e) {
			throw new CryptographyException(e);
		}
		return bytes;
	}

}
