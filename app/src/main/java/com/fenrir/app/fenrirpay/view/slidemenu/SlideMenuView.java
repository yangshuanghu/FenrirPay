package com.fenrir.app.fenrirpay.view.slidemenu;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.fenrir.app.fenrirpay.R;
import com.fenrir.app.fenrirpay.util.SlideMenuScreensTag;
import com.orhanobut.logger.Logger;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import indi.yume.tools.fragmentmanager.BaseFragmentManagerActivity;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by yume on 16/1/15.
 */
public class SlideMenuView extends FrameLayout {

    @Bind(R.id.search_item_view)
    MenuItemView searchItemView;
    @Bind(R.id.new_jobs_item_view)
    MenuItemView newJobsItemView;
    @Bind(R.id.keep_item_view)
    MenuItemView keepItemView;
    @Bind(R.id.check_item_view)
    MenuItemView checkItemView;
    @Bind(R.id.resume_item_view)
    MenuItemView resumeItemView;
    @Bind(R.id.info_item_view)
    MenuItemView infoItemView;

    private BaseFragmentManagerActivity activity;
    private OnClickListener listener;

    public SlideMenuView(Context context) {
        this(context, null);
    }

    public SlideMenuView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.sile_menu_layout, this);

        if (!isInEditMode())
            activity = (BaseFragmentManagerActivity) context;

        ButterKnife.bind(this);

        searchItemView.setSelect(true);
    }

    public void setClickListener(OnClickListener listener) {
        this.listener = listener;
    }

    @OnClick({R.id.search_item_view,
            R.id.new_jobs_item_view,
            R.id.keep_item_view,
            R.id.check_item_view,
            R.id.resume_item_view,
            R.id.info_item_view})
    void onMenuItemClick(View view) {
        searchItemView.setSelect(false);
        newJobsItemView.setSelect(false);
        keepItemView.setSelect(false);
        checkItemView.setSelect(false);
        resumeItemView.setSelect(false);
        infoItemView.setSelect(false);

        switch (view.getId()) {
            case R.id.search_item_view:
                searchItemView.setSelect(true);
                activity.switchToStackByTag(SlideMenuScreensTag.SCAN_GOODS.getTag());
                break;
        }

        if (listener != null)
            listener.onClick(view);
    }

    public interface OnClickListener {
        void onClick(View view);
    }

    public interface ProviderSlideMenu {
        void closeSlideMenu();

        void openSlideMenu();

        void toggleSlidingMenu();
    }
}
