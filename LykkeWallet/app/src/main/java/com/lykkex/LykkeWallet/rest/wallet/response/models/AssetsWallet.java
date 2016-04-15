package com.lykkex.LykkeWallet.rest.wallet.response.models;

import com.google.gson.annotations.SerializedName;
import com.lykkex.LykkeWallet.rest.internal.response.model.BaseAsset;

import java.io.Serializable;

/**
 * Created by LIZA on 01.03.2016.
 */
public class AssetsWallet extends BaseAsset implements Serializable {

    @SerializedName("Balance")
    private String balance;

    @SerializedName("Symbol")
    private String symbol;

    @SerializedName("AssetPairId")
    private String assetPairId;

    @SerializedName("IssuerId")
    private String issuerId;

    @SerializedName("HideIfZero")
    private boolean hideIfZero;

    public String getIssuerId() {
        return issuerId;
    }

    public void setIssuerId(String issuerId) {
        this.issuerId = issuerId;
    }

    public boolean isHideIfZero() {
        return hideIfZero;
    }

    public void setHideIfZero(boolean hideIfZero) {
        this.hideIfZero = hideIfZero;
    }

    public String getAssetPairId() {
        return assetPairId;
    }

    public void setAssetPairId(String assetPairId) {
        this.assetPairId = assetPairId;
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

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

}
