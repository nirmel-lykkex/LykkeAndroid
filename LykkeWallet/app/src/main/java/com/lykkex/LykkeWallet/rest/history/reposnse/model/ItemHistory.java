package com.lykkex.LykkeWallet.rest.history.reposnse.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by e.kazimirova on 29.03.2016.
 */
public class ItemHistory implements Serializable{

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

    public void setId(String id) {
        this.id = id;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public void setAsset(String asset) {
        this.asset = asset;
    }

    public void setIconId(String iconId) {
        this.iconId = iconId;
    }
}
