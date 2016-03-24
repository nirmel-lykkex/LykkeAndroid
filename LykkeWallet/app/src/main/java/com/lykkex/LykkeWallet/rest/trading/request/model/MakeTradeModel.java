package com.lykkex.LykkeWallet.rest.trading.request.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by e.kazimirova on 24.03.2016.
 */
public class MakeTradeModel {

    @SerializedName("BaseAsset")
    private String baseAsset;

    @SerializedName("AssetPair")
    private String assetPair;

    @SerializedName("Volume")
    private String volume;

    @SerializedName("Rate")
    private String rate;

    public MakeTradeModel(String baseAsset, String assetPair, String volume, String rate) {
        this.baseAsset = baseAsset;
        this.assetPair = assetPair;
        this.volume = volume;
        this.rate = rate;
    }

    public String getBaseAsset() {
        return baseAsset;
    }

    public void setBaseAsset(String baseAsset) {
        this.baseAsset = baseAsset;
    }

    public String getAssetPair() {
        return assetPair;
    }

    public void setAssetPair(String assetPair) {
        this.assetPair = assetPair;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

}
