package com.efemoney.ussdtoolbox.data.source;

import com.efemoney.ussdtoolbox.data.model.Field;

/**
 * Created by Efe on 27/02/2017.
 */
public class FieldBuilder {

    String key;
    String type;
    String label;

    public FieldBuilder(String key) {

        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getType() {
        return type;
    }

    public FieldBuilder setType(@Field.Type String type) {
        this.type = type;
        return this;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Field build() {
        Field field = new Field();
        field.setKey(key);
        field.setType(type);
        field.setLabel(label);

        return field;
    }
}
