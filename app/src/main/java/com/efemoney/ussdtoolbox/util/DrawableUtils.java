package com.efemoney.ussdtoolbox.util;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.IntDef;
import android.support.v4.content.ContextCompat;
import android.support.v7.content.res.AppCompatResources;
import android.widget.TextView;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class DrawableUtils {

    public static final int LEFT = 0;
    public static final int TOP = 1;
    public static final int RIGHT = 2;
    public static final int BOTTOM = 3;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({LEFT, TOP, RIGHT, BOTTOM})
    @interface Which{}

    public static Drawable tintDrawable(Drawable drawable,
                                        @ColorInt int color) {

        // Drawable drawable = AppCompatResources.getDrawable(context, drawableResId);
        drawable.mutate();
        drawable.setColorFilter(color, PorterDuff.Mode.SRC_IN);
        return drawable;
    }

    public static Drawable tintDrawable(Context context,
                                        @DrawableRes int drawableResId,
                                        @ColorInt int color) {

        Drawable drawable = AppCompatResources.getDrawable(context, drawableResId);
        return tintDrawable(drawable, color);
    }

    public static Drawable tintDrawableRes(Context context,
                                           Drawable drawable,
                                           @ColorRes int colorResId) {

        return tintDrawable(drawable, ContextCompat.getColor(context, colorResId));
    }

    public static Drawable tintDrawableRes(Context context,
                                           @DrawableRes int drawableResId,
                                           @ColorRes int colorResId) {

        return tintDrawable(context, drawableResId, ContextCompat.getColor(context, colorResId));
    }

    public static void tintCompoundDrawableRes(@Which int which, TextView view, @ColorRes int colorResId) {

        tintCompoundDrawable(which, view, ContextCompat.getColor(view.getContext(), colorResId));
    }

    public static void tintCompoundDrawable(@Which int which, TextView view, @ColorInt int color) {

        Drawable[] drawables = view.getCompoundDrawables();

        if (drawables[which] != null) {

            Drawable drawable = drawables[which].mutate();
            drawable.setColorFilter(color, PorterDuff.Mode.SRC_IN);

            view.setCompoundDrawables(
                    which == LEFT ? drawable : drawables[0],
                    which == TOP ? drawable : drawables[1],
                    which == RIGHT ? drawable : drawables[2],
                    which == BOTTOM ? drawable : drawables[3]
            );
        }
    }


    public static void setCompoundDrawable(@Which int which, TextView view, @DrawableRes int drawableResId) {

        Drawable d = drawableResId == 0
                ? null
                : ContextCompat.getDrawable(view.getContext(), drawableResId);

        setCompoundDrawable(which, view, d);
    }

    public static void setCompoundDrawable(@Which int which, TextView view, Drawable drawable) {

        Drawable[] drawables = view.getCompoundDrawables();
        view.setCompoundDrawablesWithIntrinsicBounds(
                which == LEFT ? drawable : drawables[0],
                which == TOP ? drawable : drawables[1],
                which == RIGHT ? drawable : drawables[2],
                which == BOTTOM ? drawable : drawables[3]
        );
    }
}
