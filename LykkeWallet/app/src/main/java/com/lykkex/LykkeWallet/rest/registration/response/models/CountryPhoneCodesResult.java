package com.lykkex.LykkeWallet.rest.registration.response.models;

import com.google.gson.annotations.SerializedName;
import com.lykkex.LykkeWallet.rest.login.response.model.PersonalData;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Murtic on 01.06.2016.
 */
public class CountryPhoneCodesResult implements Serializable{

    @SerializedName("Current")
    protected String current;

    @SerializedName("CountriesList")
    protected List<CountryPhoneCodeData> countriesList;

    public String getCurrent() {
        return current;
    }

    public void setCurrent(String current) {
        this.current = current;
    }

    public List<CountryPhoneCodeData> getCountriesList() {
        return countriesList;
    }

    public void setCountriesList(List<CountryPhoneCodeData> countriesList) {
        this.countriesList = countriesList;
    }
}