package co.sigmoidlabs.bankussdtoolbox.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Efe on 04/08/2016.
 */

public class Action implements Parcelable {

    String key;
    String name;

    List<Template> templates;
    List<Field> fields;

    public Action() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.key);
        dest.writeString(this.name);
        dest.writeList(this.templates);
        dest.writeList(this.fields);
    }

    protected Action(Parcel in) {
        this.key = in.readString();
        this.name = in.readString();
        this.templates = new ArrayList<Template>();
        in.readList(this.templates, Template.class.getClassLoader());
        this.fields = new ArrayList<Field>();
        in.readList(this.fields, Field.class.getClassLoader());
    }

    public static final Creator<Action> CREATOR = new Creator<Action>() {
        @Override
        public Action createFromParcel(Parcel source) {
            return new Action(source);
        }

        @Override
        public Action[] newArray(int size) {
            return new Action[size];
        }
    };
}
