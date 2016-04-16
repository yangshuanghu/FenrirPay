package com.fenrir.app.fenrirpay.ui.presenter;

import android.support.annotation.NonNull;

import com.fenrir.app.fenrirpay.ui.AppMessageUtil;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by yume on 15/9/16.
 */
public abstract class BasePresenter {
    @Inject
    public AppMessageUtil appMessageUtil;

    protected <T> Observable.Transformer<T, T> switchThread(){
        return obs -> obs.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    protected <T> Observable.Transformer<T, T> showNetProgressDialog(@NonNull String title){
        return obs -> obs
                .doOnSubscribe(() -> appMessageUtil.showProgressDialog(title))
                .doOnError(t -> appMessageUtil.hideProgressDialog())
                .doOnCompleted(appMessageUtil::hideProgressDialog);
    }
}
