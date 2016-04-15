package com.lykkex.LykkeWallet.rest.trading.response.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by LIZA on 15.04.2016.
 */
public class SendEmail {

    @SerializedName("Email")
    private String email;

    public String getEmail() {
        return email;
    }
}
