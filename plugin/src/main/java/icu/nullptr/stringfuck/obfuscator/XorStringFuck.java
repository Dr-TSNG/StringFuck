package icu.nullptr.stringfuck.obfuscator;

import java.nio.charset.StandardCharsets;

@SuppressWarnings("unused")
public class XorStringFuck implements IStringFuck {

    @Override
    public byte[] encrypt(String plainText, byte[] key) {
        byte[] cypherBytes = plainText.getBytes(StandardCharsets.UTF_8);
        for (int i = 0, j = 0; i < cypherBytes.length; i++) {
            cypherBytes[i] ^= key[j];
            j = (j + 1) % key.length;
        }
        return cypherBytes;
    }

    @Override
    public String decrypt(byte[] cypherBytes, byte[] key) {
        byte[] decryptBytes = cypherBytes.clone();
        for (int i = 0, j = 0; i < decryptBytes.length; i++) {
            decryptBytes[i] ^= key[j];
            j = (j + 1) % key.length;
        }
        return new String(decryptBytes, StandardCharsets.UTF_8);
    }
}
