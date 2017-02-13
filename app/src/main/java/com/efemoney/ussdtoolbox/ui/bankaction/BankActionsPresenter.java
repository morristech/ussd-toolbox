package com.efemoney.ussdtoolbox.ui.bankaction;

import java.util.List;

import com.efemoney.ussdtoolbox.UssdCodeGenerator;
import com.efemoney.ussdtoolbox.data.BanksRepository;
import com.efemoney.ussdtoolbox.data.model.Action;
import com.efemoney.ussdtoolbox.data.model.Bank;

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

        // TODO: 29/08/2016 remove test methods
        if (bank == null) bank = repo.getTestBank();
        actionsList.showTitle(bank);

        // TODO: 29/08/2016 remove test methods
        List<Action> actions = bank.getActions();
        if (actions == null) actions = repo.getTestActions();
        actionsList.showActions(actions);

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
