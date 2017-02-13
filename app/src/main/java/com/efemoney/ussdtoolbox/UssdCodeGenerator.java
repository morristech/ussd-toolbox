package com.efemoney.ussdtoolbox;

import android.support.annotation.NonNull;

import java.util.List;

import com.efemoney.ussdtoolbox.data.model.Field;
import com.efemoney.ussdtoolbox.data.model.Template;

/**
 * Created by Efe on 15/08/2016.
 */
public class UssdCodeGenerator {

    public static String generate(Template template) {

        return template.getValue();
    }

    public static String generate(Template template,
                                  @NonNull List<Field> fields,
                                  @NonNull List<Field.Value> fieldValues) {

        return template.getValue();
    }
}
