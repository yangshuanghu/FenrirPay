package com.fenrir.app.fenrirpay.data.sharedpref;

import android.content.Context;

import com.fenrir.app.fenrirpay.model.sharedpref.SharedPrefModel;
import com.fenrir.app.fenrirpay.util.Constants;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import indi.yume.tools.autosharedpref.AutoSharedPref;

/**
 * Created by yume on 16-4-16.
 */
@Module
public class SharedPrefModule {
    @Singleton
    @Provides
    SharedPrefModel provideSharedPrefModel(@Named("AppContext") Context context){
        return AutoSharedPref.newModel(context, SharedPrefModel.class, Constants.SHARED_PREF_FILE_NAME);
    }
}
