package com.example.miracle.financehelp.utils;

import android.content.Context;

/**
 * Created by Miracle on 2017/9/30 0030.
 */

public class SizeUtils {
    public static int dp2px(Context context, float value) {
        return (int) (value * context.getResources().getDisplayMetrics().density + 0.5F);
    }

    public static int px2dp(Context context, float value) {
        return (int) (value / context.getResources().getDisplayMetrics().density + 0.5F);
    }

    public static int px2sp(Context context, float value) {
        return (int) (value / context.getResources().getDisplayMetrics().scaledDensity + 0.5F);
    }

    public static int sp2px(Context context, float value) {
        return (int) (value * context.getResources().getDisplayMetrics().scaledDensity + 0.5F);
    }
}
