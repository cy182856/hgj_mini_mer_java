package com.ej.hgj.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class AESUtils {
    private static final String TRANSFORMATION = "AES/ECB/PKCS5Padding";
    private static final String ALGORITHM = "AES";

    public static String encrypt(String plainText, String key) throws Exception {
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), ALGORITHM);
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public static String decrypt(String cipherText, String key) throws Exception {
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), ALGORITHM);
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
        byte[] cipherBytes = Base64.getDecoder().decode(cipherText);
        byte[] decryptedBytes = cipher.doFinal(cipherBytes);
        return new String(decryptedBytes);
    }

}
