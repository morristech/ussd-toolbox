package com.efemoney.ussdtoolbox.data.model;

/**
 * Created by Efe on 04/08/2016.
 */

public class Template {

    String key;
    String value;

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public static class Builder {

        String key;
        String value;

        public Builder (String key) {
            this.key = key;
        }

        public Builder setValue(String value) {
            this.value = value;
            return this;
        }

        public Template build() {
            Template template = new Template();
            template.key = key;
            template.value = value;

            return template;
        }
    }
}

