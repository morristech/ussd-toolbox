package com.efemoney.ussdtoolbox;

import android.app.Application;

import com.efemoney.ussdtoolbox.di.component.AppComponent;
import com.efemoney.ussdtoolbox.di.component.DaggerAppComponent;
import com.efemoney.ussdtoolbox.di.module.AppModule;

/**
 * Created by Efe on 22/02/2017.
 */

public class App extends Application {

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public AppComponent getAppComponent() {

        return appComponent;
    }
}
