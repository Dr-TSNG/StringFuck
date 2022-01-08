package icu.nullptr.stringfuck;

import android.annotation.SuppressLint;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import sun.misc.Unsafe;

public class StringFuck {

    private static final class NativeStructsModel {
        public static void f1() {}
        public static void f2() {}
    }

    @SuppressLint("DiscouragedPrivateApi")
    public static void init() {
        try {
            Unsafe unsafe = (Unsafe) Unsafe.class.getDeclaredMethod("getUnsafe").invoke(null);
            Field artMethodField = Method.class.getSuperclass().getDeclaredField("artMethod");
            artMethodField.setAccessible(true);

            NativeStructsModel.f1();
            NativeStructsModel.f2();
            Method[] ms = NativeStructsModel.class.getDeclaredMethods();
            long artMethodSize = artMethodField.getLong(ms[1]) - artMethodField.getLong(ms[0]);

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
