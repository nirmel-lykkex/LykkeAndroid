package com.lykkex.LykkeWallet.rest.wallet.response.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by LIZA on 01.03.2016.
 */
public class BankCards {

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

}
