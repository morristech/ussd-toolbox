package com.efemoney.ussdtoolbox.base;

import android.support.annotation.StringRes;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.efemoney.ussdtoolbox.App;

public class BaseActivity extends AppCompatActivity {

    public final App getApp() {

        return (App) getApplication();
    }

    protected void setToolbar(Toolbar toolbar, boolean displayHomeAsUpEnabled) {

        setToolbar(toolbar, toolbar.getTitle(), toolbar.getSubtitle(), displayHomeAsUpEnabled);
    }

    protected void setToolbar(Toolbar toolbar, @StringRes int titleRes, @StringRes int subtitleRes,
                              boolean displayHomeAsUpEnabled) {

        CharSequence title = titleRes == 0 ? null : getString(titleRes);
        CharSequence subtitle = subtitleRes == 0 ? null : getString(subtitleRes);

        setToolbar(toolbar, title, subtitle, displayHomeAsUpEnabled);
    }

    protected void setToolbar(Toolbar toolbar, CharSequence title, CharSequence subtitle,
                              boolean displayHomeAsUpEnabled) {

        setSupportActionBar(toolbar);

        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(displayHomeAsUpEnabled);
            ab.setTitle(title);
            ab.setSubtitle(subtitle);
        }
    }
}
