package com.lykkex.LykkeWallet.rest.registration.response.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by e.kazimirova on 09.02.2016.
 */
public class AcountExisResult {

    @SerializedName("IsEmailRegistered")
    private boolean isEmailRegistered;

    public boolean isEmailRegistered() {
        return isEmailRegistered;
    }

}
