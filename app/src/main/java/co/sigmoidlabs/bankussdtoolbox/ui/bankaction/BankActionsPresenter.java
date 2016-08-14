package co.sigmoidlabs.bankussdtoolbox.ui.bankaction;

import java.util.List;

import co.sigmoidlabs.bankussdtoolbox.data.BanksRepository;
import co.sigmoidlabs.bankussdtoolbox.data.model.Action;
import co.sigmoidlabs.bankussdtoolbox.data.model.Bank;

/**
 * Created by moyinoluwa on 7/30/16.
 */
public class BankActionsPresenter implements BankActionContract.Presenter {

    private Bank bank;
    private BanksRepository repo;
    private BankActionContract.View actionsList;

    public BankActionsPresenter(Bank bank, BankActionContract.View actionsList) {
        this.bank = bank;
        this.actionsList = actionsList;

        repo = new BanksRepository();
    }

    @Override
    public void loadActions() {
        actionsList.showLoading(true);

        if (bank == null) bank = repo.getTestBank();

        List<Action> actions = bank.getActions();
        actionsList.showActions(actions);

        actionsList.showLoading(false);
    }

    @Override
    public void selectAction(Action action) {

        actionsList.showActionFields(action);
    }
}
