package com.fenrir.app.fenrirpay.ui;

import android.content.Context;

import com.fenrir.app.fenrirpay.data.api.ApiModule;
import com.fenrir.app.fenrirpay.data.sharedpref.SharedPrefModule;
import com.fenrir.app.fenrirpay.model.sharedpref.SharedPrefModel;
import com.google.gson.Gson;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Component;
import okhttp3.OkHttpClient;

/**
 * Created by yume on 15/9/14.
 */
@Singleton
@Component(modules = {ApiModule.class, SharedPrefModule.class, MainAppModule.class})
public interface AppComponent {
    @Named("AppContext") Context getContext();

    SharedPrefModel getSharedPrefModel();

    OkHttpClient getOkHttpClient();


    //API objects:
}
