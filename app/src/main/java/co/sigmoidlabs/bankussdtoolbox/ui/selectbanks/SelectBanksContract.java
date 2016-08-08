package co.sigmoidlabs.bankussdtoolbox.ui.selectbanks;

import java.util.List;

import co.sigmoidlabs.bankussdtoolbox.base.BasePresenter;
import co.sigmoidlabs.bankussdtoolbox.base.BaseView;
import co.sigmoidlabs.bankussdtoolbox.data.model.Bank;

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
