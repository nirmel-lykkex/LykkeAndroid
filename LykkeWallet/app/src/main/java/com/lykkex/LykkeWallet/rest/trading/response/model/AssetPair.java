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
    private int accurancy;

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

    public int getAccurancy() {
        return accurancy;
    }

    public void setAccurancy(int accurancy) {
        this.accurancy = accurancy;
    }
}
