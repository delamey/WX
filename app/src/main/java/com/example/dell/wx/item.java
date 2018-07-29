package com.example.dell.wx;

import android.os.Parcel;
import android.os.Parcelable;

public class item implements Parcelable {
    private int id;
    private String name;
    private String decribe;
    private double price;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.decribe);
        dest.writeDouble(this.price);
    }

    public item() {
    }

    protected item(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.decribe = in.readString();
        this.price = in.readDouble();
    }

    public static final Parcelable.Creator<item> CREATOR = new Parcelable.Creator<item>() {
        @Override
        public item createFromParcel(Parcel source) {
            return new item(source);
        }

        @Override
        public item[] newArray(int size) {
            return new item[size];
        }
    };
}
