package com.lykkex.LykkeWallet.rest.history.reposnse.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by e.kazimirova on 29.03.2016.
 */
public class Trading extends ItemHistory {

    @SerializedName("Volume")
    private String volume;

    public String getVolume() {
        return volume;
    }
}
