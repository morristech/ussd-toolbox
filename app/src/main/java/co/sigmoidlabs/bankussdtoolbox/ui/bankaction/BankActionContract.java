package co.sigmoidlabs.bankussdtoolbox.ui.bankaction;

import java.util.List;

import co.sigmoidlabs.bankussdtoolbox.base.BasePresenter;
import co.sigmoidlabs.bankussdtoolbox.base.BaseView;
import co.sigmoidlabs.bankussdtoolbox.data.model.Action;

/**
 * Created by moyinoluwa on 7/30/16.
 */
public interface BankActionContract {

    interface View extends BaseView<Presenter> {

        void showError(String message);

        void showLoading(boolean visible);

        void showActions(List<Action> actionList);

        void showActionFields(Action action);
    }

    interface Presenter extends BasePresenter {

        void loadActions();

        void selectAction(Action action);
    }
}
