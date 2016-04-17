package com.fenrir.app.fenrirpay.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fenrir.app.fenrirpay.R;
import com.fenrir.app.fenrirpay.model.api.GoodsModel;
import com.fenrir.app.fenrirpay.ui.AppComponent;
import com.fenrir.app.fenrirpay.ui.component.DaggerScanGoodsComponent;
import com.fenrir.app.fenrirpay.ui.component.ScanGoodsComponent;
import com.fenrir.app.fenrirpay.ui.module.ScanGoodsModule;
import com.fenrir.app.fenrirpay.ui.presenter.ScanGoodsPresenter;
import com.fenrir.app.fenrirpay.util.LogUtil;
import com.fenrir.app.fenrirpay.view.PriceTextView;
import com.fenrir.app.fenrirpay.view.barcode.ZXingScannerView;
import com.google.zxing.Result;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscription;

/**
 * Created by DaggerGenerator on 2016/04/16.
 */
public class ScanGoodsFragment extends BaseFragment {
    @Inject
    ScanGoodsPresenter presenter;

    @Bind(R.id.goods_scanner_view)
    ZXingScannerView scannerView;
    @Bind(R.id.continue_button)
    Button continueButton;
    @Bind(R.id.bar_code_text_view)
    TextView barCodeTextView;
    @Bind(R.id.status_text_view)
    TextView statusTextView;
    @Bind(R.id.goods_name_text_view)
    TextView goodsNameTextView;
    @Bind(R.id.sale_price_view)
    PriceTextView salePriceView;
    @Bind(R.id.cost_price_view)
    PriceTextView costPriceView;
    @Bind(R.id.count_text_view)
    TextView countTextView;
    @Bind(R.id.class_name_text_view)
    TextView classNameTextView;
    @Bind(R.id.note_text_view)
    TextView noteTextView;
    @Bind(R.id.add_cart_button)
    Button addCartButton;
    @Bind(R.id.buy_now_button)
    Button buyNowButton;

    private boolean scannerIsWorking = true;

    private Subscription searchSub;
    private GoodsModel goodsModel;

    private ZXingScannerView.ResultHandler resultHandler = new ZXingScannerView.ResultHandler() {
        @Override
        public void handleResult(Result result) {
            String barcode = result.getText();

            barCodeTextView.setText(barcode);
            statusTextView.setText("查找中");

            if (searchSub != null)
                searchSub.unsubscribe();

            searchSub = presenter.getGoodsInfoByBarCode(barcode)
                    .doOnNext(model -> statusTextView.setText(""))
                    .doOnError(model -> statusTextView.setText("查找失败"))
                    .subscribe(ScanGoodsFragment.this::setGoodsModel,
                            LogUtil::e);

            scannerView.resumeCameraPreview(resultHandler);
        }
    };

    private void setGoodsModel(GoodsModel goodsModel) {
        if (goodsModel == null) {
            addCartButton.setEnabled(false);
            buyNowButton.setEnabled(false);
            this.goodsModel = null;
            return;
        }

        goodsNameTextView.setText(goodsModel.getName());
        if(goodsModel.getPackageNum() != null)
            salePriceView.setPackageNum(goodsModel.getPackageNum().intValue());
        salePriceView.setPrice(goodsModel.getSalePrice());
        salePriceView.setUnit(goodsModel.getUnit());
        salePriceView.updateValue();

        if(goodsModel.getPackageNum() != null)
            costPriceView.setPackageNum(goodsModel.getPackageNum().intValue());
        costPriceView.setPrice(goodsModel.getCostPrice());
        costPriceView.setUnit(goodsModel.getUnit());
        costPriceView.updateValue();

        countTextView.setText(String.format("%.0f", goodsModel.getCount()));

        classNameTextView.setText(goodsModel.getClassName());
        noteTextView.setText(goodsModel.getNote());

        addCartButton.setEnabled(true);
        buyNowButton.setEnabled(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.scan_goods_fragment, container, false);
        ButterKnife.bind(this, view);

        initView();

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //init view at here.
    }

    private void initView() {
        setGoodsModel(null);

        salePriceView.clearData();
        costPriceView.clearData();

        //TODO Button
    }

    @Override
    public void onResume() {
        super.onResume();

        scannerView.setResultHandler(resultHandler);
        scannerView.startCamera();
        scannerIsWorking = true;
    }

    @Override
    public void onPause() {
        super.onPause();

        scannerView.stopCamera();
        scannerIsWorking = false;
    }

    @Override
    public void inject(AppComponent appComponent) {
        ScanGoodsComponent scanGoodsComponent = DaggerScanGoodsComponent.builder()
                .appComponent(appComponent)
                .scanGoodsModule(new ScanGoodsModule(this))
                .build();
        scanGoodsComponent.injectFragment(this);
        scanGoodsComponent.injectPresenter(presenter);
    }

    @OnClick(R.id.continue_button)
    public void onClick() {
        if (scannerIsWorking) {
            scannerIsWorking = false;
            scannerView.stopCamera();
            continueButton.setText("继续");
        } else {
            scannerIsWorking = true;
            scannerView.setResultHandler(resultHandler);
            scannerView.startCamera();
            continueButton.setText("暂停");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}