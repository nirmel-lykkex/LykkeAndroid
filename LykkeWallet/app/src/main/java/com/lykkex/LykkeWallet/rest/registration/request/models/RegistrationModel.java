package com.lykkex.LykkeWallet.rest.registration.request.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by e.kazimirova on 10.02.2016.
 */
public class RegistrationModel {

    @SerializedName("Email")
    private String email = "";

    @SerializedName("FullName")
    private String fullName = "";

    @SerializedName("ContactPhone")
    private String contactPhone = "";

    @SerializedName("Password")
    private String password = "";

    @SerializedName("ClientInfo")
    private String clientInfo = ""; //TODO "<Тип устройства>; Model:<Модель>; Os:<OS>; Screen:<Screen>"

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getMobile() {
        return contactPhone;
    }

    public void setMobile(String mobile) {
        this.contactPhone = mobile;
    }

    public String getPasswordFirst() {
        return password;
    }

    public void setPasswordFirst(String password) {
        this.password = password;
    }

    public String getClientInfo() {
        return clientInfo;
    }

    public void setClientInfo(String clientInfo) {
        this.clientInfo = clientInfo;
    }
}
