package com.efemoney.ussdtoolbox.ui.debug;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;

/**
 * Created by Efe on 29/04/2017.
 */

public class DebugOptionsDialog extends BottomSheetDialog {

    public DebugOptionsDialog(@NonNull Context context) {
        super(context);
    }

    public DebugOptionsDialog(@NonNull Context context, int theme) {
        super(context, theme);
    }

    protected DebugOptionsDialog(@NonNull Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }
}
