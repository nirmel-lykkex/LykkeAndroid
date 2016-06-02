package com.lykkex.LykkeWallet.gui.activity.authentication;

import android.os.Bundle;

import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.gui.activity.BaseActivity;
import com.lykkex.LykkeWallet.gui.fragments.controllers.FieldController;
import com.lykkex.LykkeWallet.gui.fragments.models.RegistrationModelGUI;
import com.lykkex.LykkeWallet.gui.fragments.startscreen.FieldFragment;
import com.lykkex.LykkeWallet.gui.fragments.startscreen.FieldFragment_;
import com.lykkex.LykkeWallet.gui.fragments.startscreen.SignInFragment;
import com.lykkex.LykkeWallet.gui.fragments.startscreen.SignInFragment_;
import com.lykkex.LykkeWallet.gui.utils.Constants;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

/**
 * Created by Murtic on 31/05/16.
 */

@EActivity(R.layout.activity_main)
public class SignInActivity extends BaseActivity {

    @AfterViews
    public void afterViews() {
        initFragment(new SignInFragment_(), null);
    }
}