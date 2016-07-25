package co.sigmoidlabs.bankussdtoolbox.view;

import java.util.List;

import co.sigmoidlabs.bankussdtoolbox.model.data.Field;

/**
 * Created by Efe on 25/07/2016.
 */

public interface FieldsView {


    void showField(Field field);

    void showFields(List<Field> fields);

    void hideField(Field field);

    void hideFields(List<Field> field);
}
