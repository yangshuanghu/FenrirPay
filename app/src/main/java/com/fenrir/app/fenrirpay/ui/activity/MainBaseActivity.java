package com.fenrir.app.fenrirpay.ui.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;

import com.fenrir.app.fenrirpay.R;
import com.fenrir.app.fenrirpay.ui.AppComponent;
import com.fenrir.app.fenrirpay.ui.activity.base.SlideMenuActivity;

public class MainBaseActivity extends SlideMenuActivity {
    private static int CAMERA_REQUEST_CODE = 200;

    @Override
    public int fragmentViewId() {
        return R.id.fragment_layout;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.main_base_activity);
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkCameraPermission();
        }
        setIsShowAnimWhenFinish(false);
        setFragmentAnim(R.anim.fragment_enter,
                R.anim.fragment_exit);
    }

    @Override
    protected void inject(AppComponent appComponent) {

    }

    @TargetApi(23)
    private void checkCameraPermission() {
        String permission = Manifest.permission.CAMERA;

        if (!isAllowPermission(permission)) {
            if (shouldShowRequestPermissionRationale(permission)) {
                // 一度ユーザーがパーミッションの要求を拒否した場合
                requestPermissions(new String[]{permission}, CAMERA_REQUEST_CODE);
            } else {
                requestPermissions(new String[]{permission}, CAMERA_REQUEST_CODE);
            }
        }
    }

    private boolean isAllowPermission(String permission) {
        return ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED;
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//
//        if (requestCode == CAMERA_REQUEST_CODE) {
//            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
//                previewPdf();
//            }
//        }
//    }
}
