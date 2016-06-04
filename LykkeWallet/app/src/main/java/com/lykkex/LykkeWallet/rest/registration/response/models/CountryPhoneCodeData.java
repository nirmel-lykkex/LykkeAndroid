package com.lykkex.LykkeWallet.rest.registration.response.models;

import com.google.gson.annotations.SerializedName;
import com.lykkex.LykkeWallet.rest.login.response.model.PersonalData;

import java.io.Serializable;

/**
 * Created by Murtic on 01.06.2016.
 */
public class CountryPhoneCodeData implements Serializable{

    @SerializedName("Id")
    protected String id;

    @SerializedName("Name")
    protected String name;

    @SerializedName("Prefix")
    protected String prefix;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
}