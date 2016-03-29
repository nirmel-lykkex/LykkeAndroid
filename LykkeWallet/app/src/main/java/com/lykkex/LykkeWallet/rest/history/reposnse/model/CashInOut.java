package com.lykkex.LykkeWallet.rest.history.reposnse.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by e.kazimirova on 28.03.2016.
 */
public class CashInOut extends ItemHistory {

    @SerializedName("Amount")
    private String amount;

    public String getAmount() {
        return amount;
    }
}
