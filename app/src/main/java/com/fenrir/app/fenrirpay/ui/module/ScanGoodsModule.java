package com.fenrir.app.fenrirpay.ui.module;

import android.content.Context;

import com.fenrir.app.fenrirpay.di.ActivityScope;
import com.fenrir.app.fenrirpay.ui.fragment.ScanGoodsFragment;
import com.fenrir.app.fenrirpay.ui.presenter.ScanGoodsPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by DaggerGenerator on 2016/04/16.
 */
@Module
public class ScanGoodsModule extends BaseModule{
    ScanGoodsFragment fragment;

    public ScanGoodsModule(ScanGoodsFragment fragment){
        this.fragment = fragment;
    }

    @ActivityScope
    @Provides
    public ScanGoodsPresenter providePresenter(){
        return new ScanGoodsPresenter();
    }

    @Override
    public Context provideActivity(){
        return fragment.getContext();
    }

}