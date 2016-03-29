package com.lykkex.LykkeWallet.rest.history.reposnse.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by e.kazimirova on 29.03.2016.
 */
public class ItemHistory {

    @SerializedName("Id")
    private String id;

    @SerializedName("DateTime")
    private String dateTime;

    @SerializedName("Asset")
    private String asset;

    @SerializedName("IconId")
    private String iconId;

    public String getId() {
        return id;
    }

    public String getDateTime() {
        return dateTime;
    }

    public String getAsset() {
        return asset;
    }

    public String getIconId() {
        return iconId;
    }
}
