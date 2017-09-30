package com.example.miracle.financehelp.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.Map;

public class SPUtils {
    private static SharedPreferences.Editor editor;
    private static SPUtils instance = null;
    private static SharedPreferences sp;

    private SPUtils() {
    }

    private SPUtils(Context context) {
        sp = context.getSharedPreferences("FinanceSP", Context.MODE_PRIVATE);
        editor = sp.edit();
        editor.apply();
    }

    public static void clear() {
        editor.clear().apply();
    }

    public static boolean contains(String value) {
        return sp.contains(value);
    }

    public static Map<String, ?> getAll() {
        return sp.getAll();
    }

    public static boolean getBoolean(String key) {
        return getBoolean(key, false);
    }

    public static boolean getBoolean(String key, boolean value) {
        return sp.getBoolean(key, value);
    }

    public static float getFloat(String key) {
        return getFloat(key, -1.0F);
    }

    public static float getFloat(String key, float value) {
        return sp.getFloat(key, value);
    }

    public static SPUtils getInstance(Context context) {
        try {
            if (instance == null) {
                instance = new SPUtils(context);
            }
            return instance;
        } finally {
        }
    }

    public static int getInt(String key) {
        return getInt(key, -1);
    }

    public static int getInt(String key, int value) {
        return sp.getInt(key, value);
    }

    public static long getLong(String key) {
        return getLong(key, -1L);
    }

    public static long getLong(String key, long value) {
        return sp.getLong(key, value);
    }

    public static String getString(String key) {
        return getString(key, null);
    }

    public static String getString(String key, String value) {
        return sp.getString(key, value);
    }

    public static void putBoolean(String key, boolean value) {
        editor.putBoolean(key, value).apply();
    }

    public static void putFloat(String key, float value) {
        editor.putFloat(key, value).apply();
    }

    public static void putInt(String key, int value) {
        editor.putInt(key, value).apply();
    }

    public static void putLong(String key, long value) {
        editor.putLong(key, value).apply();
    }

    public static void putString(String key1, String value) {
        editor.putString(key1, value).apply();
    }

    public static void remove(String key) {
        editor.remove(key).apply();
    }
}
