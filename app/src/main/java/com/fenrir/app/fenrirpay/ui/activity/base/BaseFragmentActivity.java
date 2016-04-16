package com.fenrir.app.fenrirpay.ui.activity.base;

import android.os.Bundle;
import android.os.PersistableBundle;

import com.fenrir.app.fenrirpay.ui.AppComponent;
import com.fenrir.app.fenrirpay.ui.MainApplication;

import butterknife.ButterKnife;
import indi.yume.tools.fragmentmanager.BaseFragmentManagerActivity;

/**
 * Created by yume on 15/9/22.
 */
public abstract class BaseFragmentActivity extends BaseFragmentManagerActivity {

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        AppComponent appComponent = MainApplication.get(this).getAppComponent();
        inject(appComponent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        injectOnStart(MainApplication.get(this).getAppComponent());
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
    }

    protected abstract void inject(AppComponent appComponent);

    protected void injectOnStart(AppComponent appComponent){

    }
}
