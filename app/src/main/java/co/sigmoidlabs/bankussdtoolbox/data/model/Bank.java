package co.sigmoidlabs.bankussdtoolbox.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.ColorInt;

import java.util.List;

/**
 * Created by Efe on 04/08/2016.
 */

public class Bank implements Parcelable{

    String key;
    String name;
    @ColorInt int color;
    @ColorInt int accentColor;

    List<Action> actions;

    protected Bank(Parcel in) {
        key = in.readString();
        name = in.readString();
        color = in.readInt();
        accentColor = in.readInt();
    }

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
        parcel.writeString(key);
        parcel.writeString(name);
        parcel.writeInt(color);
        parcel.writeInt(accentColor);
    }
}