package co.sigmoidlabs.bankussdtoolbox;

import android.support.annotation.NonNull;

import java.util.List;

import co.sigmoidlabs.bankussdtoolbox.data.model.Field;
import co.sigmoidlabs.bankussdtoolbox.data.model.Template;

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
