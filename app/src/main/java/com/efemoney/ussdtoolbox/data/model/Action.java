package com.efemoney.ussdtoolbox.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Efe on 04/08/2016.
 */

public class Action implements Parcelable {

    private String key;
    private String name;
    private List<Field> fields;
    private List<Template> templates;

    public Action() {
    }

    protected Action(Parcel in) {
        key = in.readString();
        name = in.readString();
    }



    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(key);
        dest.writeString(name);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Action> CREATOR = new Creator<Action>() {
        @Override
        public Action createFromParcel(Parcel in) {
            return new Action(in);
        }

        @Override
        public Action[] newArray(int size) {
            return new Action[size];
        }
    };


    public void setKey(String key) {
        this.key = key;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public List<Field> getFields() {
        return fields;
    }

    public List<Template> getTemplates() {
        return templates;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }

    public void setTemplates(List<Template> templates) {
        this.templates = templates;
    }
}
