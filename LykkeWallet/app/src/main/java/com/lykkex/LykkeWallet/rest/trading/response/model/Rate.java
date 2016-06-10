package com.lykkex.LykkeWallet.rest.trading.response.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by e.kazimirova on 17.03.2016.
 */
public class Rate {

    @SerializedName("Id")
    private String id;

    @SerializedName("Bid")
    private Double bid;

    @SerializedName("Ask")
    private Double ask;

    @SerializedName("ChngGrph")
    private Float[] chngGrph;

    @SerializedName("PChng")
    private Float pchng;

    @SerializedName("Inverted")
    private Boolean inverted;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getBid() {
        return bid;
    }

    public void setBid(Double bid) {
        this.bid = bid;
    }

    public Double getAsk() {
        return ask;
    }

    public void setAsk(Double ask) {
        this.ask = ask;
    }

    public Float[] getChngGrph() {
        return chngGrph;
    }

    public void setChngGrph(Float[] chngGrph) {
        this.chngGrph = chngGrph;
    }

    public Float getPchng() {
        return pchng;
    }

    public void setPchng(Float pchng) {
        this.pchng = pchng;
    }

    public Boolean getInverted() {
        return inverted;
    }

    public void setInverted(Boolean inverted) {
        this.inverted = inverted;
    }
}
