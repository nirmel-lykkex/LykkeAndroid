package com.lykkex.LykkeWallet.rest.internal.response.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by LIZA on 15.03.2016.
 */
public class SettingSignOrder {


    @SerializedName("SignOrderBeforeGo")
    private boolean signOrderBeforeGo;


    public boolean isSignOrderBeforeGo() {
        return signOrderBeforeGo;
    }

    public void setSignOrderBeforeGo(boolean signOrderBeforeGo) {
        this.signOrderBeforeGo = signOrderBeforeGo;
    }

}
