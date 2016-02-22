package com.lykkex.LykkeWallet.gui.fragments.models;

import com.lykkex.LykkeWallet.rest.login.request.model.AuthRequest;

import java.io.Serializable;

/**
 * Created by e.kazimirova on 11.02.2016.
 */
public class AuthModelGUI extends AuthRequest implements Serializable {

    private boolean isReady;

    public boolean isReady() {
        return isReady;
    }

    public void setIsReady(boolean isReady) {
        this.isReady = isReady;
    }
}
