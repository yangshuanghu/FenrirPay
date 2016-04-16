package com.fenrir.app.fenrirpay.ui;

import android.app.Application;
import android.content.Context;

/**
 * Created by yume on 16-4-16.
 */
public class MainApplication extends Application {
    private AppComponent appComponent;

    public static MainApplication get(Context context) {
        return (MainApplication) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        inject();
    }

    protected void inject() {
        appComponent = DaggerAppComponent.builder()
                .mainAppModule(new MainAppModule(this.getApplicationContext()))
                .build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
