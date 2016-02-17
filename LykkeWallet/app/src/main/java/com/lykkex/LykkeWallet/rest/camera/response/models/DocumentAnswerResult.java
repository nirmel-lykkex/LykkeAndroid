package com.lykkex.LykkeWallet.rest.camera.response.models;

import com.google.gson.annotations.SerializedName;
import com.lykkex.LykkeWallet.gui.fragments.models.KysStatusEnum;
import com.lykkex.LykkeWallet.rest.login.response.model.PersonalData;

/**
 * Created by LIZA on 16.02.2016.
 */
public class DocumentAnswerResult {

    @SerializedName("KycStatus")
    private KysStatusEnum kysStatus;

    public KysStatusEnum getKysStatus() {
        return kysStatus;
    }

    public PersonalData getPersonalData() {
        return personalData;
    }

    @SerializedName("PersonalData")
    private PersonalData personalData;
}
