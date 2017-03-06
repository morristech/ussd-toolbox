package com.efemoney.ussdtoolbox.util;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.TextView;

import com.efemoney.ussdtoolbox.R;

public class DrawableHelper<T extends TextView> {

    private final T mView;

    private int drawableLeftTint;
    private int drawableTopTint;
    private int drawableRightTint;
    private int drawableBottomTint;

    public DrawableHelper(T view) {
        mView = view;
    }

    public void loadFromAttributesAndApply(Context context, AttributeSet attrs, int defStyleAttr) {

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DrawableHelper, defStyleAttr, 0);

        // Set collective tint
        if (a.hasValue(R.styleable.DrawableHelper_drawablesTint)) {
            drawableLeftTint = drawableTopTint = drawableRightTint = drawableBottomTint =
                    a.getColor(R.styleable.DrawableHelper_drawablesTint, 0);
        }

        // Set individual tints
        if (a.hasValue(R.styleable.DrawableHelper_drawableLeftTint)) {
            drawableLeftTint = a.getColor(R.styleable.DrawableHelper_drawableLeftTint, 0);
        }

        if (a.hasValue(R.styleable.DrawableHelper_drawableTopTint)) {
            drawableLeftTint = a.getColor(R.styleable.DrawableHelper_drawableTopTint, 0);
        }

        if (a.hasValue(R.styleable.DrawableHelper_drawableRightTint)) {
            drawableLeftTint = a.getColor(R.styleable.DrawableHelper_drawableRightTint, 0);
        }

        if (a.hasValue(R.styleable.DrawableHelper_drawableBottomTint)) {
            drawableLeftTint = a.getColor(R.styleable.DrawableHelper_drawableBottomTint, 0);
        }

        a.recycle();

        apply();
    }

    private void apply() {

        if (drawableLeftTint != 0)
            DrawableUtils.tintCompoundDrawable(DrawableUtils.LEFT, mView, drawableLeftTint);

        if (drawableTopTint != 0)
            DrawableUtils.tintCompoundDrawable(DrawableUtils.TOP, mView, drawableTopTint);

        if (drawableRightTint != 0)
            DrawableUtils.tintCompoundDrawable(DrawableUtils.RIGHT, mView, drawableRightTint);

        if (drawableBottomTint != 0)
            DrawableUtils.tintCompoundDrawable(DrawableUtils.BOTTOM, mView, drawableBottomTint);
    }
}
