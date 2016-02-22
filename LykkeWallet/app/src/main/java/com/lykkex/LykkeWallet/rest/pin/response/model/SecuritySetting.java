package com.lykkex.LykkeWallet.rest.pin.response.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by LIZA on 22.02.2016.
 */
public class SecuritySetting {

    @SerializedName("RateRefreshPeriod")
    private int rateRefreshPeriod;

    @SerializedName("BaseAsset")
    private BaseAsset baseAsset;

    @SerializedName("SignOrder")
    private boolean signOrder;
}
