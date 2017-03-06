package com.efemoney.ussdtoolbox.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.ColorInt;

import java.util.List;

/**
 * Created by Efe on 04/08/2016.
 */

public class Service implements Parcelable {

    public static final Creator<Service> CREATOR = new Creator<Service>() {
        @Override
        public Service createFromParcel(Parcel in) {
            return new Service(in);
        }

        @Override
        public Service[] newArray(int size) {
            return new Service[size];
        }
    };

    private String key;
    private String name;
    private boolean favorite;
    private @ColorInt int color;
    private @ColorInt int accentColor;
    private List<Action> actions;

    public Service() {

    }

    protected Service(Parcel in) {
        key = in.readString();
        name = in.readString();
        favorite = in.readByte() != 0;
        color = in.readInt();
        accentColor = in.readInt();
        actions = in.createTypedArrayList(Action.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(key);
        dest.writeString(name);
        dest.writeByte((byte) (favorite ? 1 : 0));
        dest.writeInt(color);
        dest.writeInt(accentColor);
        dest.writeTypedList(actions);
    }

    @Override
    public int describeContents() {
        return 0;
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

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Service && key.equals(((Service) obj).key);
    }
}