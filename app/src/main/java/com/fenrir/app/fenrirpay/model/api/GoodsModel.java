package com.fenrir.app.fenrirpay.model.api;

import lombok.Data;

/**
 * Created by yume on 16-4-12.
 */
@Data
public class GoodsModel {
    private String name;
    private String barCode;
    private Float salePrice;
    private Float costPrice;
    private Float count;
    private String unit;
    private Float packageNum;
    private String note;
    private String className;
}
