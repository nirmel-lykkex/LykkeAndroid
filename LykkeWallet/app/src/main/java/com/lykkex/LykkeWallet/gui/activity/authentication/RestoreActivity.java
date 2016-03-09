package com.lykkex.LykkeWallet.gui.activity.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.gui.LykkeApplication_;
import com.lykkex.LykkeWallet.gui.activity.KysActivity_;
import com.lykkex.LykkeWallet.gui.activity.pin.EnterPinActivity_;
import com.lykkex.LykkeWallet.gui.activity.pin.SetUpPinActivity_;
import com.lykkex.LykkeWallet.gui.activity.selfie.CameraActivity_;
import com.lykkex.LykkeWallet.gui.fragments.models.KysStatusEnum;
import com.lykkex.LykkeWallet.gui.utils.Constants;
import com.lykkex.LykkeWallet.gui.utils.validation.CallBackListener;
import com.lykkex.LykkeWallet.rest.base.models.Error;
import com.lykkex.LykkeWallet.rest.login.callback.LoginDataCallback;
import com.lykkex.LykkeWallet.rest.login.response.model.AuthModelData;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

import retrofit2.Call;

/**
 * Created by LIZA on 22.02.2016.
 */
@EActivity(R.layout.restore_activity)
public class RestoreActivity extends BaseAuthenticationActivity implements CallBackListener{


    public void onStart(){
        super.onStart();
         startRequest();
    }

    public void startRequest(){
        LoginDataCallback callback = new LoginDataCallback(progressBar, this, this);
        Call<AuthModelData> call = LykkeApplication_.getInstance().getRestApi().
                getRegistrationData(Constants.PART_AUTHORIZATION + userPref.authToken().get());
        call.enqueue(callback);
    }

    @Override
    public void onSuccess(Object result) {
        super.onSuccess(result);
        if (result != null && result instanceof AuthModelData) {
            AuthModelData res = (AuthModelData) result;
            switch (KysStatusEnum.valueOf(res.getResult().getKycStatus())) {
                case Ok:
                    if (res.getResult().getPinIsEntered()) {
                        Intent intent = new Intent();
                        intent.setClass(this, EnterPinActivity_.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Intent intent = new Intent();
                        intent.setClass(this, SetUpPinActivity_.class);
                        startActivity(intent);
                        finish();
                    }
                    break;
                case NeedToFillData:
                    Intent intentSelfie = new Intent();
                    intentSelfie.setClass(this,  CameraActivity_.class);
                    startActivity(intentSelfie);
                    finish();
                    break;
            }
        }
    }


}
