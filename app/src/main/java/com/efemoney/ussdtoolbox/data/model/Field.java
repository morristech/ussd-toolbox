package com.efemoney.ussdtoolbox.data.model;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Efe on 04/08/2016.
 */

public class Field {

    public static final String TYPE_NUMBER = "number";
    public static final String TYPE_BOOLEAN = "boolean";

    @Retention(RetentionPolicy.SOURCE)
    @StringDef({TYPE_NUMBER, TYPE_BOOLEAN})
    public @interface Type {
    }

    @Type
    String type;
    String key;

    Map<String, Object> data;
    int priority;

    // boolean overridesTemplate;

    public String getKey() {
        return key;
    }

    public String getType() {
        return type;
    }

    public Object getData() {
        return data;
    }

    public int getPriority() {
        return priority;
    }

    public static class Builder {

        String key;
        String type;
        int priority;
        Map<String, Object> data;

        public Builder(String key) {
            this.key = key;
        }

        public Builder setType(@Type String type) {
            this.type = type;
            return this;
        }

        public Builder setPriority(int priority) {
            this.priority = priority;
            return this;
        }

        public Builder setData(Map<String, Object> data) {
            this.data = data;
            return this;
        }

        public Builder addData(String key, Object data) {
            if (data == null) data = new HashMap<>();
            this.data.put(key, data);
            return this;
        }

        public Field build() {
            Field f = new Field();

            f.key = key;
            f.type = type;
            f.priority = priority;
            f.data = data;

            return f;
        }
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
