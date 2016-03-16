package com.lykkex.LykkeWallet.rest.internal.response.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by e.kazimirova on 16.03.2016.
 */
public class AssetPairsResult {

    @SerializedName("AssetPairs")
    private AssetPair[] assetPairs;

    public AssetPair[] getAssetPairs() {
        return assetPairs;
    }

    public void setAssetPairs(AssetPair[] assetPairs) {
        this.assetPairs = assetPairs;
    }
}
