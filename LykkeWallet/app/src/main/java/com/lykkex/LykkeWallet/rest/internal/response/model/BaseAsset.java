package com.lykkex.LykkeWallet.rest.internal.response.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by LIZA on 03.03.2016.
 */
public class BaseAsset {

    @SerializedName("Id")
    protected String id;

    @SerializedName("Name")
    protected String name;

    @SerializedName("HideDeposit")
    protected Boolean hideDeposit = false;

    @SerializedName("HideWithdraw")
    protected Boolean hideWithdraw = false;

    @SerializedName("Symbol")
    protected String symbol;

    @SerializedName("Accuracy")
    protected Integer accuracy;

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

    public Boolean getHideDeposit() {
        return hideDeposit;
    }

    public void setHideDeposit(Boolean hideDeposit) {
        this.hideDeposit = hideDeposit;
    }

    public Boolean getHideWithdraw() {
        return hideWithdraw;
    }

    public void setHideWithdraw(Boolean hideWithdraw) {
        this.hideWithdraw = hideWithdraw;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Integer getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(Integer accuracy) {
        this.accuracy = accuracy;
    }
}
