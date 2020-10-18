package app.util;

import java.util.Base64;

public interface Encryption {

    byte[] SECRET_KEY = "LDJGOGDLKJFSDYFK".getBytes();

    default String encrypt(String pass) {
        try {
            byte[] encBytes = CryptUtils.encryptAES(SECRET_KEY, pass.getBytes());
            byte[] base64Bytes = Base64.getEncoder().encode(encBytes);
            return new String(base64Bytes);
        } catch (Exception e) {
            throw new CryptoException(e);
        }
    }

    default String decrypt(String pass) {
        try {
            byte[] base64Bytes = pass.getBytes();
            byte[] encBytes = Base64.getDecoder().decode(base64Bytes);
            byte[] decBytes = CryptUtils.decryptAES(SECRET_KEY, encBytes);
            return new String(decBytes);
        } catch (Exception e) {
            throw new CryptoException(e);
        }
    }
}
