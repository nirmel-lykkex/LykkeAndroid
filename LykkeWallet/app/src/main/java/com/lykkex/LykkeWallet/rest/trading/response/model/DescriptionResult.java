package com.lykkex.LykkeWallet.rest.trading.response.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by e.kazimirova on 18.03.2016.
 */
public class DescriptionResult implements Serializable {

    @SerializedName("Id")
    private String id;

    @SerializedName("AssetClass")
    private String assetClass;

    @SerializedName("PopIndex")
    private String popIndex;

    @SerializedName("Description")
    private String description;

    @SerializedName("IssuerName")
    private String issuerName;

    @SerializedName("NumberOfCoins")
    private String numberOfCoins;

    @SerializedName("MarketCapitalization")
    private String marketCapitalization;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAssetClass() {
        return assetClass;
    }

    public void setAssetClass(String assetClass) {
        this.assetClass = assetClass;
    }

    public String getPopIndex() {
        return popIndex;
    }

    public void setPopIndex(String popIndex) {
        this.popIndex = popIndex;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIssuerName() {
        return issuerName;
    }

    public void setIssuerName(String issuerName) {
        this.issuerName = issuerName;
    }

    public String getNumberOfCoins() {
        return numberOfCoins;
    }

    public void setNumberOfCoins(String numberOfCoins) {
        this.numberOfCoins = numberOfCoins;
    }

    public String getMarketCapitalization() {
        return marketCapitalization;
    }

    public void setMarketCapitalization(String marketCapitalization) {
        this.marketCapitalization = marketCapitalization;
    }

}
