package com.efemoney.ussdtoolbox.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Efe on 04/08/2016.
 */

public class Bank implements Parcelable{

    private String key;
    private String name;
    private @ColorInt int color;
    private @ColorInt int accentColor;

    private List<Action> actions;

    public Bank() {

    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getAccentColor() {
        return accentColor;
    }

    public void setAccentColor(int accentColor) {
        this.accentColor = accentColor;
    }

    public List<Action> getActions() {
        return actions;
    }

    public void setActions(List<Action> actions) {
        this.actions = actions;
    }

    protected Bank(Parcel in) {
        key = in.readString();
        name = in.readString();
        color = in.readInt();
        accentColor = in.readInt();
    }

    public static final Creator<Bank> CREATOR = new Creator<Bank>() {
        @Override
        public Bank createFromParcel(Parcel in) {
            return new Bank(in);
        }

        @Override
        public Bank[] newArray(int size) {
            return new Bank[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(key);
        parcel.writeString(name);
        parcel.writeInt(color);
        parcel.writeInt(accentColor);
    }

    public static class Builder {

        String key;
        String name;
        @ColorInt int color;
        @ColorInt int accentColor;

        List<Action> actions;

        public Builder(String key) {
            this.key = key;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setColor(@ColorInt int color) {
            this.color = color;
            return this;
        }

        public Builder setAccent(@ColorInt int accentColor) {
            this.accentColor = accentColor;
            return this;
        }

        public Builder setActions(@NonNull List<Action> actions) {
            this.actions = actions;
            return this;
        }

        public Builder addAction(Action action) {
            if (actions == null) actions = new ArrayList<>();
            this.actions.add(action);
            return this;
        }

        public Bank build() {
            Bank bank = new Bank();
            bank.key = key;
            bank.name = name;
            bank.color = color;
            bank.accentColor = accentColor;
            bank.actions = actions;

            return bank;
        }
    }
}