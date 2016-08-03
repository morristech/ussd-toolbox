package co.sigmoidlabs.bankussdtoolbox.data.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Bank implements Parcelable {
    public String bankName;
    public String bankLogo;

    protected Bank(Parcel in) {
        bankName = in.readString();
        bankLogo = in.readString();
    }

    public Bank() {}

    public static final Creator<Bank> CREATOR = new Creator<Bank>() {
        @Override
        public Bank createFromParcel(Parcel in) {
            return new Bank(in);
        }

        @Override
        public Bank[] newArray(int size) {
            return new Bank[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(bankName);
        parcel.writeString(bankLogo);
    }
}
