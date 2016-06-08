/**
 * 
 */
package org.llama.library;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

import junit.framework.TestCase;

import org.llama.library.cryptography.CipherProvider;
import org.llama.library.cryptography.Coder;
import org.llama.library.cryptography.Decryptable;
import org.llama.library.cryptography.Encryptable;
import org.llama.library.cryptography.KeyGenerater;

/**
 * 
 * @author tonny
 * @date 2012-8-30
 * @version 1.0.0
 */
public class DES3 extends TestCase {

	public void test() throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException {
		Coder coder = new org.llama.library.cryptography.Base64();
		System.out.println(coder.encode("hello123".getBytes()));

		Encryptable encryptor = new CipherProvider(createAlgorithm("DESede", "ECB", "PKCS5Padding"),
				KeyGenerater.encoderedByte2Key("kgl51um6adpbjnbz6xln3455", "DESede", coder));
		encryptor.setCoder(coder);
		String en = encryptor.encrypt("1234");
		System.out.println(en);
		System.out.println(((Decryptable) encryptor).decrypt(en));

	}

	/**
	 * @throws InvalidKeyException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 */
	private Key getkey(String key) throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException {
		SecureRandom sr = new SecureRandom();
		DESedeKeySpec dks = new DESedeKeySpec(key.getBytes());
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
		return keyFactory.generateSecret(dks);
	}

	public void one() throws Exception {
		Coder coder = new org.llama.library.cryptography.Base64();
		DESedeKeySpec dks = new DESedeKeySpec("kgl51um6adpbjnbz6xln34515".getBytes());
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
		SecretKey securekey = keyFactory.generateSecret(dks);
		IvParameterSpec iv = new IvParameterSpec("56781234".getBytes());
		Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, securekey, iv);
		String eb = coder.encode(cipher.doFinal("hello".getBytes()));
		System.out.println(eb);
		cipher.init(Cipher.DECRYPT_MODE, securekey, iv);
		System.out.println(new String(cipher.doFinal(coder.decode(eb))));
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
}
