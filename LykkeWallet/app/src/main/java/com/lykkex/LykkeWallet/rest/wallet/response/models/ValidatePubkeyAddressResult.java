package com.lykkex.LykkeWallet.rest.wallet.response.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by LIZA on 06.03.2016.
 */
public class ValidatePubkeyAddressResult {

    @SerializedName("Valid")
    private Boolean valid;

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }
}
