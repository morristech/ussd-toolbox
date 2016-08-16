package co.sigmoidlabs.bankussdtoolbox.util;

import android.graphics.Color;
import android.support.annotation.CheckResult;
import android.support.annotation.ColorInt;
import android.support.annotation.FloatRange;
import android.support.annotation.IntRange;

/**
 * Utility methods for working with colors.
 *
 * Created by Efe on 15/08/2016.
 */
public class ColorUtils {

    private ColorUtils() {}

    /**
     * Set the alpha component of {@code color} to be {@code alpha}.
     */
    public static @CheckResult @ColorInt int modifyAlpha(@ColorInt int color,
                                                         @IntRange(from = 0, to = 255) int alpha) {
        return (color & 0x00ffffff) | (alpha << 24);
    }

    /**
     * Set the alpha component of {@code color} to be {@code alpha}.
     */
    public static @CheckResult @ColorInt int modifyAlpha(@ColorInt int color,
                                                         @FloatRange(from = 0f, to = 1f) float alpha) {
        return modifyAlpha(color, (int) (255f * alpha));
    }

    /**
     * Blend {@code color1} and {@code color2} using the given ratio.
     *
     * @param ratio of which to blend. 0.0 will return {@code color1}, 0.5 will give an even blend,
     *              1.0 will return {@code color2}.
     */
    public static @CheckResult @ColorInt int blendColors(@ColorInt int color1,
                                                         @ColorInt int color2,
                                                         @FloatRange(from = 0f, to = 1f) float ratio) {
        final float inverseRatio = 1f - ratio;
        float a = (Color.alpha(color1) * inverseRatio) + (Color.alpha(color2) * ratio);
        float r = (Color.red(color1) * inverseRatio) + (Color.red(color2) * ratio);
        float g = (Color.green(color1) * inverseRatio) + (Color.green(color2) * ratio);
        float b = (Color.blue(color1) * inverseRatio) + (Color.blue(color2) * ratio);
        return Color.argb((int) a, (int) r, (int) g, (int) b);
    }

    /**
     * Convert to HSL & check the lightness value
     */
    public static boolean isDark(@ColorInt int color) {
        float[] hsl = new float[3];
        android.support.v4.graphics.ColorUtils.colorToHSL(color, hsl);
        return isDark(hsl);
    }

    /**
     * Check that the lightness value (0–1)
     */
    public static boolean isDark(float[] hsl) { // @Size(3)
        return hsl[2] < 0.5f;
    }

    /**
     * Calculate a variant of the color to make it more suitable for overlaying information. Light
     * colors will be lightened and dark colors will be darkened
     *
     * @param color the color to adjust
     * @param isDark whether {@code color} is light or dark
     * @param lightnessMultiplier the amount to modify the color e.g. 0.1f will alter it by 10%
     * @return the adjusted color
     */
    public static @ColorInt int scrimify(@ColorInt int color,
                                         boolean isDark,
                                         @FloatRange(from = 0f, to = 1f) float lightnessMultiplier) {
        float[] hsl = new float[3];
        android.support.v4.graphics.ColorUtils.colorToHSL(color, hsl);

        if (!isDark) {
            lightnessMultiplier += 1f;
        } else {
            lightnessMultiplier = 1f - lightnessMultiplier;
        }


        hsl[2] = MathUtils.constrain(0f, 1f, hsl[2] * lightnessMultiplier);
        return android.support.v4.graphics.ColorUtils.HSLToColor(hsl);
    }

    public static @ColorInt int scrimify(@ColorInt int color,
                                         @FloatRange(from = 0f, to = 1f) float lightnessMultiplier) {
        return scrimify(color, isDark(color), lightnessMultiplier);
    }

    /**
     * Returns a color suitable for us as the status bar color
     */
    public static @ColorInt int getPrimaryDark(@ColorInt int color) {

        // Modify black with 30% alpha and blend the results
        return blendColors(color, modifyAlpha(0, 0.3f), 0.5f);
    }
}
