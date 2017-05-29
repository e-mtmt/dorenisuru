package jp.eunika.dorenisuru.common.util;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.config.ConfigFileApplicationListener;
import org.springframework.core.env.StandardEnvironment;

public final class EncryptionUtil {
	private static final String encryptAlgorithm = "Blowfish";
	private static final String propertyKeyOnEncryptKey = "app.encrypt-key";
	private static final Cipher cipher;

	static {
		StandardEnvironment env = new StandardEnvironment();
		new ConfigFileApplicationListener().postProcessEnvironment(env, new SpringApplication());
		String encryptKey = env.getRequiredProperty(propertyKeyOnEncryptKey, String.class);
		try {
			cipher = Cipher.getInstance(encryptAlgorithm);
			cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(encryptKey.getBytes(), encryptAlgorithm));
		}
		catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException e) {
			throw new RuntimeException(e);
		}
	}

	public static String encryptText(String text) {
		try {
			return DatatypeConverter.printHexBinary(cipher.doFinal(text.getBytes()));
		}
		catch (IllegalBlockSizeException | BadPaddingException e) {
			throw new RuntimeException(e);
		}
	}

	private EncryptionUtil() {}
}
