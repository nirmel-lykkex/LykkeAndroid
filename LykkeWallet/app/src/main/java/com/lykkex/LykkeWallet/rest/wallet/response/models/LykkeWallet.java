package com.lykkex.LykkeWallet.rest.wallet.response.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by LIZA on 01.03.2016.
 */
public class LykkeWallet {

    @SerializedName("Assets")
    private AssetsWallet[] assets;

    @SerializedName("Equity")
    private int equity;

    public AssetsWallet[] getAssets() {
        return assets;
    }

    public void setAssets(AssetsWallet[] assets) {
        this.assets = assets;
    }

    public int getEquity() {
        return equity;
    }

    public void setEquity(int equity) {
        this.equity = equity;
    }

}
