package com.efemoney.ussdtoolbox.data.model;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Efe on 22/02/2017.
 */

public class ActionBuilder {

    private String key;
    private String name;
    private List<Field> fields;
    private List<Template> templates;

    public ActionBuilder(String key) {
        this.key = key;
    }

    public ActionBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public ActionBuilder setFields(@NonNull List<Field> fields) {
        this.fields = fields;
        return this;
    }

    public ActionBuilder addField(Field field) {
        if (fields == null) fields = new ArrayList<>();
        fields.add(field);
        return this;
    }

    public ActionBuilder setTemplates(List<Template> templates) {
        this.templates = templates;
        return this;
    }

    public ActionBuilder addTemplate(Template template) {
        if (templates == null) templates = new ArrayList<>();
        templates.add(template);
        return this;
    }

    public Action build() {
        Action action = new Action();
        action.setKey(key);
        action.setName(name);
        action.setFields(fields);
        action.setTemplates(templates);

        return action;
    }
}
