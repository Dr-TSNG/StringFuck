package icu.nullptr.stringfuck;

import java.lang.reflect.Field;

import sun.misc.Unsafe;

public class StringFuck {

    private static final byte[] code = {0x67, 0x65, 0x74, 0x55, 0x6E, 0x73, 0x61, 0x66, 0x65};

    public static void init() {
        try {
            Unsafe unsafe = (Unsafe) Unsafe.class.getDeclaredMethod(new String(code)).invoke(null);
            assert unsafe != null;
            Field decryptorField = Stub.class.getDeclaredFields()[0];
            long decryptorOffset = unsafe.objectFieldOffset(decryptorField);
            unsafe.putObject(Stub.instance, decryptorOffset, Config.decryptorClass.getDeclaredMethods()[0]);
        } catch (Throwable ignored) {
        }
    }
}
