package com.efemoney.ussdtoolbox.util;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.util.Locale;

public class Utils {

    public static void hideKeyboard(Context context, View view) {
        InputMethodManager imm = (InputMethodManager)
                context.getSystemService(Context.INPUT_METHOD_SERVICE);

        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager inputManager =
                (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);

        // check if no view has focus:
        View v = activity.getCurrentFocus();

        if (v == null) return;

        inputManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    public static int dpToPxInt(Context context, int dps) {

        return Math.round(dpToPx(context, dps));
    }

    public static float dpToPx(Context context, int dps) {

        return context.getResources().getDisplayMetrics().density * dps;
    }

    public static int spToPxInt(Context context, int sps) {

        return Math.round(spToPx(context, sps));
    }

    public static float spToPx(Context context, int sps) {

        return context.getResources().getDisplayMetrics().scaledDensity * sps;
    }

    public static String format(String format, Object... args) {
        return String.format(Locale.ENGLISH, format, args);
    }
}
