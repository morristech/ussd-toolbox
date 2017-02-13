package com.efemoney.ussdtoolbox.ui.selectbanks;

import java.util.List;

import com.efemoney.ussdtoolbox.base.BasePresenter;
import com.efemoney.ussdtoolbox.base.BaseView;
import com.efemoney.ussdtoolbox.data.model.Bank;

/**
 * Created by moyinoluwa on 7/30/16.
 */
public interface SelectBanksContract {

    interface View extends BaseView<Presenter> {
        void showLoading(boolean visible);

        void showError(String message);

        void showBanks(List<Bank> bankList);

        void showBankActions(Bank bank);
    }

    interface Presenter extends BasePresenter {
        void loadBanks();

        void selectBank(Bank bank);
    }
}
