package icu.nullptr.stringfuck;

import android.annotation.SuppressLint;
import android.util.Log;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.Buffer;
import java.nio.ByteBuffer;

import sun.misc.Unsafe;

public class StringFuck {

    @SuppressLint("DiscouragedPrivateApi")
    public static void init() {
        try {
            Unsafe unsafe = (Unsafe) Unsafe.class.getDeclaredMethod("getUnsafe").invoke(null);
            Field artMethodField = Method.class.getSuperclass().getDeclaredField("artMethod");
            artMethodField.setAccessible(true);

            Constructor<?>[] cs = Exception.class.getDeclaredConstructors();
            long artMethodSize = artMethodField.getLong(cs[1]) - artMethodField.getLong(cs[0]);

            Method src = Config.decryptorClass.getMethods()[0];
            Method dst = Stub.class.getMethods()[0];
            long srcAddr = artMethodField.getLong(src);
            long dstAddr = artMethodField.getLong(dst);

            unsafe.copyMemory(srcAddr, dstAddr, artMethodSize);
        } catch (ReflectiveOperationException e) {
            throw new ExceptionInInitializerError(e);
        }
    }
}
