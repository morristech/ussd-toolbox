package com.efemoney.ussdtoolbox.di.component;

import com.efemoney.ussdtoolbox.data.source.ServicesRepository;
import com.efemoney.ussdtoolbox.di.module.AppModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton @Component(modules = AppModule.class)
public interface AppComponent {

    ServicesRepository repository();
}
