package com.efemoney.ussdtoolbox.data.model;

import android.support.annotation.ColorInt;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Efe on 22/02/2017.
 */

public class ServiceBuilder {

    private String key;
    private String name;
    private String description;
    @ColorInt private int color;
    @ColorInt private int accentColor;
    private List<Action> actions;

    public ServiceBuilder(String key) {
        this.key = key;
    }

    public ServiceBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public ServiceBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    public ServiceBuilder setColor(@ColorInt int color) {
        this.color = color;
        return this;
    }

    public ServiceBuilder setAccent(@ColorInt int accentColor) {
        this.accentColor = accentColor;
        return this;
    }

    public ServiceBuilder setActions(List<Action> actions) {
        this.actions = actions;
        return this;
    }

    public ServiceBuilder addAction(Action action) {
        if (actions == null) actions = new ArrayList<>(2);
        this.actions.add(action);
        return this;
    }

    public Service build() {
        Service service = new Service();
        service.setKey(key);
        service.setName(name);
        service.setColor(color);
        service.setAccentColor(accentColor);
        service.setActions(actions == null ? new ArrayList<>(0) : actions);

        return service;
    }
}
