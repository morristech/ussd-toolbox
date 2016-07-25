package co.sigmoidlabs.bankussdtoolbox.presenter;

import java.util.List;

import co.sigmoidlabs.bankussdtoolbox.model.data.Bank;

/**
 * Created by Efe on 24/07/2016.
 */

public interface BanksPresenter {

    void onSelectBanks(List<Bank> selectedBanks);
}
