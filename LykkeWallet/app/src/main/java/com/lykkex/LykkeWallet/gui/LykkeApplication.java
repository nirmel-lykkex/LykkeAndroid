package com.lykkex.LykkeWallet.gui;

import android.app.Application;

import com.crashlytics.android.Crashlytics;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EApplication;

import io.fabric.sdk.android.Fabric;

/**
 * Created by e.kazimirova on 08.02.2016.
 */
@EApplication
public class LykkeApplication extends Application {


    @AfterInject
    public void init() {
        Fabric.with(this, new Crashlytics());
    }
}
