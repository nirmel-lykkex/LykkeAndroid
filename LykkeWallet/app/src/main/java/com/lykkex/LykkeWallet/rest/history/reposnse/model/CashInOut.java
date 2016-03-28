package com.lykkex.LykkeWallet.rest.history.reposnse.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by e.kazimirova on 28.03.2016.
 */
public class CashInOut {

    @SerializedName("Id")
    private String id;

    @SerializedName("Amount")
    private String amount;

    @SerializedName("DateTime")
    private String dateTime;

    @SerializedName("Asset")
    private String asset;

    @SerializedName("IconId")
    private String iconId;

    @SerializedName("Volume")
    private String volume;
}
