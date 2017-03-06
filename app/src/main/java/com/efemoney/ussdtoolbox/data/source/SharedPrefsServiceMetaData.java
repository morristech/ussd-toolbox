package com.efemoney.ussdtoolbox.data.source;

import android.content.Context;
import android.content.SharedPreferences;

import com.efemoney.ussdtoolbox.data.ServiceMetaData;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Efe on 26/02/2017.
 */

@Singleton
public class SharedPrefsServiceMetaData implements ServiceMetaData {

    private static final String CLICKS = "com.efemoney.ussdtoolbox.clicks";
    private static final String FAVES = "com.efemoney.ussdtoolbox.favorites";
    private final SharedPreferences favePrefs;
    private final SharedPreferences clicksPrefs;

    @Inject public SharedPrefsServiceMetaData(Context context) {
        this.favePrefs = context.getSharedPreferences(FAVES, Context.MODE_PRIVATE);
        this.clicksPrefs = context.getSharedPreferences(CLICKS, Context.MODE_PRIVATE);
    }

    @Override
    public boolean getFavorite(String serviceKey) {

        return favePrefs.getBoolean(serviceKey, false);
    }

    @Override
    public void toggleFavorite(String serviceKey) {

        boolean current = favePrefs.getBoolean(serviceKey, false);
        favePrefs.edit().putBoolean(serviceKey, !current).apply();
    }

    @Override
    public int getClicks(String serviceKey) {

        return clicksPrefs.getInt(serviceKey, 0);
    }

    @Override
    public void incrementClick(String serviceKey) {

        int current = clicksPrefs.getInt(serviceKey, 0);
        clicksPrefs.edit().putInt(serviceKey, current + 1).apply();
    }
}
