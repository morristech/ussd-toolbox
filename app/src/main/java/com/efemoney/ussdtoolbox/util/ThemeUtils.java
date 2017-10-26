package com.efemoney.ussdtoolbox.util;

/**
 * Created by Efe on 28/04/2017.
 */

public class ThemeUtils {

    public static final int[] EMPTY_STATE_SET = new int[0];
    public static final int[] DISABLED_STATE_SET = new int[]{-android.R.attr.state_enabled};
    public static final int[] FOCUSED_STATE_SET = new int[]{android.R.attr.state_focused};
    public static final int[] ACTIVATED_STATE_SET = new int[]{android.R.attr.state_activated};
    public static final int[] PRESSED_STATE_SET = new int[]{android.R.attr.state_pressed};
    public static final int[] CHECKED_STATE_SET = new int[]{android.R.attr.state_checked};
    public static final int[] SELECTED_STATE_SET = new int[]{android.R.attr.state_selected};

    public static final int[] NOT_PRESSED_OR_FOCUSED_STATE_SET = new int[]{
            -android.R.attr.state_pressed,
            -android.R.attr.state_focused
    };
}
