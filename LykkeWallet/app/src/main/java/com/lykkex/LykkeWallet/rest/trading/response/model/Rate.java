package com.lykkex.LykkeWallet.rest.trading.response.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by e.kazimirova on 17.03.2016.
 */
public class Rate {

    @SerializedName("Id")
    private String id;

    @SerializedName("Bid")
    private String bid;

    @SerializedName("PChng")
    private String ask;

    @SerializedName("ChngGrph")
    private float[] pchng;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public String getAsk() {
        return ask;
    }

    public void setAsk(String ask) {
        this.ask = ask;
    }

    public float[] getPchng() {
        return pchng;
    }

    public void setPchng(float[] pchng) {
        this.pchng = pchng;
    }

}
