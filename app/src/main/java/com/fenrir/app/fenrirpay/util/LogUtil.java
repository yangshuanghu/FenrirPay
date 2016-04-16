package com.fenrir.app.fenrirpay.util;

import android.text.TextUtils;
import android.util.Log;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.fenrir.app.fenrirpay.BuildConfig;

/**
 * Created by yume on 16/3/18.
 */
public class LogUtil {
    private static boolean debug = BuildConfig.DEBUG;

    private LogUtil(){}

    public static void setDebug(boolean debug) {
        LogUtil.debug = debug;
    }

    public static void m(String tag, String msg) {
        showMsg(tag, msg);
    }

    public static void m(Object from, String msg) {
        m(from.getClass(), msg);
    }

    public static void m(Object from, String... msg) {
        m(from.getClass(), msg);
    }

    public static void m(Class clazz, String msg) {
        m(clazz.getSimpleName(), msg);
    }

    public static void m(Class clazz, String... msg) {
        showMsg(clazz.getSimpleName(),
                Stream.of(msg)
                        .collect(Collectors.joining(" | ")));
    }

    public static void m(String msg) {
        showMsg(null, msg);
    }

    public static void m(String... msg) {
        showMsg(null,
                Stream.of(msg)
                        .collect(Collectors.joining(" | ")));
    }

    public static void e(Throwable t) {
        if(debug)
            t.printStackTrace();
    }

    private static void showMsg(String tag, String msg) {
        if(debug)
            Log.d(TextUtils.isEmpty(tag) ? "LogUtil" : tag, msg);
    }
}
