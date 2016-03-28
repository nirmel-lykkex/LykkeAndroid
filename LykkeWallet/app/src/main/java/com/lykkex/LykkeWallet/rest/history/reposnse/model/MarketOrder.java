package com.lykkex.LykkeWallet.rest.history.reposnse.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by e.kazimirova on 28.03.2016.
 */
public class MarketOrder {
    @SerializedName("Id")
    private String id;

    @SerializedName("DateTime")
    private String dateTime;

    @SerializedName("OrderType")
    private String orderType;

    @SerializedName("Volume")
    private String volume;

    @SerializedName("Price")
    private String price;

    @SerializedName("BaseAsset")
    private String baseAsset;

    @SerializedName("AssetPair")
    private String assetPair;

    @SerializedName("BlockchainId")
    private String blockChainId;

    @SerializedName("BlockchainSetteled")
    private String blockChainSetteled;

    @SerializedName("TotalCost")
    private String totalCost;

    @SerializedName("Comission")
    private String comission;

    @SerializedName("Position")
    private String position;

    @SerializedName("Accuracy")
    private String accuracy;

}
