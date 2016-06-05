package com.lykkex.LykkeWallet.rest.mobileverify.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Murtic on 30/05/16.
 */
public class VerifyCodeResult {

    @SerializedName("Passed")
    private Boolean passed;

    public Boolean getPassed() {
        return passed;
    }

    public void setPassed(Boolean passed) {
        this.passed = passed;
    }
}
