package icu.nullptr.stringfuck;

import android.annotation.SuppressLint;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.function.Function;

import sun.misc.Unsafe;

public class StringFuck {

    @SuppressLint("DiscouragedPrivateApi")
    public static void init() {
        try {
            Unsafe unsafe = (Unsafe) Unsafe.class.getDeclaredMethod("getUnsafe").invoke(null);
            assert unsafe != null;
            Field decryptorField = Stub.class.getDeclaredFields()[0];
            long decryptorOffset = unsafe.objectFieldOffset(decryptorField);
            Method m = Config.decryptorClass.getDeclaredMethods()[0];
            Function<byte[], String> impl = cypherBytes -> {
                try {
                    return (String) m.invoke(null, (Object) cypherBytes);
                } catch (ReflectiveOperationException e) {
                    if (BuildConfig.DEBUG) throw new ExceptionInInitializerError(e);
                    else return null;
                }
            };
            unsafe.putObject(Stub.instance, decryptorOffset, impl);
        } catch (ReflectiveOperationException e) {
            if (BuildConfig.DEBUG) throw new ExceptionInInitializerError(e);
        }
    }
}
