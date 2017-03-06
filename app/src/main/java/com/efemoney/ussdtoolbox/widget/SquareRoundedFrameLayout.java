package com.efemoney.ussdtoolbox.widget;

import android.content.Context;
import android.util.AttributeSet;

public class SquareRoundedFrameLayout extends RoundedFrameLayout {

    public SquareRoundedFrameLayout(Context context) {

        super(context);
    }

    public SquareRoundedFrameLayout(Context context, AttributeSet attrs) {

        super(context, attrs);
    }

    public SquareRoundedFrameLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}
