package co.sigmoidlabs.bankussdtoolbox.data;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Efe on 24/07/2016.
 */

public class BanksRepository {

    public static final String BANKS = "banks";

    private static DatabaseReference banks;

    public BanksRepository() {

        banks = FirebaseDatabase.getInstance().getReference(BANKS);
    }
}
