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

}
