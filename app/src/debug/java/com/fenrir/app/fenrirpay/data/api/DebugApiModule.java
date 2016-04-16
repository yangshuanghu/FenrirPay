package com.fenrir.app.fenrirpay.data.api;

import com.facebook.stetho.okhttp3.StethoInterceptor;

import dagger.Module;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;

/**
 * Created by yume on 15/12/15.
 */
@Module
public class DebugApiModule extends ApiModule {

    @Override
    protected OkHttpClient.Builder okHttpClientSetting(OkHttpClient.Builder builder) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return builder
                .addNetworkInterceptor(new StethoInterceptor())
                .addInterceptor(interceptor);
    }

    @Override
    protected Retrofit.Builder dealRestAdapter(Retrofit.Builder builder) {
//        builder.setLogLevel(RestAdapter.LogLevel.FULL);
        return builder;
    }
}
