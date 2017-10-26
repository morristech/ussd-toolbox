package com.efemoney.ussdtoolbox.widget;

import android.content.Context;
import android.support.v7.widget.AppCompatRadioButton;
import android.util.AttributeSet;

import com.efemoney.ussdtoolbox.R;
import com.efemoney.ussdtoolbox.util.DrawableHelper;
import com.efemoney.ussdtoolbox.util.FontHelper;

public class FontRadioButton extends AppCompatRadioButton {

    FontHelper mFontHelper;

    public FontRadioButton(Context context) {
        this(context, null);
    }

    public FontRadioButton(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.radioButtonStyle);
    }

    public FontRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mFontHelper = new FontHelper<>(this);
        mFontHelper.loadFromAttributesAndApply(context, attrs, defStyleAttr);

        new DrawableHelper<>(this).loadFromAttributesAndApply(context, attrs, defStyleAttr);
    }

    @Override
    public void setTextAppearance(int resId) {
        super.setTextAppearance(resId);

        if (mFontHelper != null) {
            mFontHelper.onSetTextAppearance(getContext(), resId);
        }
    }
}