package com.efemoney.ussdtoolbox.di.module;

import android.content.Context;

import com.efemoney.ussdtoolbox.data.source.FirebaseServicesRepository;
import com.efemoney.ussdtoolbox.data.source.ServicesRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Efe on 22/02/2017.
 */
@Module @Singleton
public class AppModule {

    private Context context;

    public AppModule(Context context) {

        this.context = context.getApplicationContext();
    }

    @Provides @Singleton Context context() {

        return context;
    }

    @Provides @Singleton ServiceMetaData serviceMetaData(SharedPrefsServiceMetaData metadata) {

        return metadata;
    }

    @Provides @Singleton ServicesRepository repository(FirebaseServicesRepository repository) {

        return repository;
    }
}
