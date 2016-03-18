package com.lykkex.LykkeWallet.rest.trading.response.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by e.kazimirova on 17.03.2016.
 */
public class RatesResult {

    @SerializedName("Rates")
    private Rate[] rates;

    public Rate[] getRates() {
        return rates;
    }

    public void setRates(Rate[] rates) {
        this.rates = rates;
    }

}
