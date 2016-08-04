package co.sigmoidlabs.bankussdtoolbox.data.model;

import android.support.annotation.ColorInt;

import java.util.List;

/**
 * Created by Efe on 04/08/2016.
 */

public class Bank {

    String key;
    String name;
    @ColorInt int color;
    @ColorInt int accentColor;

    List<Action> actions;
}