package com.lykkex.LykkeWallet.rest.registration.response.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by e.kazimirova on 10.02.2016.
 */
public class RegistrationResult {

    @SerializedName("Token")
    private String token;

    @SerializedName("PersonalData")
    private String personalData;

    @SerializedName("FullName")
    private String fullName;

    @SerializedName("Email")
    private String email;

    @SerializedName("Phone")
    private String phone;

    @SerializedName("Country")
    private String country;

    @SerializedName("Address")
    private String address;

    @SerializedName("City")
    private String city;

    @SerializedName("Zip")
    private String zip;
}