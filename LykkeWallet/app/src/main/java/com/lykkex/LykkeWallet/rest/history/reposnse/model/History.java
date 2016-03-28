package com.lykkex.LykkeWallet.rest.history.reposnse.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by e.kazimirova on 28.03.2016.
 */
public class History {

    @SerializedName("MarketOrders")
    private MarketOrder[] marketOrders;

    @SerializedName("CashInOut")
    private CashInOut[] cashInOuts;

    @SerializedName("Trades")
    private CashInOut[] trades;
}
