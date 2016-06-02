package com.lykkex.LykkeWallet.gui.activity.authentication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.gui.LykkeApplication_;
import com.lykkex.LykkeWallet.gui.activity.KysActivity_;
import com.lykkex.LykkeWallet.gui.fragments.models.KysStatusEnum;
import com.lykkex.LykkeWallet.gui.fragments.storage.UserPref_;
import com.lykkex.LykkeWallet.gui.models.SettingSinglenton;
import com.lykkex.LykkeWallet.gui.utils.Constants;
import com.lykkex.LykkeWallet.gui.utils.LykkeUtils;
import com.lykkex.LykkeWallet.gui.utils.validation.CallBackListener;
import com.lykkex.LykkeWallet.gui.widgets.ErrorDialog;
import com.lykkex.LykkeWallet.rest.base.models.Error;
import com.lykkex.LykkeWallet.rest.login.callback.LoginDataCallback;
import com.lykkex.LykkeWallet.rest.login.response.model.AuthModelData;
import com.lykkex.LykkeWallet.rest.login.response.model.AuthModelResult;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.util.Random;

import retrofit2.Call;

/**
 * Created by LIZA on 22.02.2016.
 */
@EActivity
public abstract class BaseAuthenticationActivity extends Activity implements CallBackListener {

    protected  @ViewById ProgressBar progressBar;
    protected  @Pref  UserPref_ userPref;
    protected Handler mHandler = new Handler();
    protected int count = 0;
    private Runnable run = new Runnable() {
        @Override
        public void run() {
            startRequest();
        }
    };

    public void onStart(){
        super.onStart();
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onSuccess(Object result) {
       // progressBar.setVisibility(View.GONE);
        if (result instanceof Error) {
            onFail(result);
        } else if  (result != null && result instanceof AuthModelData) {
            AuthModelData res = (AuthModelData) result;
            if (res.getResult().getPersonalData().getAddress() != null) {
                userPref.address().put(res.getResult().getPersonalData().getAddress());
            }

            if (res.getResult().getToken() != null) {
                userPref.authToken().put(res.getResult().getToken());
            }

            if (res.getResult().getPersonalData().getCity() != null) {
                userPref.city().put(res.getResult().getPersonalData().getCity());
            }

            if (res.getResult().getPersonalData().getCountry() != null) {
                userPref.country().put(res.getResult().getPersonalData().getCountry());
            }

            if (res.getResult().getPersonalData().getEmail() != null) {
                userPref.email().put(res.getResult().getPersonalData().getEmail());
            }

            if (res.getResult().getPersonalData().getFullName() != null) {
                userPref.fullName().put(res.getResult().getPersonalData().getFullName());
            }

            if (res.getResult().getPersonalData().getPhone() != null) {
                userPref.phone().put(res.getResult().getPersonalData().getPhone());
            }

            if (res.getResult().getPersonalData().getZip() != null) {
                userPref.zip().put(res.getResult().getPersonalData().getZip());
            }
            switch (KysStatusEnum.valueOf(res.getResult().getKycStatus())){
                case RestrictedArea:

                case Rejected:

                case Pending:
                    Intent intentKysPending = new Intent();
                    intentKysPending.putExtra(Constants.EXTRA_KYS_STATUS,
                            KysStatusEnum.valueOf(res.getResult().getKycStatus()));
                    intentKysPending.setClass(this, KysActivity_.class);
                    startActivity(intentKysPending);
                    finish();
                    break;
            }
        }
    }

    protected abstract void startRequest();

    @Override
    public void onFail(Object error) {
        count +=1;
        mHandler.removeCallbacks(run);

        if (count <= 3 && (error == null || ((Error)error).getCode() != Constants.ERROR_401)) {
            mHandler.postDelayed(run, Constants.DELAY_5000);
        } else {
            userPref.clear();
            setUpError(getString(R.string.not_authorized));
        }

    }

    protected void setUpError(String error) {
        if (SettingSinglenton.getInstance().isDebugMode()) {
            Toast.makeText(this, error, Toast.LENGTH_LONG).show();
        } else {
            LykkeUtils.showError(getFragmentManager(), error);
        }
    }
}


