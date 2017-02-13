package com.efemoney.ussdtoolbox.data.model;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Efe on 04/08/2016.
 */

public class Action {

    String key;
    String name;
    List<Field> fields;
    List<Template> templates;

    public Action() {
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

    public static class Builder {

        String key;
        String name;
        List<Field> fields;
        List<Template> templates;

        public Builder(String key) {
            this.key = key;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setFields(@NonNull List<Field> fields) {
            this.fields = fields;
            return this;
        }

        public Builder addField(Field field) {
            if (fields == null) fields = new ArrayList<>();
            fields.add(field);
            return this;
        }

        public Builder setTemplates(List<Template> templates) {
            this.templates = templates;
            return this;
        }

        public Builder addTemplate(Template template) {
            if (templates == null) templates = new ArrayList<>();
            templates.add(template);
            return this;
        }

        public Action build() {
            Action action = new Action();
            action.key = key;
            action.name = name;
            action.fields = fields;
            action.templates = templates;

            return action;
        }
    }
}
