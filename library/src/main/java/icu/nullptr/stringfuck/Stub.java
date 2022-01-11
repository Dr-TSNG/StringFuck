package icu.nullptr.stringfuck;

import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;

@SuppressWarnings("unused")
public final class Stub {

    public static final Stub instance = new Stub();

    private final Method decryptor;

    Stub() {
        String dummy1 = _decrypt(new byte[0]);
        String dummy2 = _decrypt(new byte[0]);
        decryptor = Stub.class.getDeclaredMethods()[0];
    }

    private static String _decrypt(byte[] cypherBytes) {
        byte[] stub = cypherBytes.clone();
        for (int i = 1; i < stub.length; i++) {
            stub[i] ^= stub[i - 1];
        }
        return new String(stub, StandardCharsets.UTF_8);
    }

    public String decrypt(String encrypted) {
        try {
            return (String) decryptor.invoke(null, encrypted.getBytes(StandardCharsets.UTF_8));
        } catch (ReflectiveOperationException e) {
            if (BuildConfig.DEBUG) throw new ExceptionInInitializerError(e);
            else return null;
        }
    }
}
