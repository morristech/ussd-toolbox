package com.efemoney.ussdtoolbox.util;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;

/**
 * Created by Efe on 15/08/2016.
 */
public class DrawableUtils {

    public static Drawable tintDrawable(Drawable drawable, int bankColor) {

        drawable.mutate().setColorFilter(bankColor, PorterDuff.Mode.SRC_IN);
        // Drawable wrapped = DrawableCompat.wrap(drawable.mutate());
        // DrawableCompat.setTint(wrapped, bankColor);
        // DrawableCompat.setTintMode(wrapped, PorterDuff.Mode.SRC_IN);

        return drawable;
    }
}
