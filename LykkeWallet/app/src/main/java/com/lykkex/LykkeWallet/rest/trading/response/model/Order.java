package com.lykkex.LykkeWallet.rest.trading.response.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by e.kazimirova on 24.03.2016.
 */
public class Order {

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
    private String blockchainId;

    @SerializedName("BlockchainSetteled")
    private String blockchainSetteled;

    @SerializedName("TotalCost")
    private String totalCost;

    @SerializedName("Comission")
    private String comission;

    @SerializedName("Position")
    private String position;

    @SerializedName("Accuracy")
    private String accuracy;
}
