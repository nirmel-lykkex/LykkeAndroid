package com.lykkex.LykkeWallet.rest.wallet.request.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by e.kazimirova on 19.04.2016.
 */
public class CashOut {

    @SerializedName("MultiSig")
    private String multiSig;

    @SerializedName("Amount")
    private String amount;

    @SerializedName("AssetId")
    private String assetId;

    public CashOut(String multiSig, String amount, String assetId) {
        this.multiSig = multiSig;
        this.amount = amount;
        this.assetId = assetId;
    }
}
