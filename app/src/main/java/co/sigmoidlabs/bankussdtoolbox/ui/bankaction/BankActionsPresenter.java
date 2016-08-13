package co.sigmoidlabs.bankussdtoolbox.ui.bankaction;

import java.util.List;

import co.sigmoidlabs.bankussdtoolbox.data.model.Action;
import co.sigmoidlabs.bankussdtoolbox.data.model.Bank;

/**
 * Created by moyinoluwa on 7/30/16.
 */
public class BankActionsPresenter implements BankActionContract.Presenter {

    private final Bank bank;
    private final BankActionContract.View actionsList;

    public BankActionsPresenter(Bank bank, BankActionContract.View actionsList) {
        this.bank = bank;
        this.actionsList = actionsList;
    }

    @Override
    public void loadActions() {
        actionsList.showLoading(true);

        List<Action> actions = bank.getActions();
        actionsList.showActions(actions);

        actionsList.showLoading(false);
    }

    @Override
    public void selectAction(Action action) {

        actionsList.showActionFields(action);
    }
}
