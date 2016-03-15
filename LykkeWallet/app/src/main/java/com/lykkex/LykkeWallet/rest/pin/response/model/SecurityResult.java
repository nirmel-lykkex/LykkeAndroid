package com.lykkex.LykkeWallet.rest.pin.response.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by LIZA on 22.02.2016.
 */
public class SecurityResult {

    public boolean isPassed() {
        return passed;
    }

    public void setPassed(boolean passed) {
        this.passed = passed;
    }

    public SecuritySetting getSetting() {
        return setting;
    }

    public void setSetting(SecuritySetting setting) {
        this.setting = setting;
    }

    @SerializedName("Passed")
    private boolean passed;

    @SerializedName("Settings")
    private SecuritySetting setting;
}
