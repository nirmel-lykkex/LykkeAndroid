package com.lykkex.LykkeWallet.rest.history.reposnse.model;

import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;

/**
 * Created by e.kazimirova on 29.03.2016.
 */
public class Trading extends ItemHistory {

    @SerializedName("Volume")
    private BigDecimal volume;

    public BigDecimal getVolume() {
        return volume;
    }

    public void setVolume(BigDecimal volume) {
        this.volume = volume;
    }
}
