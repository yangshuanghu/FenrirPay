package com.fenrir.app.fenrirpay.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;

import com.fenrir.app.fenrirpay.R;
import com.fenrir.app.fenrirpay.ui.AppComponent;
import com.fenrir.app.fenrirpay.ui.AppMessageUtil;
import com.fenrir.app.fenrirpay.ui.MainApplication;
import com.fenrir.app.fenrirpay.ui.activity.base.SlideMenuActivity;
import com.fenrir.app.fenrirpay.util.DisplayUtil;
import com.fenrir.app.fenrirpay.view.HeadView;
import com.fenrir.app.fenrirpay.view.slidemenu.SlideMenuView;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import indi.yume.tools.fragmentmanager.BaseManagerFragment;
import rx.Observable;

/**
 * Created by yume on 15/9/14.
 */
public abstract class BaseFragment extends BaseManagerFragment {
    protected final String TAG = getClass().getSimpleName();

    @Bind(R.id.head_view_layout)
    @Nullable
    HeadView headViewLayout;

    @Inject
    public AppMessageUtil appMessageUtil;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inject(MainApplication.get(getContext()).getAppComponent());
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        initHeadView();

        releaseFocus();
    }

    private void initHeadView() {
        if(headViewLayout == null)
            return;

        String customTitle = headTitle();
        if(!TextUtils.isEmpty(customTitle))
            headViewLayout.setTitleText(customTitle);

        Activity activity = getActivity();
        if(activity instanceof SlideMenuView.ProviderSlideMenu) {
            headViewLayout.setLeftMenuBtnListener(view1 -> {
                ((SlideMenuView.ProviderSlideMenu) activity).openSlideMenu();
                DisplayUtil.hideSoftKeyBoard(activity);
            });
        }

        headViewLayout.setLeftBackBtnListener(view2 -> finish());
        headViewLayout.setLeftCloseBtnListener(view2 -> finish());

        setHeadBtns(headViewLayout);

        refreshDrawerLayoutEnable();
    }

    @Override
    protected void onShow() {
        super.onShow();
        refreshDrawerLayoutEnable();
    }

    @Override
    protected void preBackResultData() {
        super.preBackResultData();
        DisplayUtil.hideSoftKeyBoard(getActivity());
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            DisplayUtil.hideSoftKeyBoard(getActivity());
        }
    }

    protected abstract void inject(AppComponent appComponent);

    protected String headTitle() {
        return "";
    }

    protected void setHeadBtns(HeadView headView){}

    protected void releaseFocus() {
        View view = getView();
        if(view != null) {
            view.setFocusable(true);
            view.setFocusableInTouchMode(true);
            view.requestFocus();
        }
    }

    protected void setOnTouchReleaseFocus() {
        View view = getView();
        if(view != null) {
            view.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    view.setFocusable(true);
                    view.setFocusableInTouchMode(true);
                    view.requestFocus();
                    return false;
                }
            });
        }
    }

    protected <T> Observable.Transformer<T, T> showNetProgressDialog(int title){
        return obs -> obs
                .doOnSubscribe(() -> appMessageUtil.showProgressDialog(getResources().getString(title)))
                .doOnError(t -> appMessageUtil.hideProgressDialog())
                .doOnCompleted(appMessageUtil::hideProgressDialog);
    }

    private void refreshDrawerLayoutEnable() {
        if(headViewLayout == null)
            return;

        switch (headViewLayout.getLeftBtnStatus()) {
            case HeadView.NONE:
            case HeadView.CLOSE:
            case HeadView.BACK:
                if (getActivity() instanceof SlideMenuActivity)
                    ((SlideMenuActivity) getActivity()).setEnableDrawer(false);
                break;
            case HeadView.MENU:
            default:
                if (getActivity() instanceof SlideMenuActivity)
                    ((SlideMenuActivity) getActivity()).setEnableDrawer(true);
                break;
        }
    }
}
