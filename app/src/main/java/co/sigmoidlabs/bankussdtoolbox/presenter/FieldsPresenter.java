package co.sigmoidlabs.bankussdtoolbox.presenter;

import java.util.List;

import co.sigmoidlabs.bankussdtoolbox.model.data.Field;

/**
 * Created by Efe on 24/07/2016.
 */

public interface FieldsPresenter {

    <T> void onGenerateUSSDCode(List<Field.Value> values);
}
