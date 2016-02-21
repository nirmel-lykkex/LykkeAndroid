package com.lykkex.LykkeWallet.rest.login.response.model;

import com.google.gson.annotations.SerializedName;
import com.lykkex.LykkeWallet.rest.registration.response.models.RegistrationResult;

/**
 * Created by e.kazimirova on 11.02.2016.
 */
public class AuthModelResult extends RegistrationResult {

    @SerializedName("KycStatus")
    private String kycStatus;

    @SerializedName("PinIsEntered")
    private String pinIsEntered;

    public String getPinIsEntered() {
        return pinIsEntered;
    }

    public void setPinIsEntered(String pinIsEntered) {
        this.pinIsEntered = pinIsEntered;
    }

    public String getKycStatus() {
        return kycStatus;
    }

    public void setKycStatus(String kycStatus) {
        this.kycStatus = kycStatus;
    }
}
