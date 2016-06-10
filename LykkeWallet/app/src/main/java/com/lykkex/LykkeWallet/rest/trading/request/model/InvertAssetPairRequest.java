package com.lykkex.LykkeWallet.rest.trading.request.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Murtic on 11.06.2016.
 */
public class InvertAssetPairRequest {

    @SerializedName("AssetPairId")
    private String assetPairId;

    @SerializedName("Inverted")
    private Boolean inverted;

    public InvertAssetPairRequest(String assetPairId, Boolean inverted) {
        this.assetPairId = assetPairId;
        this.inverted = inverted;
    }

    public InvertAssetPairRequest() {
    }

    public String getAssetPairId() {
        return assetPairId;
    }

    public void setAssetPairId(String assetPairId) {
        this.assetPairId = assetPairId;
    }

    public Boolean getInverted() {
        return inverted;
    }

    public void setInverted(Boolean inverted) {
        this.inverted = inverted;
    }
}
