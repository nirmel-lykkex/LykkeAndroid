package com.lykkex.LykkeWallet.rest.appinfo.response.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Murtic on 30/05/16.
 */
public class AppInfoResult {

    @SerializedName("AppVersion")
    private String appVersion;

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

}
