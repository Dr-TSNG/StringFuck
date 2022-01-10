package icu.nullptr.stringfuck;

import java.nio.charset.StandardCharsets;
import java.util.function.Function;

public final class Stub {

    public static final Stub instance = new Stub();

    public final Function<byte[], String> decryptor;

    Stub() {
        decryptor = Stub::decrypt;
    }

    private static String decrypt(byte[] cypherBytes) {
        byte[] stub = cypherBytes.clone();
        for (int i = 1; i < stub.length; i++) {
            stub[i] ^= stub[i - 1];
        }
        return new String(stub, StandardCharsets.UTF_8);
    }
}
