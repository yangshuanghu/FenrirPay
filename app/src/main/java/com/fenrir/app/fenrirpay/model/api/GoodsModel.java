package com.fenrir.app.fenrirpay.model.api;

import lombok.Data;

/**
 * Created by yume on 16-4-12.
 */
@Data
public class GoodsModel {
    private String name;
    private String barCode;
    private float salePrice;
    private float costPrice;
    private int count;
    private String unit;
    private int packageNum;
    private String note;
    private String className;
}
