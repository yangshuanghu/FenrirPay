package com.fenrir.app.fenrirpay.ui.module;

import android.content.Context;

import com.fenrir.app.fenrirpay.di.ActivityScope;
import com.fenrir.app.fenrirpay.ui.AppMessageUtil;

import dagger.Module;
import dagger.Provides;

/**
 * Created by yume on 15/9/16.
 */
@Module
public abstract class BaseModule {
    @ActivityScope
    @Provides
    AppMessageUtil getAppMessageUtil(){
        return new AppMessageUtil(provideActivity());
    }

    abstract Context provideActivity();
}
