package com.lykkex.LykkeWallet.rest.wallet.response.models;

import com.google.gson.annotations.SerializedName;
import com.lykkex.LykkeWallet.rest.internal.response.model.BaseAsset;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created by LIZA on 01.03.2016.
 */
public class AssetsWallet implements Serializable {

    @SerializedName("Balance")
    private BigDecimal balance;

    @SerializedName("Symbol")
    private String symbol;

    @SerializedName("AssetPairId")
    private String assetPairId;

    @SerializedName("IssuerId")
    private String issuerId;

    @SerializedName("HideIfZero")
    private boolean hideIfZero;

    @SerializedName("Accuracy")
    private int accuracy;

    @SerializedName("Id")
    protected String id;

    @SerializedName("Name")
    protected String name;

    public int getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(int accuracy) {
        this.accuracy = accuracy;
    }

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

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getBalanceWithSymbol() {
        String value = symbol != null ? symbol : "";

        value += " " + (balance.doubleValue() > 0 ? balance.setScale(accuracy,
                RoundingMode.HALF_EVEN).stripTrailingZeros().toPlainString() : "0");

        return value;
    }
}
