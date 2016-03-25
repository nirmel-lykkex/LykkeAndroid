package com.lykkex.LykkeWallet.rest.trading.response.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by e.kazimirova on 25.03.2016.
 */
public class Transaction {

    @SerializedName("Hash")
    private String hash;

    @SerializedName("Date")
    private String date;

    @SerializedName("Confirmations")
    private String confirmations;

    @SerializedName("Block")
    private String block;

    @SerializedName("Height")
    private String height;

    @SerializedName("SenderId")
    private String senderId;

    @SerializedName("AssetId")
    private String assetId;

    @SerializedName("Quantity")
    private String quality;

    @SerializedName("Url")
    private String url;

    public String getHash() {
        return hash;
    }

    public String getDate() {
        return date;
    }

    public String getConfirmations() {
        return confirmations;
    }

    public String getBlock() {
        return block;
    }

    public String getHeight() {
        return height;
    }

    public String getSenderId() {
        return senderId;
    }

    public String getAssetId() {
        return assetId;
    }

    public String getQuality() {
        return quality;
    }

    public String getUrl() {
        return url;
    }

}
