package com.lykkex.LykkeWallet.rest.registration.response.models;

import com.google.gson.annotations.SerializedName;
import com.lykkex.LykkeWallet.rest.login.response.model.PersonalData;

/**
 * Created by e.kazimirova on 10.02.2016.
 */
public class RegistrationResult {

    @SerializedName("Token")
    protected String token;

    @SerializedName("PersonalData")
    protected PersonalData personalData;

    public PersonalData getPersonalData() {
        return personalData;
    }

    public String getToken() {
        return token;
    }
}