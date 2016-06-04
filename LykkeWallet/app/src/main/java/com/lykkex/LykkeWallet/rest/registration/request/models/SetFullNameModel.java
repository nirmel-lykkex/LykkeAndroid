package com.lykkex.LykkeWallet.rest.registration.request.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Murtic on 04/06/16.
 */
public class SetFullNameModel implements Serializable {

    @SerializedName("FullName")
    private String fullName = "";

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
