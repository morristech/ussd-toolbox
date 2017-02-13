package com.efemoney.ussdtoolbox.widget;

import android.content.Context;
import android.util.AttributeSet;

public class SquareRoundedLinearLayout extends RoundedLinearLayout {
    public SquareRoundedLinearLayout(Context context) {
        super(context);
    }

    public SquareRoundedLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareRoundedLinearLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}
