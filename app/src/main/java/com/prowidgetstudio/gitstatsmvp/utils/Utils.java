package com.prowidgetstudio.gitstatsmvp.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Dzano on 1.12.2018.
 */

public class Utils {


    public static boolean isValidGitURL(String url) {

        String[] dioUri = url.split("/");

        if (dioUri.length > 1 && !dioUri[0].equalsIgnoreCase("repos")) {
            return true;

        } else if (dioUri.length > 2 && dioUri[0].equalsIgnoreCase("repos")) {
            return true;

        } else {
            System.out.println("POGESNO UNESEN URL");
            return false;
        }
    }

    public static String gitURL(String url) {

        String[] dioUri = url.split("github.com/");

        if (dioUri.length == 2) {

            url = dioUri[1];
            dioUri = url.split("/");

            if (dioUri.length > 1 && !dioUri[0].equalsIgnoreCase("repos")) {
                return dioUri[0] + "/" + dioUri[1];

            } else if (dioUri.length > 2 && dioUri[0].equalsIgnoreCase("repos")) {
                return dioUri[1] + "/" + dioUri[2];

            } else {
                return "error";
            }

        } else return "error";
    }


    public static boolean isNetworkAvailable(Context context) {

        context = context.getApplicationContext();

        boolean isNetAvailable = false;
        if (context != null) {

            final ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            if (mConnectivityManager != null) {

                boolean mobileNetwork = false;
                boolean wifiNetwork = false;

                boolean mobileNetworkConnected = false;
                boolean wifiNetworkConnected = false;

                final NetworkInfo mobileInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
                final NetworkInfo wifiInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

                if (mobileInfo != null) {
                    mobileNetwork = mobileInfo.isAvailable();
                }

                if (wifiInfo != null) {
                    wifiNetwork = wifiInfo.isAvailable();
                }

                if (wifiNetwork || mobileNetwork) {

                    if (mobileInfo != null) {
                        mobileNetworkConnected = mobileInfo.isConnectedOrConnecting();
                    }
                    wifiNetworkConnected = wifiInfo.isConnectedOrConnecting();
                }
                isNetAvailable = (mobileNetworkConnected || wifiNetworkConnected);
            }
        }
        return isNetAvailable;
    }
}
