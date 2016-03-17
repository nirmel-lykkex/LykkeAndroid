package com.lykkex.LykkeWallet.rest.trading.response.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by e.kazimirova on 17.03.2016.
 */
public class Rates {

    @SerializedName("Id")
    private String id;

    @SerializedName("Bid")
    private int bid;

    @SerializedName("PChng")
    private int ask;

    @SerializedName("ChngGrph")
    private int pchng;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getBid() {
        return bid;
    }

    public void setBid(int bid) {
        this.bid = bid;
    }

    public int getAsk() {
        return ask;
    }

    public void setAsk(int ask) {
        this.ask = ask;
    }

    public int getPchng() {
        return pchng;
    }

    public void setPchng(int pchng) {
        this.pchng = pchng;
    }

}
