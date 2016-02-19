package com.lykkex.LykkeWallet.rest.pin.request.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by e.kazimirova on 19.02.2016.
 */
public class PinRequest {

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    @SerializedName("Pin")
    private String pin;

    public PinRequest(String pin){
        this.pin = pin;
    }
}
