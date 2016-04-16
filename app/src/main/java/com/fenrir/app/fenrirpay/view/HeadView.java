package com.fenrir.app.fenrirpay.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.IntDef;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fenrir.app.fenrirpay.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import butterknife.Bind;
import butterknife.ButterKnife;
import lombok.Getter;

/**
 * Created by yume on 16/1/18.
 */
public class HeadView extends FrameLayout {
    @Bind(R.id.base_back_layout)
    LinearLayout baseBackLayout;

    @Bind(R.id.left_menu_head_btn)
    ImageView leftMenuHeadBtn;
    @Bind(R.id.left_back_head_btn)
    ImageView leftBackHeadBtn;
    @Bind(R.id.left_close_head_btn)
    ImageView leftCloseHeadBtn;

    @Getter
    @Bind(R.id.main_title_head_tv)
    TextView titleHeadTv;

    @Bind(R.id.right_text_head_btn)
    TextView rightTextHeadBtn;
    @Bind(R.id.right_head_btn)
    ImageView rightHeadBtn;

    @Bind(R.id.shadow_back_img)
    ImageView shadowBackImg;
    @Bind(R.id.shadow_layout)
    FrameLayout shadowLayout;

    /**
     * Left Button Style
     */
    public static final int NONE  = 0;
    public static final int MENU  = 1;
    public static final int BACK  = 2;
    public static final int CLOSE  = 3;

    @IntDef({NONE, MENU, CLOSE, BACK})
    @Retention(RetentionPolicy.SOURCE)
    public @interface LeftBtn {
    }

    @HeadView.LeftBtn
    private int leftBtn = NONE;

    /**
     * Right Button Style
     */
    public static final int RELOAD = 1;

    @IntDef({NONE, RELOAD})
    @Retention(RetentionPolicy.SOURCE)
    public @interface RightBtn {
    }

    public HeadView(Context context) {
        this(context, null);
    }

    public HeadView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.head_layout, this);
        ButterKnife.bind(this);

        if (attrs != null)
            initAttr(context.obtainStyledAttributes(attrs, R.styleable.HeadView));
    }

    private void initAttr(TypedArray tArray) {
        setupLeftButton(tArray.getInt(R.styleable.HeadView_left_btn, 0));

        titleHeadTv.setText(tArray.getString(R.styleable.HeadView_title_text));

        rightTextHeadBtn.setText(tArray.getString(R.styleable.HeadView_right_text));
        rightTextHeadBtn.setTextColor(tArray.getColor(R.styleable.HeadView_right_text_color, ContextCompat.getColor(getContext(), R.color.color_gr1)));
        setupRightButton(tArray.getInt(R.styleable.HeadView_right_btn, NONE));

        shadowBackImg.setImageResource(tArray.getResourceId(R.styleable.HeadView_shadow_back, R.color.color_wh));
        if (!tArray.getBoolean(R.styleable.HeadView_show_shadow, false))
            shadowLayout.setVisibility(GONE);
    }

    public void setupLeftButton(int buttonType) {
        leftMenuHeadBtn.setVisibility(GONE);
        leftBackHeadBtn.setVisibility(GONE);
        leftCloseHeadBtn.setVisibility(GONE);
        leftMenuHeadBtn.setOnClickListener(null);
        leftBackHeadBtn.setOnClickListener(null);
        leftCloseHeadBtn.setOnClickListener(null);

        switch (buttonType) {
            case 1:
                leftMenuHeadBtn.setVisibility(VISIBLE);
                leftBtn = MENU;
                break;
            case 2:
                leftBackHeadBtn.setVisibility(VISIBLE);
                leftBtn = BACK;
                break;
            case 3:
                leftCloseHeadBtn.setVisibility(VISIBLE);
                leftBtn = CLOSE;
                break;
        }
    }

    private void setupRightButton(int buttonType) {
        switch (buttonType) {
            case RELOAD:
                rightHeadBtn.setVisibility(VISIBLE);
                rightHeadBtn.setImageResource(R.mipmap.ic_refresh);
                break;
            case NONE:
                rightHeadBtn.setVisibility(GONE);
                break;
        }
    }

    public void setLeftMenuBtnListener(OnClickListener listener) {
        leftMenuHeadBtn.setOnClickListener(listener);
    }

    public void setLeftBackBtnListener(OnClickListener listener) {
        leftBackHeadBtn.setOnClickListener(listener);
    }

    public void setLeftCloseBtnListener(OnClickListener listener) {
        leftCloseHeadBtn.setOnClickListener(listener);
    }

    public void setRightTextBtnListener(OnClickListener listener) {
        rightTextHeadBtn.setOnClickListener(listener);
    }

    public void setRightHeadBtnListener(OnClickListener listener) {
        rightHeadBtn.setOnClickListener(listener);
    }

    public void setTitleText(int textRes) {
        titleHeadTv.setText(textRes);
    }

    public void setTitleText(String title) {
        titleHeadTv.setText(title);
    }

    public void setRightBtnText(String text) {
        rightTextHeadBtn.setText(text);
    }

    public void setRightBtnTextRes(int textRes) {
        rightTextHeadBtn.setText(textRes);
    }

    public void setRightHeadBtnState(@RightBtn int state) {
        setupRightButton(state);
    }

    public void setShadowBackImg(int resId) {
        shadowBackImg.setImageResource(resId);
    }

    @LeftBtn
    public int getLeftBtnStatus() {
        return leftBtn;
    }
}
