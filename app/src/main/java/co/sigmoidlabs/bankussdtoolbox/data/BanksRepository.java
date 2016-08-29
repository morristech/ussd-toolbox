package co.sigmoidlabs.bankussdtoolbox.data;

import android.graphics.Color;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import co.sigmoidlabs.bankussdtoolbox.data.model.Action;
import co.sigmoidlabs.bankussdtoolbox.data.model.Bank;
import co.sigmoidlabs.bankussdtoolbox.data.model.Field;
import co.sigmoidlabs.bankussdtoolbox.data.model.Template;

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

        Bank.Builder gtbBuilder = new Bank.Builder("gtb")
                .setName("GTBank")
                .setColor(Color.parseColor("#E04D00"))
                .setAccent(Color.parseColor("#E04D00"));

        Field amount = new Field.Builder("amount").setType(Field.TYPE_NUMBER).build();
        Field accountNo = new Field.Builder("account").setType(Field.TYPE_NUMBER).build();

        Action transfer = new Action.Builder("transferMoney")
                .setName("Transfer Money")
                .addField(amount)
                .addField(accountNo)
                .addTemplate(new Template.Builder("one").setValue("*737*{amount}#").build())
                .addTemplate(new Template.Builder("two").setValue("*737*{amount}*{nuban}#").build())
                .build();

        Action balance = new Action.Builder("checkBalance")
                .setName("Check Balance")
                .addTemplate(new Template.Builder("one").setValue("*737*6#").build())
                .build()
        ;

        gtbBuilder.addAction(transfer);
        gtbBuilder.addAction(balance);

        return gtbBuilder.build();
    }

    public ArrayList<Bank> getTestBanks(int count) {
        ArrayList<Bank> banks = new ArrayList<>();
        for (int i=0; i<count; i++) {
            banks.add(getTestBank());
        }
        return banks;
    }
}
