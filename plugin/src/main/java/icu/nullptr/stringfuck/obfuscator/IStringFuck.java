package icu.nullptr.stringfuck.obfuscator;

public interface IStringFuck {

    byte[] encrypt(String plainText, byte[] key);

    String decrypt(byte[] cypherBytes, byte[] key);
}
