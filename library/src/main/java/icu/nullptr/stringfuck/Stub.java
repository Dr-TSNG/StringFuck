package icu.nullptr.stringfuck;

import java.nio.charset.StandardCharsets;

public final class Stub {

    public static Stub instance = new Stub();

    public String decrypt(byte[] cypherBytes) {
        byte[] stub = cypherBytes.clone();
        for (int i = 1; i < stub.length; i++) {
            stub[i] ^= stub[i - 1];
        }
        return new String(stub, StandardCharsets.UTF_8);
    }
}
