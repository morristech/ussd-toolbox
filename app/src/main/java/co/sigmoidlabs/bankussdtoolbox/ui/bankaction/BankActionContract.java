package co.sigmoidlabs.bankussdtoolbox.ui.bankaction;

import java.util.List;

import co.sigmoidlabs.bankussdtoolbox.base.BasePresenter;
import co.sigmoidlabs.bankussdtoolbox.base.BaseView;
import co.sigmoidlabs.bankussdtoolbox.data.model.Action;
import co.sigmoidlabs.bankussdtoolbox.data.model.Bank;

/**
 * Created by moyinoluwa on 7/30/16.
 */
public interface BankActionContract {

    interface View extends BaseView<Presenter> {

        void showError(String message);

        void showLoading(boolean visible);

        void showTitle(Bank bank);

        void showActions(List<Action> actionList);

        void showActionFields(Action action);
    }

    interface Presenter extends BasePresenter {

        void loadActions();

        void onActionSelected(Action action);
    }
}
