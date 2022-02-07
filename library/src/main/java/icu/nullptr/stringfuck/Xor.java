package icu.nullptr.stringfuck;

import java.nio.charset.StandardCharsets;

@SuppressWarnings("unused")
public class Xor {

    static {
        String dummy = decrypt(new byte[0]);
    }

    public static String decrypt(byte[] cypherBytes) {
        byte[] decryptBytes = cypherBytes.clone();
        for (int i = 0, j = 0; i < decryptBytes.length; i++) {
            decryptBytes[i] ^= Config.key[j];
            j = (j + 1) % Config.key.length;
        }
        return new String(decryptBytes, StandardCharsets.UTF_8);
    }
}
