package com.efemoney.ussdtoolbox.di.module;

import android.content.Context;

import com.efemoney.ussdtoolbox.data.ServiceMetaData;
import com.efemoney.ussdtoolbox.data.source.AssetJsonServicesRepository;
import com.efemoney.ussdtoolbox.data.source.ServicesRepository;
import com.efemoney.ussdtoolbox.data.source.SharedPrefsServiceMetaData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Efe on 22/02/2017.
 */
@Module
public class AppModule {

    private Context context;

    public AppModule(Context context) {

        this.context = context.getApplicationContext();
    }

    @Provides @Singleton Context context() {

        return context;
    }

    @Provides @Singleton Gson gson() {

        return new GsonBuilder().create();
    }

    @Provides @Singleton ServiceMetaData serviceMetaData(SharedPrefsServiceMetaData metadata) {

        return metadata;
    }

    @Provides @Singleton ServicesRepository repository(AssetJsonServicesRepository repository) {

        return repository;
    }
}
