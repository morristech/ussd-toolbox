package com.efemoney.ussdtoolbox.widget;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.efemoney.ussdtoolbox.util.DrawableHelper;
import com.efemoney.ussdtoolbox.util.FontHelper;

public class FontTextView extends AppCompatTextView {

    FontHelper mFontHelper;
    DrawableHelper mDrawableHelper;

    public FontTextView(Context context) {
        this(context, null);
    }

    public FontTextView(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.textViewStyle);
    }

    public FontTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mFontHelper = new FontHelper<>(this);
        mFontHelper.loadFromAttributesAndApply(context, attrs, defStyleAttr);

        mDrawableHelper = new DrawableHelper<>(this);
        mDrawableHelper.loadFromAttributesAndApply(context, attrs, defStyleAttr);
    }

    @Override
    public void setTextAppearance(int resId) {
        super.setTextAppearance(resId);

        if (mFontHelper != null) {
            mFontHelper.onSetTextAppearance(getContext(), resId);
        }
    }
}
