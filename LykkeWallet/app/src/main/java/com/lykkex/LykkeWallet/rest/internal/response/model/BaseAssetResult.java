package com.lykkex.LykkeWallet.rest.internal.response.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by LIZA on 03.03.2016.
 */
public class BaseAssetResult {

    @SerializedName("Asset")
    private BaseAsset asset;

    public BaseAsset getAsset() {
        return asset;
    }

    public void setAsset(BaseAsset asset) {
        this.asset = asset;
    }

}
