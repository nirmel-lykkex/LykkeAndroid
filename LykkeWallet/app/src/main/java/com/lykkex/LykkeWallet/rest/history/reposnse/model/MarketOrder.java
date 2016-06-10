package com.lykkex.LykkeWallet.rest.history.reposnse.model;

import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;

/**
 * Created by e.kazimirova on 28.03.2016.
 */
public class MarketOrder extends ItemHistory {

    @SerializedName("OrderType")
    private String orderType;

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

    @SerializedName("Volume")
    private BigDecimal volume;

    public BigDecimal getVolume() {
        return volume;
    }

    public void setVolume(BigDecimal volume) {
        this.volume = volume;
    }

    public String getOrderType() {
        return orderType;
    }

    public String getPrice() {
        return price;
    }

    public String getBaseAsset() {
        return baseAsset;
    }

    public String getAssetPair() {
        return assetPair;
    }

    public String getBlockChainId() {
        return blockChainId;
    }

    public String getBlockChainSetteled() {
        return blockChainSetteled;
    }

    public String getTotalCost() {
        return totalCost;
    }

    public String getComission() {
        return comission;
    }

    public String getPosition() {
        return position;
    }

    public String getAccuracy() {
        return accuracy;
    }
}
