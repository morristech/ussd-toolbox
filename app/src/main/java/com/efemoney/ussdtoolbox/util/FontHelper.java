package com.efemoney.ussdtoolbox.util;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.efemoney.ussdtoolbox.R;

public class FontHelper<T extends TextView> {

    private final T mView;

    private String font = FontUtil.DEFAULT;
    private int style = Typeface.NORMAL;

    public FontHelper(T view) {
        mView = view;
    }

    public void loadFromAttributesAndApply(Context context, AttributeSet attrs, int defStyleAttr) {

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.FontHelper, defStyleAttr, 0);

        if (a.hasValue(R.styleable.FontHelper_android_textAppearance)) {

            // Get id of the resource that holds the textAppearance
            final int resId = a.getResourceId(R.styleable.FontHelper_android_textAppearance, 0);

            TypedArray atp = context.obtainStyledAttributes(resId, R.styleable.FontTextAppearance);

            if (atp.hasValue(R.styleable.FontTextAppearance_font)) {
                font = atp.getString(R.styleable.FontTextAppearance_font);
            }

            if (atp.hasValue(R.styleable.FontTextAppearance_android_textStyle)) {
                style = atp.getInt(R.styleable.FontTextAppearance_android_textStyle, Typeface.NORMAL);
            }

            atp.recycle();
        }

        if (a.hasValue(R.styleable.FontHelper_font)) {
            font = a.getString(R.styleable.FontHelper_font);
        }

        if (a.hasValue(R.styleable.FontHelper_android_textStyle)) {
            style = a.getInt(R.styleable.FontHelper_android_textStyle, style);
        }

        a.recycle();

        applyFont();
    }

    public void onSetTextAppearance(Context context, int resId) {

        TypedArray atp = context.obtainStyledAttributes(resId, R.styleable.FontTextAppearance);

        if (atp.hasValue(R.styleable.FontTextAppearance_font)) {
            font = atp.getString(R.styleable.FontTextAppearance_font);
        }

        if (atp.hasValue(R.styleable.FontTextAppearance_android_textStyle)) {
            style = atp.getInt(R.styleable.FontTextAppearance_android_textStyle, Typeface.NORMAL);
        }

        atp.recycle();

        applyFont();
    }

    private void applyFont() {

        Typeface tfc = FontUtil.get(mView.getContext(), font, style);

        if (tfc != null) {
            mView.setTypeface(tfc);
            mView.setPaintFlags(mView.getPaintFlags() | Paint.ANTI_ALIAS_FLAG);
        }
    }
}
