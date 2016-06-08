/**
 * 
 */
package org.llama.library;

import junit.framework.TestCase;
import org.junit.Test;
import org.llama.library.cryptography.*;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * 
 * @author tonny
 * @date 2014-10-13
 * @version 1.0.0
 */
public class AESTest extends TestCase {
	public void test() throws NoSuchAlgorithmException {
		KeyGenerator kgen = KeyGenerator.getInstance("AES");
		kgen.init(128, new SecureRandom("pjjtewmencoder".getBytes()));
		SecretKey secretKey = kgen.generateKey();
		Base64 base64 = new Base64();
		String encode = base64.encode(secretKey.getEncoded());
		System.out.println(encode);
		CipherProvider cipherProvider = new CipherProvider("AES", KeyGenerater.encoderedByte2Key(encode, "AES", base64));
		cipherProvider.setCoder(base64);
		String encrypt = cipherProvider.encrypt("张三少妇;371123458193453456;AFHKDKJJUIIOOIIOOOOUUHNBGGJKIOUYU;男;中国");
		System.out.println(encrypt);
		System.out
				.println(cipherProvider
						.decrypt("qa4lnh/jC0ED3fSs6u5rL2EyyUtTJu7CzRkX1vG5ZCF5TskswIaXK9Wd6QH2gHp23KhjALYc375BkEpCwTE3/cYJj4Rem/g07peXm/6ORKk="));
	}

	@Test
	public void test2() {
		CryptographyManager component = (CryptographyManager) EnterpriseApplication.getComponent("security");
		Encryptable encryptor = component.getEncryptor("passwordDecoder");
		System.out.println(encryptor.encrypt("1234"));
		Decryptable decryptor = component.getDecryptor("passwordDecoder");
		System.out.println(decryptor.decrypt("2SAVkmYYs/1weMzUr2nPSQ=="));
	}


}
