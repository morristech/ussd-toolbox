package com.efemoney.ussdtoolbox.data.source;

import com.efemoney.ussdtoolbox.data.model.Action;
import com.efemoney.ussdtoolbox.data.model.Field;
import com.efemoney.ussdtoolbox.data.model.Service;
import com.efemoney.ussdtoolbox.data.model.Template;

import java.util.List;

/**
 * Created by Efe on 27/02/2017.
 */
public interface ServiceParser<T> {

    String KEY = "key";
    String NAME = "name";
    String DESCRIPTION = "description";
    String TYPE = "type";
    String HINT = "hint";
    String COLOR = "color";
    String LABEL = "label";
    String VALUE = "value";
    String FIELDS = "fields";
    String ACTIONS = "actions";
    String TEMPLATES = "templates";
    String TEMPLATE_NO = "templateNo";
    String TEMPLATE_YES = "templateYes";
    String ACCENT_COLOR = "accentColor";

    List<Service> parseServices(T object);

    List<Action> parseActions(T object);

    List<Template> parseTemplates(T object);

    List<Field> parseFields(T object);

    Field parseField(String type, T object);
}
