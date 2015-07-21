package cn.iam007.base.utils;

import android.util.Log;

public class LogUtil {

    public final static boolean LOGGER_ENABLE = true;

    public static void d(String msg) {
        if (LOGGER_ENABLE) {
            StackTraceElement[] elements = Thread.currentThread()
                    .getStackTrace();
            String filename = elements[3].getFileName();
            if (filename != null) {
                int index = filename.indexOf(".");
                String tag = filename;
                if (index >= 0) {
                    tag = filename.substring(0, index);
                }

                d(tag, msg);
            }
        }
    }

    public static void d(String tag, String msg) {
        if (LOGGER_ENABLE) {
            Log.d(tag, msg);
        }
    }

    public static void v(String msg) {
        if (LOGGER_ENABLE) {
            StackTraceElement[] elements = Thread.currentThread()
                    .getStackTrace();
            String tag = elements[1].getClass().getName();

            v(tag, msg);
        }
    }

    public static void v(String tag, String msg) {
        if (LOGGER_ENABLE) {
            Log.v(tag, msg);
        }
    }

    public static void e(String msg) {
        if (LOGGER_ENABLE) {
            StackTraceElement[] elements = Thread.currentThread()
                    .getStackTrace();
            String tag = elements[1].getClass().getName();

            e(tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (LOGGER_ENABLE) {
            Log.e(tag, msg);
        }
    }
}
