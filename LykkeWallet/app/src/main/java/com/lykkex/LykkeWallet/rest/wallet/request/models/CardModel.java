package com.lykkex.LykkeWallet.rest.wallet.request.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by LIZA on 06.03.2016.
 */
public class CardModel {

    @SerializedName("BankNumber")
    private String bankNumber;

    @SerializedName("Name")
    private String name;

    @SerializedName("Type")
    private String type = "visa";

    @SerializedName("MonthTo")
    private int monthTo;

    @SerializedName("YearTo")
    private int yearTo;

    @SerializedName("Cvc")
    private String cvc;


    public void setBankNumber(String bankNumber) {
        this.bankNumber = bankNumber;
    }

    public String getBankNumber() {
        return bankNumber;
    }

    public String getCvc() {
        return cvc;
    }

    public void setCvc(String cvc) {
        this.cvc = cvc;
    }

    public int getYearTo() {
        return yearTo;
    }

    public void setYearTo(int yearTo) {
        this.yearTo = yearTo;
    }

    public int getMonthTo() {
        return monthTo;
    }

    public void setMonthTo(int monthTo) {
        this.monthTo = monthTo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
