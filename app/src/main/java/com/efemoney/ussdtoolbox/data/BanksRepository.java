package com.efemoney.ussdtoolbox.data;

import android.graphics.Color;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import com.efemoney.ussdtoolbox.data.model.Action;
import com.efemoney.ussdtoolbox.data.model.Bank;
import com.efemoney.ussdtoolbox.data.model.Field;
import com.efemoney.ussdtoolbox.data.model.Template;

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

        gtbBuilder.setActions(getTestActions());

        return gtbBuilder.build();
    }

    public List<Bank> getTestBanks(int count) {
        ArrayList<Bank> banks = new ArrayList<>(count);
        for (int i=0; i<count; i++) {
            banks.add(getTestBank());
        }
        return banks;
    }

    public List<Action> getTestActions() {

        List<Action> actions = new ArrayList<>(2);

        Action.Builder transferBuilder = new Action.Builder("transferMoney")
                .setName("Transfer Money")
                .addTemplate(new Template.Builder("one").setValue("*737*{amount}#").build())
                .addTemplate(new Template.Builder("two").setValue("*737*{amount}*{nuban}#").build());

        transferBuilder.setFields(getTestFields());

        Action transfer = transferBuilder.build();

        Action balance = new Action.Builder("checkBalance")
                .setName("Check Balance")
                .addTemplate(new Template.Builder("one").setValue("*737*6#").build())
                .build();

        actions.add(transfer);
        actions.add(balance);

        return actions;
    }

    public List<Field> getTestFields() {

        List<Field> fields = new ArrayList<>(2);

        Field amount = new Field.Builder("amount").setType(Field.TYPE_NUMBER).build();
        Field accountNo = new Field.Builder("account").setType(Field.TYPE_NUMBER).build();

        fields.add(amount);
        fields.add(accountNo);

        return fields;
    }
}
