package com.lykkex.LykkeWallet.gui.managers;

import com.lykkex.LykkeWallet.gui.fragments.models.RegistrationModelGUI;
import com.lykkex.LykkeWallet.rest.registration.request.models.RegistrationModel;

/**
 * Created by Murtic on 01/06/16.
 */
public class UserManager {

    private static UserManager INSTANCE;

    private RegistrationModelGUI model;

    private UserManager() {
        this.model = new RegistrationModelGUI();
    }

    public static synchronized UserManager getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new UserManager();
        }

        return INSTANCE;
    }

    public RegistrationModelGUI getRegistrationModel() {
        return model;
    }
}
