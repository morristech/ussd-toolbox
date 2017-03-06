package com.efemoney.ussdtoolbox.widget;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;

import com.efemoney.ussdtoolbox.R;
import com.efemoney.ussdtoolbox.util.FontHelper;

public class FontEditText extends AppCompatEditText {

    FontHelper mFontHelper;

    public FontEditText(Context context) {
        this(context, null);
    }

    public FontEditText(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.editTextStyle);
    }

    public FontEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mFontHelper = new FontHelper<>(this);
        mFontHelper.loadFromAttributesAndApply(context, attrs, defStyleAttr);
    }

    @Override
    public void setTextAppearance(int resId) {
        super.setTextAppearance(resId);

        if (mFontHelper != null) {
            mFontHelper.onSetTextAppearance(getContext(), resId);
        }
    }
}
