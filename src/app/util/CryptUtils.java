package app.util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 */
public class CryptUtils {

    public static byte[] encryptAES(byte[] keyBytes, byte[] dataBytes) {
        return handleAES(keyBytes, dataBytes, Cipher.ENCRYPT_MODE);
    }

    public static byte[] decryptAES(byte[] keyBytes, byte[] dataBytes) {
        return handleAES(keyBytes, dataBytes, Cipher.DECRYPT_MODE);
    }

    private static byte[] handleAES(byte[] keyBytes, byte[] dataBytes, int mode) {

        if (keyBytes == null || keyBytes.length != 16) {
            throw new CryptoException("A chave é inválida");
        }

        if (dataBytes == null) {
            throw new CryptoException("Não existem dados");
        }

        try {
            SecretKeySpec key = new SecretKeySpec(keyBytes, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(mode, key);
            return cipher.doFinal(dataBytes);

        } catch (Exception e) {
            throw new CryptoException(e);
        }
    }
}
