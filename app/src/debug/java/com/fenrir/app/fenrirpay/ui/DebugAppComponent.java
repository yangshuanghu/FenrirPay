package com.fenrir.app.fenrirpay.ui;

import com.fenrir.app.fenrirpay.data.api.DebugApiModule;
import com.fenrir.app.fenrirpay.data.sharedpref.SharedPrefModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by yume on 15/12/15.
 */
@Singleton
@Component(modules = {DebugApiModule.class, SharedPrefModule.class, MainAppModule.class})
public interface DebugAppComponent extends AppComponent {

}
