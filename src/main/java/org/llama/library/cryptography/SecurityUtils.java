/**  
 * @Title: CryptographyUtils.java
 * @Package works.tonny.library.cryptography
 * @author Tonny
 * @date 2012-4-9 下午5:09:53
 */
package org.llama.library.cryptography;

import java.io.UnsupportedEncodingException;

/**
 * @ClassName: CryptographyUtils
 * @Description:
 * @author Tonny
 * @date 2012-4-9 下午5:09:53
 * @version 1.0
 */
public class SecurityUtils {

	public static final HashProvider MD5 = new HashProvider("MD5");

	public static final HashProvider MD2 = new HashProvider("MD2");

	public static final HashProvider SHA1 = new HashProvider("SHA-1");

	public static final HashProvider SHA256 = new HashProvider("SHA-256");

	public static final HashProvider SHA384 = new HashProvider("SHA-384");

	public static final HashProvider SHA512 = new HashProvider("SHA-512");

	public static final Coder CODER = new Hex();

	public static final Coder BASE64 = new Base64();

	static {
		MD5.setCoder(CODER);
		MD2.setCoder(CODER);
		SHA1.setCoder(CODER);
		SHA256.setCoder(CODER);
		SHA384.setCoder(CODER);
		SHA512.setCoder(CODER);
	}

	public static String base64Encoder(String str) {
		return BASE64.encode(str.getBytes());
	}

	public static String base64Decoder(String str) {
		return new String(BASE64.decode(str));
	}

	public static byte[] base64DecoderToByte(String str) {
		return BASE64.decode(str);
	}

	public static String base64Encoder(String str, String charset) throws UnsupportedEncodingException {
		return BASE64.encode(str.getBytes(charset));
	}

	public static String base64Decoder(String str, String charset) throws CryptographyException,
			UnsupportedEncodingException {
		return new String(BASE64.decode(str), charset);
	}

	public static String hexEncoder(byte[] str) {
		return CODER.encode(str);
	}

	public static String md5(String str) {
		return MD5.encrypt(str);
	}

	public static String md2(String str) {
		return MD5.encrypt(str);
	}

	public static String sha1(String str) {
		return SHA1.encrypt(str);
	}

	public static String sha256(String str) {
		return SHA256.encrypt(str);
	}

	public static String sha384(String str) {
		return SHA384.encrypt(str);
	}

	public static String sha512(String str) {
		return SHA512.encrypt(str);
	}

}
