package com.fenrir.app.fenrirpay.view.slidemenu;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.fenrir.app.fenrirpay.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by yume on 16-2-6.
 */
public class MenuItemView extends FrameLayout {
    @Bind(R.id.slide_menu_item_icon)
    ImageView slideMenuItemIcon;
    @Bind(R.id.slide_menu_item_tv)
    TextView slideMenuItemTv;
    @Bind(R.id.slide_item_back_layout)
    FrameLayout slideItemBackLayout;

    public MenuItemView(Context context) {
        super(context);
    }

    public MenuItemView(Context context, AttributeSet attrs) {
        super(context, attrs);

        inflate(context, R.layout.slide_menu_item_layout, this);
        ButterKnife.bind(this);

        if(attrs != null)
            initAttrs(context.obtainStyledAttributes(attrs, R.styleable.MenuItemView));
    }

    private void initAttrs(TypedArray tArray) {
        slideItemBackLayout.setBackgroundResource(
                tArray.getResourceId(R.styleable.MenuItemView_item_back, R.color.color_wh));

        ColorStateList colors = tArray.getColorStateList(R.styleable.MenuItemView_text_color);
        if(colors != null)
            slideMenuItemTv.setTextColor(colors);
        else
            slideMenuItemTv.setTextColor(tArray.getColor(R.styleable.MenuItemView_text_color, Color.WHITE));

        slideMenuItemIcon.setImageResource(tArray.getResourceId(R.styleable.MenuItemView_icon_src, 0));
        slideMenuItemTv.setText(tArray.getString(R.styleable.MenuItemView_item_text));
    }

    public void setSelect(boolean isSelect) {
        slideMenuItemIcon.setSelected(isSelect);
        slideMenuItemTv.setSelected(isSelect);
        slideItemBackLayout.setSelected(isSelect);
    }
}
