package com.efemoney.ussdtoolbox.ui.bankaction;

import java.util.List;

import com.efemoney.ussdtoolbox.base.BasePresenter;
import com.efemoney.ussdtoolbox.base.BaseView;
import com.efemoney.ussdtoolbox.data.model.Action;
import com.efemoney.ussdtoolbox.data.model.Bank;

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
