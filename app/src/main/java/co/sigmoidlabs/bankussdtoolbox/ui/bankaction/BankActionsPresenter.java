package co.sigmoidlabs.bankussdtoolbox.ui.bankaction;

import co.sigmoidlabs.bankussdtoolbox.UssdCodeGenerator;
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

    public BankActionsPresenter(Bank bank, BankActionContract.View view) {
        this.bank = bank;
        this.actionsList = view;

        repo = new BanksRepository();
    }

    @Override
    public void loadActions() {
        actionsList.showLoading(true);

        if (bank == null) bank = repo.getTestBank();

        actionsList.showTitle(bank);
        actionsList.showActions(bank.getActions());

        actionsList.showLoading(false);
    }

    @Override
    public void onActionSelected(Action action) {

        if (action.getFields() == null || action.getFields().size() == 0) {

            String ussd = UssdCodeGenerator.generate(action.getTemplates().get(0));
            actionsList.showUssdCode(bank.getColor(), action.getName(), ussd);

        } else {

            actionsList.showActionFields(action);
        }
    }
}
