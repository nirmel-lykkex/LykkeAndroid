package com.lykkex.LykkeWallet.rest.camera.request.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by e.kazimirova on 14.02.2016.
 */
public class CameraModel {

    @SerializedName("Data")
    private String data;

    @SerializedName("Ext")
    private String ext;

    @SerializedName("Type")
    private String type;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
