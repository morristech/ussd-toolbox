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
        gtb.setAccentColor(Color.parseColor("#E04D00"));
        gtb.setColor(Color.parseColor("#E04D00"));
        gtb.setName("GTBank");

        Bank stanbic = new Bank();
        stanbic.setColor(Color.parseColor("#1A9AEF"));
        stanbic.setAccentColor(Color.parseColor("#1A9AEF"));
        stanbic.setName("Stanbic");

        Bank diamond = new Bank();
        diamond.setColor(Color.parseColor("#B3D619"));
        diamond.setAccentColor(Color.parseColor("#B3D619"));
        diamond.setName("Diamond");

        Bank fbn = new Bank();
        fbn.setColor(Color.parseColor("#004168"));
        fbn.setAccentColor(Color.parseColor("#004168"));
        fbn.setName("First Bank of Nigeria");

        banks.add(gtb);
        banks.add(stanbic);
        banks.add(diamond);
        banks.add(fbn);

        return banks;
    }
}
