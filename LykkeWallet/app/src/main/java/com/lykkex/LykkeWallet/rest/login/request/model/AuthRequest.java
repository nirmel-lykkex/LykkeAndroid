package com.lykkex.LykkeWallet.rest.login.request.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by e.kazimirova on 11.02.2016.
 */
public class AuthRequest implements Serializable{

    @SerializedName("Email")
    protected String email = "";

    @SerializedName("Password")
    protected String password = "";

    @SerializedName("ClientInfo")
    protected String clientInfo;


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getClientInfo() {
        return clientInfo;
    }

    public void setClientInfo(String clientInfo) {
        this.clientInfo = clientInfo;
    }

}
