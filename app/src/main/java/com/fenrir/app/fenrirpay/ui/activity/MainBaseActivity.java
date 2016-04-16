package com.fenrir.app.fenrirpay.ui.activity;

import android.os.Bundle;

import com.fenrir.app.fenrirpay.R;
import com.fenrir.app.fenrirpay.ui.AppComponent;
import com.fenrir.app.fenrirpay.ui.activity.base.SlideMenuActivity;

public class MainBaseActivity extends SlideMenuActivity {
    @Override
    public int fragmentViewId() {
        return R.id.fragment_layout;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.main_base_activity);
        super.onCreate(savedInstanceState);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        }
        setIsShowAnimWhenFinish(false);
        setFragmentAnim(R.anim.fragment_enter,
                R.anim.fragment_exit);
    }

    @Override
    protected void inject(AppComponent appComponent) {

    }
}
