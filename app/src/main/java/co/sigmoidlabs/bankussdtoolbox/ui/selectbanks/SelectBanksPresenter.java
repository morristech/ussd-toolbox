package co.sigmoidlabs.bankussdtoolbox.ui.selectbanks;

import android.graphics.Color;
import android.support.annotation.NonNull;

import java.util.ArrayList;

import co.sigmoidlabs.bankussdtoolbox.data.BanksRepository;
import co.sigmoidlabs.bankussdtoolbox.data.model.Bank;

/**
 * Created by moyinoluwa on 7/30/16.
 */
public class SelectBanksPresenter implements SelectBanksContract.Presenter {

    private final SelectBanksContract.View listBanksView;
    private final BanksRepository banksRepository;

    public SelectBanksPresenter(SelectBanksContract.View listBanksView,
                                BanksRepository banksRepository) {
        this.listBanksView = listBanksView;
        this.banksRepository = banksRepository;
    }

    @Override
    public void loadBanks() {
        // TODO: 03/08/2016 load banks from firebase using the repository
        listBanksView.showLoading(true);
        listBanksView.showBanks(banksRepository.getTestBanks(5));
        listBanksView.showLoading(false);
    }

    @Override
    public void selectBank(@NonNull Bank bank) {
        listBanksView.showBankActions(bank);
    }
}
