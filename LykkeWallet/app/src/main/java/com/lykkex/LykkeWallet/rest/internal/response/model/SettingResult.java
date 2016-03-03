package com.lykkex.LykkeWallet.rest.internal.response.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by LIZA on 03.03.2016.
 */
public class SettingResult {

    @SerializedName("RateRefreshPeriod")
    private int rateRefreshPeriod;

    @SerializedName("BaseAsset")
    private BaseAsset baseAsset;

    @SerializedName("SignOrder")
    private boolean signOrder;

    @SerializedName("DepositUrl")
    private String depositUrl;

    public int getRateRefreshPeriod() {
        return rateRefreshPeriod;
    }

    public void setRateRefreshPeriod(int rateRefreshPeriod) {
        this.rateRefreshPeriod = rateRefreshPeriod;
    }

    public BaseAsset getBaseAsset() {
        return baseAsset;
    }

    public void setBaseAsset(BaseAsset baseAsset) {
        this.baseAsset = baseAsset;
    }

    public boolean isSignOrder() {
        return signOrder;
    }

    public void setSignOrder(boolean signOrder) {
        this.signOrder = signOrder;
    }

    public boolean getSignOrder() {
        return signOrder;
    }

    public String getDepositUrl() {
        return depositUrl;
    }

    public void setDepositUrl(String depositUrl) {
        this.depositUrl = depositUrl;
    }
}
