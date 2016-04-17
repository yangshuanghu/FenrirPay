package com.fenrir.app.fenrirpay.data.api;

import com.fenrir.app.fenrirpay.model.api.GoodsModel;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by yume on 16/1/22.
 *
 * @author xuemao.tang
 */
public interface SearchGoodsService {
    /**
     * Api:  findGoodsItem
     * 概要:  通过条形码查询商品数据
     *
     * @param barCode (必須)   商品的条形码
     */
    @FormUrlEncoded
    @POST("api_findGoodsItem")
    public Observable<GoodsModel> findGoodsItem(
            @Field("barCode") String barCode
    );
}