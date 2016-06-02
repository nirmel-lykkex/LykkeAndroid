package com.lykkex.LykkeWallet.rest.emailverify.request.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Murtic on 01.06.2016.
 */
public class VerifyEmailRequest {

    @SerializedName("Email")
    private String email;

    public VerifyEmailRequest(String email){
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
