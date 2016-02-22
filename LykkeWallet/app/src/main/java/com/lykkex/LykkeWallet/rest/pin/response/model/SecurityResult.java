package com.lykkex.LykkeWallet.rest.pin.response.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by LIZA on 22.02.2016.
 */
public class SecurityResult {

    @SerializedName("Passed")
    private boolean passed;

    @SerializedName("Settings")
    private SecuritySetting setting;
}
