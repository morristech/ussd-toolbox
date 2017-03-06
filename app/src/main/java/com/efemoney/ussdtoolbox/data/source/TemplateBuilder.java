package com.efemoney.ussdtoolbox.data.source;

import com.efemoney.ussdtoolbox.data.model.Template;

/**
 * Created by Efe on 01/03/2017.
 */
public class TemplateBuilder {

    String key;
    String value;

    public TemplateBuilder(String key) {
        this.key = key;
    }

    public TemplateBuilder setValue(String value) {
        this.value = value;
        return this;
    }

    public Template build() {
        Template template = new Template();
        template.setKey(key);
        template.setValue(value);

        return template;
    }
}
