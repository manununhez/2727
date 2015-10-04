package com.cibersons.app2727;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * Created by manunez on 01/10/2015.
 */
public class App2727 extends Application {
    public static final String TAG = "com.cibersons.2727";

    public static class Logger {
        public static void i(String message) {
            Log.i(TAG, message);
        }

        public static void w(String message) {
            Log.w(TAG, message);
        }

        public static void e(String message) {
            if (message == null) {
                message = "Sin mensaje";
            }
            Log.e(TAG, message);
        }

        public static void d(String message) {
            Log.d(TAG, message);
        }
    }
}
