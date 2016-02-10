package com.lykkex.LykkeWallet.gui.fragments.models;

import com.lykkex.LykkeWallet.rest.registration.request.models.RegistrationModel;

/**
 * Created by e.kazimirova on 10.02.2016.
 */
public class RegistrationModelGUI extends RegistrationModel {

    private String secondPassword;
    private boolean isReady = false;


    public String getSecondPassword() {
        return secondPassword;
    }

    public void setSecondPassword(String secondPassword) {
        this.secondPassword = secondPassword;
    }

    public boolean isReady() {
        return isReady;
    }

    public void setIsReady(boolean isReady) {
        this.isReady = isReady;
    }
}
