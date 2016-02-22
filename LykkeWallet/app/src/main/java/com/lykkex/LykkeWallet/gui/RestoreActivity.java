package com.lykkex.LykkeWallet.gui;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ProgressBar;

import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.gui.fragments.models.KysStatusEnum;
import com.lykkex.LykkeWallet.gui.fragments.storage.UserPref_;
import com.lykkex.LykkeWallet.gui.utils.Constants;
import com.lykkex.LykkeWallet.gui.utils.validation.CallBackListener;
import com.lykkex.LykkeWallet.rest.base.models.Error;
import com.lykkex.LykkeWallet.rest.login.callback.LoginDataCallback;
import com.lykkex.LykkeWallet.rest.login.response.model.AuthModelData;
import com.lykkex.LykkeWallet.rest.login.response.model.AuthModelResult;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import retrofit2.Call;

/**
 * Created by LIZA on 22.02.2016.
 */
@EActivity(R.layout.restore_activity)
public class RestoreActivity extends Activity implements CallBackListener{

    @ViewById ProgressBar progressBar;
    @Pref UserPref_ userPref;

    @AfterViews
    public void afterViews(){
        progressBar.setVisibility(View.VISIBLE);
        LoginDataCallback callback = new LoginDataCallback(progressBar, this, this);
        Call<AuthModelData> call = LykkeApplication_.getInstance().getRestApi().
                getRegistrationData(userPref.authToken().get());
        call.enqueue(callback);
    }

    @Override
    public void onSuccess(Object result) {
        progressBar.setVisibility(View.GONE);
        if (result != null && result instanceof AuthModelResult) {
            AuthModelResult res = (AuthModelResult) result;
            switch (KysStatusEnum.valueOf(res.getKycStatus())){
                case Ok:
                    if (res.getPinIsEntered()) {
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
                    intentSelfie.setClass(this, SelfieActivity_.class);
                    startActivity(intentSelfie);
                    finish();
                    break;
                case RestrictedArea:
                    Intent intentRestrictedArea = new Intent();
                    intentRestrictedArea.putExtra(Constants.EXTRA_KYS_STATUS,
                            KysStatusEnum.RestrictedArea);
                    intentRestrictedArea.setClass(this, KysActivity_.class);
                    startActivity(intentRestrictedArea);
                    finish();
                    break;
                case Reject:
                    Intent intentKys = new Intent();
                    intentKys.putExtra(Constants.EXTRA_KYS_STATUS,
                            KysStatusEnum.Reject);
                    intentKys.setClass(this, KysActivity_.class);
                    startActivity(intentKys);
                    finish();
                    break;
            }
        }
    }

    @Override
    public void onFail(Error error) {
        progressBar.setVisibility(View.GONE);
    }
}
