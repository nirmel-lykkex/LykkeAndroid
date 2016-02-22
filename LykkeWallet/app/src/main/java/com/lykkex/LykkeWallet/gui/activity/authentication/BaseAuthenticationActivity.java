package com.lykkex.LykkeWallet.gui.activity.authentication;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ProgressBar;

import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.gui.EnterPinActivity_;
import com.lykkex.LykkeWallet.gui.KysActivity_;
import com.lykkex.LykkeWallet.gui.SetUpPinActivity_;
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
public class BaseAuthenticationActivity extends Activity implements CallBackListener {

    protected  @ViewById ProgressBar progressBar;
    protected  @Pref  UserPref_ userPref;

    @AfterViews
    public void afterViews(){
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onSuccess(Object result) {
        progressBar.setVisibility(View.GONE);
        if (result != null && result instanceof AuthModelData) {
            AuthModelData res = (AuthModelData) result;
            userPref.address().put(res.getResult().getPersonalData().getAddress());
            userPref.authToken().put(res.getResult().getToken());
            userPref.city().put(res.getResult().getPersonalData().getCity());
            userPref.country().put(res.getResult().getPersonalData().getCountry());
            userPref.email().put(res.getResult().getPersonalData().getEmail());
            userPref.fullName().put(res.getResult().getPersonalData().getFullName());
            userPref.phone().put(res.getResult().getPersonalData().getPhone());
            userPref.zip().put(res.getResult().getPersonalData().getZip());

            switch (KysStatusEnum.valueOf(res.getResult().getKycStatus())){
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

    @Override
    public void onFail(Error error) {
        progressBar.setVisibility(View.GONE);
    }
}


