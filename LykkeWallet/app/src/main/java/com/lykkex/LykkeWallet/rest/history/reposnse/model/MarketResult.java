package com.lykkex.LykkeWallet.rest.history.reposnse.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by LIZA on 18.04.2016.
 */
public class MarketResult {

    @SerializedName("MarketOrder")
    private MarketOrder marketOrder;

    public MarketOrder getMarketOrder() {
        return marketOrder;
    }
}
