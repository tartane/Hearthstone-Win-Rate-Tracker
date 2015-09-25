package com.hswinratetracker;


import android.app.Application;
import android.content.Context;

public class App extends Application {
    private static Context context;
    public static int sdkVersion;
    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sdkVersion = android.os.Build.VERSION.SDK_INT;
        context = this;
    }

}