package com.lykkex.LykkeWallet.rest.wallet.response.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by LIZA on 01.03.2016.
 */
public class BankCards implements Parcelable{

    @SerializedName("Id")
    private String id;

    @SerializedName("Type")
    private String type;

    @SerializedName("LastDigits")
    private String lastDigits;

    @SerializedName("Name")
    private String name;

    @SerializedName("MonthTo")
    private int monthTo;

    @SerializedName("YearTo")
    private int yearTo;

    public BankCards(Parcel source) {
        id = source.readString();
        type= source.readString();
        lastDigits= source.readString();
        name= source.readString();
        monthTo = source.readInt();
        yearTo = source.readInt();
    }

    public int getYearTo() {
        return yearTo;
    }

    public void setYearTo(int yearTo) {
        this.yearTo = yearTo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLastDigits() {
        return lastDigits;
    }

    public void setLastDigits(String lastDigits) {
        this.lastDigits = lastDigits;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMonthTo() {
        return monthTo;
    }

    public void setMonthTo(int monthTo) {
        this.monthTo = monthTo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(type);
        dest.writeString(lastDigits);
        dest.writeString(name);
        dest.writeInt(monthTo);
        dest.writeInt(yearTo);
    }

    public static final Creator<BankCards> CREATOR = new Creator<BankCards>() {
        @Override
        public BankCards createFromParcel(Parcel source) {
            return new BankCards(source);
        }

        @Override
        public BankCards[] newArray(int size) {
            return new BankCards[size];
        }
    };

}
