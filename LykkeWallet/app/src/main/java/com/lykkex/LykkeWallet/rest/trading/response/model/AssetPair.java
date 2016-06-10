package com.lykkex.LykkeWallet.rest.trading.response.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by e.kazimirova on 16.03.2016.
 */
public class AssetPair implements Serializable{

    @SerializedName("Group")
    private String group;

    @SerializedName("Id")
    private String id;

    @SerializedName("Name")
    private String name;

    @SerializedName("Accuracy")
    private int accuracy;

    @SerializedName("InvertedAccuracy")
    private int invertedAccuracy;

    @SerializedName("Inverted")
    private Boolean inverted;

    @SerializedName("BaseAssetId")
    private String baseAssetId;

    @SerializedName("QuotingAssetId")
    private String quotingAssetId;

    public String getQuotingAssetId() {
        return quotingAssetId;
    }

    public String getBaseAssetId() {
        return baseAssetId;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(int accuracy) {
        this.accuracy = accuracy;
    }

    public int getInvertedAccuracy() {
        return invertedAccuracy;
    }

    public void setInvertedAccuracy(int invertedAccuracy) {
        this.invertedAccuracy = invertedAccuracy;
    }

    public Boolean getInverted() {
        return inverted;
    }

    public void setInverted(Boolean inverted) {
        this.inverted = inverted;
    }

    public void setBaseAssetId(String baseAssetId) {
        this.baseAssetId = baseAssetId;
    }

    public void setQuotingAssetId(String quotingAssetId) {
        this.quotingAssetId = quotingAssetId;
    }
}
