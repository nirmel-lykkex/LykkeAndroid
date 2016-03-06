package com.lykkex.LykkeWallet.gui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.util.Log;

import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.gui.activity.authentication.FieldActivity_;
import com.lykkex.LykkeWallet.gui.activity.authentication.RestoreActivity_;
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

    @Pref UserPref_ userPref;
    
    @AfterViews
    public void afterViews(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (userPref.authToken().get().isEmpty()) {
                    Intent intent = new Intent();
                    intent.setClass(getApplicationContext(), FieldActivity_.class);
                    startActivity(intent);
                    finish();
                } else {
                    Log.e("Liza ", "Restore started!!!");
                    Intent intent = new Intent();
                    intent.setClass(getApplicationContext(), RestoreActivity_.class);
                    finish();
                    startActivity(intent);
                }            }
        }, Constants.DELAY_5000);
    }
}
