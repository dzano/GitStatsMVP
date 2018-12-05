package com.prowidgetstudio.gitstatsmvp;

import android.app.Application;
import android.content.res.Configuration;

import com.squareup.leakcanary.LeakCanary;

/**
 * Created by Dzano on 1.12.2018.
 */

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        if (!LeakCanary.isInAnalyzerProcess(this) && BuildConfig.DEBUG) {
            System.out.println("IDE LEAK-CANARY");
            LeakCanary.install(this);
        }


    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }
}