package co.sigmoidlabs.bankussdtoolbox.model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Efe on 24/07/2016.
 */

public class DataManager {

    public static final String BANKS = "banks";

    private static DatabaseReference banks;

    DataManager() {

        banks = FirebaseDatabase.getInstance().getReference(BANKS);
    }
}
