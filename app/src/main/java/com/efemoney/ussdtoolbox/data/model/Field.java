package com.efemoney.ussdtoolbox.data.model;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Efe on 04/08/2016.
 */

public class Field {

    public static final String TYPE_INPUT = "input";
    public static final String TYPE_BOOLEAN = "bool";

    @Type private String type;
    private String key;
    private String label;
    private Value value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Value getValue() {
        return value;
    }

    public void setValue(Value value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Retention(RetentionPolicy.SOURCE)
    @StringDef({TYPE_INPUT, TYPE_BOOLEAN})
    public @interface Type {

    }

    public static class Value {

        @Type String type;
        Object data;

        public String getType() {
            return type;
        }

        public Object getData() {
            return data;
        }
    }
}
