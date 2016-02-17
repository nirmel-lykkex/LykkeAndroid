package com.lykkex.LykkeWallet.gui.fragments.storage;

import org.androidannotations.annotations.sharedpreferences.DefaultBoolean;
import org.androidannotations.annotations.sharedpreferences.SharedPref;

/**
 * Created by LIZA on 17.02.2016.
 */
@SharedPref(value=SharedPref.Scope.APPLICATION_DEFAULT)
public interface SetUpPref {

    @DefaultBoolean(false)
    boolean isCheckingStatusStart();
}
