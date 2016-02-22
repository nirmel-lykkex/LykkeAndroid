package com.lykkex.LykkeWallet.gui;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.ActionBar;

import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.gui.fragments.FieldFragment_;
import com.lykkex.LykkeWallet.gui.fragments.storage.SetUpPref_;
import com.lykkex.LykkeWallet.gui.fragments.storage.UserPref_;
import com.lykkex.LykkeWallet.gui.utils.Constants;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.sharedpreferences.Pref;

/**
 * Created by LIZA on 21.02.2016.
 */
@EActivity(R.layout.splash_activity)
public class SplashActivity extends Activity{

    @Pref SetUpPref_ setUpPref;
    @Pref UserPref_ userPref;
    
    @AfterViews
    public void afterViews(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!setUpPref.isCheckingStatusStart().get() && !setUpPref.isSelfieStatusStart().get()
                        && setUpPref.kysStatusStart().get().isEmpty()) {
                    Intent intent = new Intent();
                    intent.setClass(getApplicationContext(), FieldActivity_.class);
                    startActivity(intent);
                    finish();
                } else if (setUpPref.isSelfieStatusStart().get() &&
                        setUpPref.kysStatusStart().get().isEmpty()){
                    Intent intent = new Intent();
                    intent.setClass(getApplicationContext(), SelfieActivity_.class);
                    finish();
                    startActivity(intent);
                } else if (!setUpPref.kysStatusStart().get().isEmpty() &&
                        !setUpPref.isInteredPin().get()){
                    Intent intent = new Intent();
                    intent.setClass(getApplicationContext(), KysActivity_.class);
                    finish();
                    startActivity(intent);
                } else if (setUpPref.isInteredPin().get()){
                    Intent intent = new Intent();
                    intent.setClass(getApplicationContext(), PinActivity_.class);
                    finish();
                    startActivity(intent);
                }
            }
        }, Constants.DELAY_5000);
    }
}
