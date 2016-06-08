package org.llama.library.cryptography;

/**
 * 字节数组与十六进制字符串转换器
 * 
 * @author tonny
 * 
 */

public class Hex implements Coder {
	/**
	 * 字节数组编码为十六进制字符串
	 * 
	 * @param bytes 字节数组
	 * @return 编码后的字符串
	 */
	public String encode(byte[] b) {
		StringBuffer hs = new StringBuffer();
		String stmp = null;
		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
				hs.append("0").append(stmp);
			else
				hs.append(stmp);
		}
		return hs.toString().toUpperCase();
	}

	/**
	 * 16进制表示的字符串转换成字节数组
	 * 
	 * @param str 编码后的字符串
	 * @return 原字节数组
	 */
	public byte[] decode(String str) throws CryptographyException {
		char c, c1;
		int x;
		if (str.length() % 2 != 0) {
			throw new CryptographyException("密钥格式不正确");
		}
		byte[] ret = new byte[str.length() / 2];
		for (int i = 0; i < str.length(); i++) {
			c = str.charAt(i);
			c1 = str.charAt(++i);
			if (!(c >= '0' && c <= '9' || c >= 'A' && c <= 'F' || c >= 'a' && c <= 'f'))
				throw new CryptographyException("密钥格式不正确");
			if (!(c1 >= '0' && c1 <= '9' || c1 >= 'A' && c1 <= 'F' || c1 >= 'a' && c1 <= 'f'))
				throw new CryptographyException("密钥格式不正确");
			x = Integer.decode("0x" + c + c1).intValue();
			if (x > 127) {
				ret[i / 2] = (byte) (x | 0xffffff00);
			} else {
				ret[i / 2] = (byte) (x);
			}
		}
		return ret;
	}
}
