package com.fenrir.app.fenrirpay.util;

import com.fenrir.app.fenrirpay.ui.fragment.ScanGoodsFragment;

import indi.yume.tools.fragmentmanager.BaseManagerFragment;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by yume on 16/1/18.
 * <p>
 * Constants class for provide fragment when switch screen by slide menu.
 */
@AllArgsConstructor
public enum SlideMenuScreensTag {
    /**
     * 条形码扫描界面
     */
    SCAN_GOODS("scan_goods", ScanGoodsFragment.class);


    @Getter
    private String tag;
    @Getter
    private Class<? extends BaseManagerFragment> fragmentClazz;
}
