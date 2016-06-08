package com.lykkex.LykkeWallet.gui.activity.authentication;

import android.os.Bundle;

import com.crashlytics.android.Crashlytics;
import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.gui.activity.BaseActivity;
import com.lykkex.LykkeWallet.gui.fragments.registration.RegistrationStep1Fragment_;
import com.lykkex.LykkeWallet.gui.fragments.registration.RegistrationStep2Fragment_;
import com.lykkex.LykkeWallet.gui.fragments.registration.RegistrationStep3Fragment_;
import com.lykkex.LykkeWallet.gui.fragments.registration.RegistrationStep4Fragment_;
import com.lykkex.LykkeWallet.gui.fragments.startscreen.DepositFragment;
import com.lykkex.LykkeWallet.gui.fragments.startscreen.DepositFragment_;
import com.lykkex.LykkeWallet.gui.fragments.startscreen.SignInFragment_;
import com.lykkex.LykkeWallet.gui.fragments.storage.UserPref_;
import com.lykkex.LykkeWallet.gui.utils.Constants;

import org.androidannotations.annotations.AfterExtras;
import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;

import io.fabric.sdk.android.Fabric;

/**
 * Created by Murtic on 31/05/16.
 *
 * This activity is responsible for sign in and sign up process
 */

@EActivity(R.layout.activity_main)
public class SignInActivity extends BaseActivity {

    public static final int REGISTRATION_STEP_1 = 1;
    public static final int REGISTRATION_STEP_2 = 2;
    public static final int REGISTRATION_STEP_3 = 3;
    public static final int REGISTRATION_STEP_4 = 4;

    @Extra("fragmentId")
    Integer fragmentId = 0;

    private UserPref_ pref;

    @AfterExtras
    public void onExtras() {
        pref = new UserPref_(this);

        Bundle bundle = new Bundle();
        bundle.putBoolean(Constants.SKIP_BACKSTACK, true);

        switch (fragmentId) {
            case REGISTRATION_STEP_1:
                initFragment(new RegistrationStep1Fragment_(), bundle);

                break;
            case REGISTRATION_STEP_2:
                initFragment(new RegistrationStep2Fragment_(), bundle);

                break;
            case REGISTRATION_STEP_3:
                initFragment(new RegistrationStep3Fragment_(), bundle);

                break;
            case REGISTRATION_STEP_4:
                initFragment(new RegistrationStep4Fragment_(), bundle);

                break;
            default:
                if(pref.isDepositVisible().get()) {
                    initFragment(new DepositFragment_(), bundle);

                    pref.isDepositVisible().put(false);
                } else {
                    initFragment(new SignInFragment_(), bundle);
                }
        }
    }
}