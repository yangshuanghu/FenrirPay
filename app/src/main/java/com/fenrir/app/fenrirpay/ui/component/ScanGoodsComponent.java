package com.fenrir.app.fenrirpay.ui.component;

import com.fenrir.app.fenrirpay.di.ActivityScope;
import com.fenrir.app.fenrirpay.ui.AppComponent;
import com.fenrir.app.fenrirpay.ui.fragment.ScanGoodsFragment;
import com.fenrir.app.fenrirpay.ui.module.ScanGoodsModule;
import com.fenrir.app.fenrirpay.ui.presenter.ScanGoodsPresenter;

import dagger.Component;
import dagger.Module;

/**
 * Created by DaggerGenerator on 2016/04/16.
 */
@Component(modules = ScanGoodsModule.class,
        dependencies = AppComponent.class)
@ActivityScope
public interface ScanGoodsComponent{

    public void injectFragment(ScanGoodsFragment fragment);

    public void injectPresenter(ScanGoodsPresenter presenter);

}