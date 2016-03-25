package com.lykkex.LykkeWallet.rest.trading.response.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by e.kazimirova on 25.03.2016.
 */
public class TransactionResult {

    @SerializedName("Transaction")
    private Transaction transaction;

    public Transaction getTransaction() {
        return transaction;
    }
}
