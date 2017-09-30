package com.example.miracle.financehelp.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Miracle on 2017/9/30 0030.
 */

public class NetworkUtils {
    private static NetworkInfo getActiveNetworkInfo(Context context) {
        return ((ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
    }

    public static boolean isConnected(Context context) {
        NetworkInfo networkInfo = getActiveNetworkInfo(context);
        return (networkInfo != null) && (networkInfo.isConnected());
    }
}
