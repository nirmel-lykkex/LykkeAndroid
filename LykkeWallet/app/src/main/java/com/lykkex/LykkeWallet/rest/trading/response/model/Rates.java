package com.lykkex.LykkeWallet.rest.trading.response.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by e.kazimirova on 17.03.2016.
 */
public class Rates {

    @SerializedName("Id")
    private String id;

    @SerializedName("Bid")
    private float bid;

    @SerializedName("PChng")
    private float ask;

    @SerializedName("ChngGrph")
    private float[] pchng;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getBid() {
        return bid;
    }

    public void setBid(int bid) {
        this.bid = bid;
    }

    public double getAsk() {
        return ask;
    }

    public void setAsk(int ask) {
        this.ask = ask;
    }

    public float[] getPchng() {
        return pchng;
    }

    public void setPchng(float[] pchng) {
        this.pchng = pchng;
    }

}
