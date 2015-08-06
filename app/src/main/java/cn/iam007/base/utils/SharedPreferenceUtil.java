package cn.iam007.base.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;

public class SharedPreferenceUtil {

    private static String PREDERENCE_NAME = "SharedPreferenceUtil";

    private static Context context;

    public static void init(Context _context) {
        context = _context;
        PREDERENCE_NAME = _context.getPackageName();
    }

    public static void setBoolean(String key, Boolean value) {
        SharedPreferences sp = context.getSharedPreferences(PREDERENCE_NAME, 0);

        if (value != sp.getBoolean(key, false)) {
            Editor editor = sp.edit();
            editor.putBoolean(key, value);
            editor.commit();
        }
    }

    public static boolean getBoolean(String key, Boolean defaultValue) {
        SharedPreferences sp = context.getSharedPreferences(PREDERENCE_NAME, 0);
        return sp.getBoolean(key, defaultValue);
    }

    public static void setLong(String key, Long value) {
        SharedPreferences sp = context.getSharedPreferences(PREDERENCE_NAME, 0);

        if (value != sp.getLong(key, 0)) {
            Editor editor = sp.edit();
            editor.putLong(key, value);
            editor.commit();
        }
    }

    public static long getLong(String key, Long defaultValue) {
        SharedPreferences sp = context.getSharedPreferences(PREDERENCE_NAME, 0);
        return sp.getLong(key, defaultValue);
    }

    public static void setOnSharedPreferenceChangeListener(
            OnSharedPreferenceChangeListener listener) {
        SharedPreferences sp = context.getSharedPreferences(PREDERENCE_NAME, 0);
        sp.registerOnSharedPreferenceChangeListener(listener);
    }

    public static void clearOnSharedPreferenceChangeListener(
            OnSharedPreferenceChangeListener listener) {
        SharedPreferences sp = context.getSharedPreferences(PREDERENCE_NAME, 0);
        sp.unregisterOnSharedPreferenceChangeListener(listener);
    }
}
