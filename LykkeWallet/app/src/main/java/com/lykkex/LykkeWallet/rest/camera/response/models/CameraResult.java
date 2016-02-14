package com.lykkex.LykkeWallet.rest.camera.response.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by e.kazimirova on 14.02.2016.
 */
public class CameraResult {

    @SerializedName("IdCard")
    private boolean idCard = true;

    @SerializedName("ProofOfAddress")
    private boolean proofOfAddress = true;

    @SerializedName("Selfie")
    private boolean selfie = true;

    public boolean isIdCard() {
        return idCard;
    }

    public boolean isProofOfAddress() {
        return proofOfAddress;
    }

    public boolean isSelfie() {
        return selfie;
    }

    public void setIdCard(boolean idCard) {
        this.idCard = idCard;
    }

    public void setSelfie(boolean selfie) {
        this.selfie = selfie;
    }

    public void setProofOfAddress(boolean proofOfAddress) {
        this.proofOfAddress = proofOfAddress;
    }

}
