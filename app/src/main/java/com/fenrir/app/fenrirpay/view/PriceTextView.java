package com.fenrir.app.fenrirpay.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.support.annotation.StyleableRes;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.fenrir.app.fenrirpay.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by yume on 16-4-16.
 */
public class PriceTextView extends FrameLayout {
    @Bind(R.id.price_unit_text_view)
    TextView priceUnitTextView;
    @Bind(R.id.price_text_view)
    TextView priceTextView;
    @Bind(R.id.divider_text_view)
    TextView dividerTextView;
    @Bind(R.id.unit_text_view)
    TextView unitTextView;

    @Getter
    @Setter
    private float price = 1;
    @Getter
    @Setter
    private int packageNum = 1;
    @Getter
    @Setter
    private String unit = "个";

    public PriceTextView(Context context) {
        this(context, null);
    }

    public PriceTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        inflate(context, R.layout.price_text_view_layout, this);
        ButterKnife.bind(this);

        if (attrs != null)
            init(context.obtainStyledAttributes(attrs, R.styleable.PriceTextView));

        updateValue();
    }

    private void init(TypedArray tArray) {
        int count = tArray.getIndexCount();
        for (int i = 0; i < count; i++)
            setAttr(tArray.getIndex(i), tArray);
    }

    private void setAttr(@StyleableRes int attrIndex, TypedArray tArray) {
        switch (attrIndex) {
            case R.styleable.PriceTextView_ptv_price:
                price = tArray.getFloat(attrIndex, -1);
                break;
            case R.styleable.PriceTextView_ptv_packageNum:
                packageNum = tArray.getInt(attrIndex, 1);
                break;
            case R.styleable.PriceTextView_ptv_unit:
                unit = tArray.getString(attrIndex);
                break;
            case R.styleable.PriceTextView_ptv_enableThruLine:
                if(tArray.getBoolean(attrIndex, false))
                    enableDeleteLine();
                break;
        }
    }

    public void enableDeleteLine() {
        priceTextView.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
    }

    public void clearData() {
        price = -1;
        updateValue();
    }

    public void updateValue() {
        if (price < 0) {
            getChildAt(0).setVisibility(INVISIBLE);
            return;
        } else {
            if(Math.abs(price) < 0.01) {
                priceUnitTextView.setVisibility(INVISIBLE);
                priceTextView.setText("免费");
            } else {
                priceUnitTextView.setVisibility(VISIBLE);
                priceTextView.setText(String.format("%.2f", price));
            }

            getChildAt(0).setVisibility(VISIBLE);
        }

        if(packageNum <= 0) {
            dividerTextView.setVisibility(INVISIBLE);
            unitTextView.setVisibility(INVISIBLE);
        } else {
            dividerTextView.setVisibility(VISIBLE);
            unitTextView.setVisibility(VISIBLE);
        }

        if(TextUtils.isEmpty(unit))
            unit = "";

        unitTextView.setText(packageNum + " " + unit);
    }
}
