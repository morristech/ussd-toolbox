package com.efemoney.ussdtoolbox.util;

import android.support.annotation.NonNull;

import com.efemoney.ussdtoolbox.data.model.Field;
import com.efemoney.ussdtoolbox.data.model.Template;

import java.util.List;

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
