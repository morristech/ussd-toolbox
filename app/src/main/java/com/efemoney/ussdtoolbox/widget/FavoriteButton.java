package com.efemoney.ussdtoolbox.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatImageButton;
import android.util.AttributeSet;

import com.efemoney.ussdtoolbox.R;

/**
 * Created by Efe on 26/02/2017.
 */

public class FavoriteButton extends AppCompatImageButton {

    private static final int[] STATE_FAVORITED = new int[]{R.attr.state_favorite};

    private boolean mFavorite;

    public FavoriteButton(Context context) {

        this(context, null);
    }

    public FavoriteButton(Context context, AttributeSet attrs) {

        this(context, attrs, android.support.v7.appcompat.R.attr.imageButtonStyle);
    }

    public FavoriteButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.FavoriteButton, defStyleAttr, 0);
        boolean isFavorite = a.getBoolean(R.styleable.FavoriteButton_favorite, false);
        a.recycle();

        setFavorite(isFavorite);
    }

    public void setFavorite(boolean favorite) {

        if (mFavorite != favorite) {
            mFavorite = favorite;
            refreshDrawableState();
        }
    }

    public boolean isFavorite() {

        return mFavorite;
    }

    @Override
    public int[] onCreateDrawableState(int extraSpace) {

        if (isFavorite()) {
            int[] sups = super.onCreateDrawableState(extraSpace + 1);
            return mergeDrawableStates(sups, STATE_FAVORITED);
        }

        return super.onCreateDrawableState(extraSpace);
    }
}
