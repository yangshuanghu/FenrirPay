package com.fenrir.app.fenrirpay.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import me.dm7.barcodescanner.zxing.ZXingScannerView;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

import com.fenrir.app.fenrirpay.R;
import com.fenrir.app.fenrirpay.ui.AppComponent;
import com.fenrir.app.fenrirpay.ui.component.DaggerScanGoodsComponent;
import com.fenrir.app.fenrirpay.ui.component.ScanGoodsComponent;
import com.fenrir.app.fenrirpay.ui.module.ScanGoodsModule;
import com.fenrir.app.fenrirpay.ui.presenter.ScanGoodsPresenter;
import com.fenrir.app.fenrirpay.util.LogUtil;
import com.google.zxing.Result;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

/**
 * Created by DaggerGenerator on 2016/04/16.
 */
public class ScanGoodsFragment extends BaseFragment{
    @Inject
    ScanGoodsPresenter presenter;

    private ZXingScannerView scannerView;
    private ZXingScannerView.ResultHandler resultHandler = new ZXingScannerView.ResultHandler() {
        @Override
        public void handleResult(Result result) {
            LogUtil.m(this, "getText:", result.getText());
            LogUtil.m(this, "getBarcodeFormat:", result.getBarcodeFormat().toString());

            scannerView.resumeCameraPreview(resultHandler);
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
//        View view = inflater.inflate(R.layout.scan_goods_fragment, container, false);
//        ButterKnife.bind(this, view);

        scannerView = new ZXingScannerView(getActivity());

        return scannerView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
            //init view at here.
    }

    @Override
    public void onResume() {
        super.onResume();

        scannerView.setResultHandler(resultHandler);
        scannerView.startCamera();
    }

    @Override
    public void onPause() {
        super.onPause();

        scannerView.stopCamera();
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
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}