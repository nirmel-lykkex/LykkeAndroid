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
    private BigDecimal price;

    @SerializedName("BaseAsset")
    private String baseAsset;

    @SerializedName("AssetPair")
    private String assetPair;

    @SerializedName("BlockchainId")
    private String blockChainId;

    @SerializedName("BlockchainSetteled")
    private String blockChainSetteled;

    @SerializedName("TotalCost")
    private BigDecimal totalCost;

    @SerializedName("Comission")
    private BigDecimal comission;

    @SerializedName("Position")
    private BigDecimal position;

    @SerializedName("Accuracy")
    private Integer accuracy;

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

    public BigDecimal getPrice() {
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

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public BigDecimal getComission() {
        return comission;
    }

    public BigDecimal getPosition() {
        return position;
    }

    public Integer getAccuracy() {
        return accuracy;
    }
}
