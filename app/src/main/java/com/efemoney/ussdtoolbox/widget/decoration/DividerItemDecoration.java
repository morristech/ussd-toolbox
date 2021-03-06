package com.efemoney.ussdtoolbox.widget.decoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.efemoney.ussdtoolbox.R;

/**
 * Created by Efe on 13/08/2016.
 */
public class DividerItemDecoration extends RecyclerView.ItemDecoration {

    private final float height;
    private Drawable divider;

    public DividerItemDecoration(Context context) {

        int dividerColor = ContextCompat.getColor(context, R.color.divider);
        divider = new ColorDrawable(dividerColor);

        height = context.getResources().getDimension(R.dimen.divider_height);
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {

        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();

        int childCount = parent.getChildCount();

        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);

            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

            int top = child.getBottom() + params.bottomMargin;

            divider.setBounds(left, top, right, (int) (top + height));
            divider.draw(c);
        }
    }
}
