package com.fenrir.app.fenrirpay.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

import com.fenrir.app.fenrirpay.R;
import com.fenrir.app.fenrirpay.di.ActivityScope;
import com.fenrir.app.fenrirpay.ui.AppComponent;
import com.fenrir.app.fenrirpay.ui.component.DaggerScanGoodsComponent;
import com.fenrir.app.fenrirpay.ui.component.ScanGoodsComponent;
import com.fenrir.app.fenrirpay.ui.module.ScanGoodsModule;
import com.fenrir.app.fenrirpay.ui.presenter.ScanGoodsPresenter;

import dagger.Component;
import dagger.Module;

import javax.inject.Inject;

/**
 * Created by DaggerGenerator on 2016/04/16.
 */
public class ScanGoodsFragment extends BaseFragment{
    @Inject
    ScanGoodsPresenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.scan_goods_fragment, container, false);
        ButterKnife.bind(this, view);
        
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
            //init view at here.
    }

    @Override
    public void inject(AppComponent appComponent){
        ScanGoodsComponent scanGoodsComponent = DaggerScanGoodsComponent.builder()
            .appComponent(appComponent)
            .scanGoodsModule(new ScanGoodsModule(this))
            .build();
        scanGoodsComponent.injectFragment(this);
        scanGoodsComponent.injectPresenter(presenter);
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView(); ButterKnife.unbind(this);
    }

}