package co.sigmoidlabs.bankussdtoolbox.data;

import android.graphics.Color;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import co.sigmoidlabs.bankussdtoolbox.data.model.Action;
import co.sigmoidlabs.bankussdtoolbox.data.model.Bank;

/**
 * Created by Efe on 24/07/2016.
 */

public class BanksRepository {

    private static final String BANKS = "banks";

    private static DatabaseReference banks;

    public BanksRepository() {

        banks = FirebaseDatabase.getInstance().getReference(BANKS);
    }

    public Bank getTestBank() {

        return new Bank.Builder("gtb")
                .setName("GTBank")
                .setAccent(Color.parseColor("#E04D00"))
                .setColor(Color.parseColor("#E04D00"))
                .addAction(new Action.Builder("transfer").setName("Transfer Money").build())
                .build()
        ;
    }
}
