package com.lykkex.LykkeWallet.gui.managers;

import com.lykkex.LykkeWallet.gui.fragments.models.RegistrationModelGUI;
import com.lykkex.LykkeWallet.rest.registration.request.models.RegistrationModel;

import org.androidannotations.annotations.EBean;

/**
 * Created by Murtic on 01/06/16.
 */
@EBean(scope = EBean.Scope.Singleton)
public class UserManager {

    private RegistrationModelGUI model = new RegistrationModelGUI();

    private boolean isUserRegistered = false;

    public RegistrationModelGUI getRegistrationModel() {
        return model;
    }

    public boolean isUserRegistered() {
        return isUserRegistered;
    }

    public void setUserRegistered(boolean userRegistered) {
        isUserRegistered = userRegistered;
    }
}
