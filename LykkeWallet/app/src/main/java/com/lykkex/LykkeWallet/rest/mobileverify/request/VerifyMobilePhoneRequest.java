package com.lykkex.LykkeWallet.rest.mobileverify.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Murtic on 01.06.2016.
 */
public class VerifyMobilePhoneRequest {

    @SerializedName("PhoneNumber")
    private String phoneNumber;

    public VerifyMobilePhoneRequest(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}