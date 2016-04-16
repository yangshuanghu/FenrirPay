package com.fenrir.app.fenrirpay.ui.activity.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.KeyEvent;
import android.widget.Toast;

import com.fenrir.app.fenrirpay.R;
import com.fenrir.app.fenrirpay.view.slidemenu.SlideMenuView;
import static com.fenrir.app.fenrirpay.util.SlideMenuScreensTag.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import indi.yume.tools.fragmentmanager.BaseManagerFragment;


/**
 * Created by yume on 16/1/18.
 *
 * Base class for activities that has slide menu.
 * subclass must have a SlideMenuView(id = slide_menu_view) and a DrawerLayout(id = drawer_layout) at layout xml file.
 */
public abstract class SlideMenuActivity extends BaseFragmentActivity implements SlideMenuView.ProviderSlideMenu {
    @Bind(R.id.slide_menu_view)
    SlideMenuView slideMenuView;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @Override
    public Map<String, Class<? extends BaseManagerFragment>> BaseFragmentWithTag() {
        Map<String, Class<? extends BaseManagerFragment>> map = new HashMap<>();
        map.put(SCAN_GOODS.getTag(), SCAN_GOODS.getFragmentClazz());

        return map;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        slideMenuView.setClickListener(view -> drawerLayout.closeDrawers());

        switchToStackByTag(SCAN_GOODS.getTag());
    }

    public void setEnableDrawer(boolean enable) {
        if(enable)
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        else
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    @Override
    public void closeSlideMenu() {
        drawerLayout.closeDrawer(GravityCompat.START);
    }

    @Override
    public void openSlideMenu() {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    @Override
    public void toggleSlidingMenu() {
        if(drawerLayout.isDrawerOpen(drawerLayout))
            closeSlideMenu();
        else
            openSlideMenu();
    }
}
