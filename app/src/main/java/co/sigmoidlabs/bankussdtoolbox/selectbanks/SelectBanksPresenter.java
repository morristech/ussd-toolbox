package co.sigmoidlabs.bankussdtoolbox.selectbanks;

import android.support.annotation.NonNull;

import java.util.ArrayList;

import co.sigmoidlabs.bankussdtoolbox.data.BanksRepository;
import co.sigmoidlabs.bankussdtoolbox.data.models.Bank;

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
        listBanksView.showBanks(createDummyBanks());
        listBanksView.showLoading(false);
    }

    @Override
    public void selectBank(@NonNull Bank bank) {
        listBanksView.showBankActions(bank);
    }


    private ArrayList<Bank> createDummyBanks() {
        ArrayList<Bank> banks = new ArrayList<>();

        Bank gtb = new Bank();
        gtb.bankLogo = "https://en.wikipedia.org/wiki/File:Guaranty_Trust_Bank.jpg";
        gtb.bankName = "GTBank";

        Bank stanbic = new Bank();
        stanbic.bankName = "Stanbic";
        stanbic.bankLogo = "https://en.wikipedia.org/wiki/File:Stanbic_IBTC_Holdings_Logo.png";

        Bank diamond = new Bank();
        diamond.bankName = "Diamond";
        diamond.bankLogo = "https://en.wikipedia.org/wiki/File:Diamond_Bank_Logo.jpg";

        banks.add(gtb);
        banks.add(stanbic);
        banks.add(diamond);

        return banks;
    }
}
