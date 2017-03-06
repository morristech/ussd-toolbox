package com.efemoney.ussdtoolbox.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.efemoney.ussdtoolbox.R;

public class Toolbar extends android.support.v7.widget.Toolbar {

    LinearLayout titleLayout;

    private AppCompatImageView mLogoView;

    private FontTextView mTitleTextView;
    private FontTextView mSubtitleTextView;

    private int mTitleTextAppearance;
    private int mSubtitleTextAppearance;

    private CharSequence mTitleText;
    private CharSequence mSubtitleText;

    private boolean mExpanded;

    public Toolbar(Context context) {
        this(context, null);
    }

    public Toolbar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, android.support.v7.appcompat.R.attr.toolbarStyle);
    }

    @SuppressLint("PrivateResource")
    public Toolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.Toolbar, defStyleAttr, 0);

        mTitleTextAppearance = a.getResourceId(android.support.v7.appcompat.R.styleable.Toolbar_titleTextAppearance, 0);
        mSubtitleTextAppearance = a.getResourceId(android.support.v7.appcompat.R.styleable.Toolbar_subtitleTextAppearance, 0);

        setTitleTextAppearance(context, mTitleTextAppearance);
        setSubtitleTextAppearance(context, mSubtitleTextAppearance);

        a.recycle();
    }


    // TITLE METHODS
    @Override
    public CharSequence getTitle() {

        return mTitleText;
    }

    @Override
    public CharSequence getSubtitle() {

        return mSubtitleText;
    }

    private void ensureTitleLayout() {

        if (titleLayout != null && titleLayout.getParent() == this) return;

        titleLayout = new LinearLayout(getContext());
        titleLayout.setOrientation(LinearLayout.VERTICAL);
        titleLayout.setGravity(Gravity.CENTER_VERTICAL);
        titleLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_NONE);
        titleLayout.setDividerDrawable(null);
        titleLayout.setDividerPadding(0);

        LayoutParams params = generateDefaultLayoutParams();
        params.leftMargin = getCurrentContentInsetStart();

        addView(titleLayout, params);
    }

    @Override
    public void setTitle(CharSequence title) {

        if (!TextUtils.isEmpty(title)) {

            if (mTitleTextView == null) {

                final Context context = getContext();
                ensureTitleLayout();

                mTitleTextView = new FontTextView(context);
                mTitleTextView.setSingleLine();
                mTitleTextView.setEllipsize(TextUtils.TruncateAt.END);

                if (mTitleTextAppearance != 0) {
                    mTitleTextView.setTextAppearance(context, mTitleTextAppearance);
                }

                LinearLayoutCompat.LayoutParams p = new LinearLayoutCompat.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                titleLayout.addView(mTitleTextView, 0, p);
            }

        } else {

            if (mTitleTextView != null) titleLayout.removeView(mTitleTextView);
        }

        if (mTitleTextView != null) mTitleTextView.setText(title);

        mTitleText = title;
    }

    @Override
    public void setSubtitle(CharSequence subtitle) {

        if (!TextUtils.isEmpty(subtitle)) {

            if (mSubtitleTextView == null) {

                final Context context = getContext();
                ensureTitleLayout();

                mSubtitleTextView = new FontTextView(context);
                mSubtitleTextView.setSingleLine();
                mSubtitleTextView.setGravity(Gravity.CENTER);
                mSubtitleTextView.setEllipsize(TextUtils.TruncateAt.END);

                if (mSubtitleTextAppearance != 0) {
                    mSubtitleTextView.setTextAppearance(context, mSubtitleTextAppearance);
                }

                LinearLayoutCompat.LayoutParams p = new LinearLayoutCompat.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                titleLayout.addView(mSubtitleTextView, p);
            }

        } else {

            if (mSubtitleTextView != null) titleLayout.removeView(mSubtitleTextView);
        }

        if (mSubtitleTextView != null) mSubtitleTextView.setText(subtitle);

        mSubtitleText = subtitle;
    }

    @Override
    public void setTitleTextAppearance(Context context, @StyleRes int resId) {
        mTitleTextAppearance = resId;
        if (mTitleTextView != null) {
            mTitleTextView.setTextAppearance(context, resId);
        }
    }

    @Override
    public void setTitleTextColor(@ColorInt int color) {
        if (mTitleTextView != null) {
            mTitleTextView.setTextColor(color);
        }
    }

    @Override
    public void setSubtitleTextAppearance(Context context, @StyleRes int resId) {
        mSubtitleTextAppearance = resId;
        if (mSubtitleTextView != null) {
            mSubtitleTextView.setTextAppearance(context, resId);
        }
    }

    @Override
    public void setSubtitleTextColor(@ColorInt int color) {
        if (mSubtitleTextView != null) {
            mSubtitleTextView.setTextColor(color);
        }
    }


    // LOGO METHODS
    @Override
    public Drawable getLogo() {

        return mLogoView != null ? mLogoView.getDrawable() : null;
    }

    @Override
    public void setLogo(Drawable drawable) {

        if (drawable != null) {
            if (mLogoView == null) {
                mLogoView = new AppCompatImageView(getContext());
                mLogoView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                mLogoView.setAdjustViewBounds(true);
            }

            LayoutParams params = new LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.CENTER_VERTICAL
            );
            params.rightMargin = getContentInsetStart();

            addView(mLogoView, params);

        } else {

            if (mLogoView != null) removeView(mLogoView);
        }

        if (mLogoView != null) {
            mLogoView.setImageDrawable(drawable);
        }
    }

    @Override
    public void setLogoDescription(CharSequence description) {

        if (!TextUtils.isEmpty(description)) {
            if (mLogoView == null) {
                mLogoView = new AppCompatImageView(getContext());
                mLogoView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                mLogoView.setAdjustViewBounds(true);
            }
        }

        if (mLogoView != null) {
            mLogoView.setContentDescription(description);
        }
    }

    @Override
    public CharSequence getLogoDescription() {

        return mLogoView != null ? mLogoView.getContentDescription() : null;
    }

    public boolean onMenuItemActionExpand(MenuItem item) {
        mExpanded = true;
        if (mLogoView != null && mLogoView.getParent() == this) {
            removeView(mLogoView);
        }
        return true;
    }

    public boolean onMenuItemActionCollapse(MenuItem item) {
        mExpanded = false;
        if (mLogoView != null) {
            addView(mLogoView, mLogoView.getLayoutParams());
        }
        return true;
    }
}
