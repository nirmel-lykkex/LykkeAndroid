package com.lykkex.LykkeWallet.rest.registration.response.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by e.kazimirova on 09.02.2016.
 */
public class AcountExistResult {

    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    @SerializedName("IsEmailRegistered")
    private boolean isEmailRegistered;

    public boolean isEmailRegistered() {
        return isEmailRegistered;
    }

}
