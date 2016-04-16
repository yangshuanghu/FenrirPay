package com.fenrir.app.fenrirpay.ui;

import com.facebook.stetho.Stetho;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;

import butterknife.ButterKnife;

public class DebugMainApplication extends MainApplication{
    @Override
    public void onCreate() {
        super.onCreate();

        ButterKnife.setDebug(true);

        Stetho.initializeWithDefaults(this);

        System.out.println("DebugMainApplication: is Debug mode");
    }
}