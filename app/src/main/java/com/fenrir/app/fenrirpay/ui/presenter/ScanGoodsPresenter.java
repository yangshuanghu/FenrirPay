package com.fenrir.app.fenrirpay.ui.presenter;


import com.fenrir.app.fenrirpay.data.api.SearchGoodsService;
import com.fenrir.app.fenrirpay.model.api.GoodsModel;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by DaggerGenerator on 2016/04/16.
 */
public class ScanGoodsPresenter extends BasePresenter{
    @Inject
    SearchGoodsService searchGoodsService;

    public Observable<GoodsModel> getGoodsInfoByBarCode(String barcode) {
        return searchGoodsService.findGoodsItem(barcode)
                .compose(switchThread());
    }
}