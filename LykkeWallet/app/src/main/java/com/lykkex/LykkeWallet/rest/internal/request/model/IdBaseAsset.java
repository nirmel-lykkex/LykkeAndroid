package com.lykkex.LykkeWallet.rest.internal.request.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by LIZA on 15.03.2016.
 */
public class IdBaseAsset {

    public IdBaseAsset(String id) {
        this.id = id;
    }

    @SerializedName("Id")
    private String id;
}
