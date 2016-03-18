package com.lykkex.LykkeWallet.rest.trading.response.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by e.kazimirova on 17.03.2016.
 */
public class RateResult {

    public Rate getRate() {
        return rate;
    }

    public void setRate(Rate rate) {
        this.rate = rate;
    }

    @SerializedName("Rate")
    private Rate rate;
}