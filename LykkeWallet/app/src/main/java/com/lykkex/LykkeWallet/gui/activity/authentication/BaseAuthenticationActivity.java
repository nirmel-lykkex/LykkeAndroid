package com.lykkex.LykkeWallet.gui.activity.authentication;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.gui.LykkeApplication;
import com.lykkex.LykkeWallet.gui.activity.KysActivity_;
import com.lykkex.LykkeWallet.gui.activity.pin.EnterPinActivity_;
import com.lykkex.LykkeWallet.gui.activity.pin.SetUpPinActivity_;
import com.lykkex.LykkeWallet.gui.activity.selfie.CameraActivity_;
import com.lykkex.LykkeWallet.gui.fragments.models.KysStatusEnum;
import com.lykkex.LykkeWallet.gui.fragments.storage.UserPref_;
import com.lykkex.LykkeWallet.gui.managers.SettingManager;
import com.lykkex.LykkeWallet.gui.utils.Constants;
import com.lykkex.LykkeWallet.gui.utils.LykkeUtils;
import com.lykkex.LykkeWallet.rest.base.models.Error;
import com.lykkex.LykkeWallet.rest.camera.response.models.CameraData;
import com.lykkex.LykkeWallet.rest.login.response.model.AuthModelData;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by LIZA on 22.02.2016.
 */
@EActivity
public abstract class BaseAuthenticationActivity extends AppCompatActivity {

    protected @ViewById ProgressBar progressBar;
    protected @Pref  UserPref_ userPref;
    protected Handler mHandler = new Handler();
    protected int count = 0;
    private Runnable run = new Runnable() {
        @Override
        public void run() {
            startRequest();
        }
    };
    protected @ViewById TextView textView;

    @App
    protected LykkeApplication lykkeApplication;

    public void onStart(){
        super.onStart();
        progressBar.setVisibility(View.VISIBLE);
    }

    public void onSuccess(Object result) {
        progressBar.setVisibility(View.GONE);

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

    public void onFail(Object error) {
        if(!(error instanceof Error)) return;

        count +=1;

        mHandler.removeCallbacks(run);

        if (count <= 3 && (error == null || ((Error)error).getCode() != Constants.ERROR_401)) {
            mHandler.postDelayed(run, Constants.DELAY_5000);
        } else {
            userPref.clear();
            userPref.isDepositVisible().put(false);

            setUpError(getString(R.string.not_authorized));
        }
    }

    protected void setUpError(String error) {
        if (SettingManager.getInstance().isDebugMode()) {
            Toast.makeText(this, error, Toast.LENGTH_LONG).show();
        } else {
            LykkeUtils.showError(getFragmentManager(), error);
        }
    }

    protected void handleNextStep(AuthModelData data) {
        switch (KysStatusEnum.valueOf(data.getResult().getKycStatus())){
            case Ok:
                if (data.getResult().getPinIsEntered()) {
                    Intent intent = new Intent();
                    intent.setClass(getBaseContext(), EnterPinActivity_.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent();
                    intent.setClass(getBaseContext(), SetUpPinActivity_.class);
                    startActivity(intent);
                    finish();
                }
                break;
            case NeedToFillData:
                String fullName = data.getResult().getPersonalData().getFullName();
                String phoneNumber = data.getResult().getPersonalData().getPhone();

                if(fullName == null || fullName.isEmpty()) {
                    Intent intent = new Intent();
                    intent.setClass(getBaseContext(), SignInActivity_.class);
                    intent.putExtra("fragmentId", SignInActivity.REGISTRATION_STEP_3);

                    startActivity(intent);
                } else if(phoneNumber == null || phoneNumber.isEmpty()) {
                    Intent intent = new Intent();
                    intent.setClass(getBaseContext(), SignInActivity_.class);
                    intent.putExtra("fragmentId", SignInActivity.REGISTRATION_STEP_4);

                    startActivity(intent);
                } else {
                    progressBar.setVisibility(View.VISIBLE);

                    textView.setText(R.string.check_documents);

                    Call<CameraData> callCamera = lykkeApplication.getRestApi().checkDocuments();
                    callCamera.enqueue(new Callback<CameraData>() {
                        @Override
                        public void onResponse(Call<CameraData> call, Response<CameraData> response) {
                            progressBar.setVisibility(View.GONE);

                            if (!response.isSuccess() || response.body() == null) {
                                Log.e("ERROR", "Unexpected error while checking user documents: " +
                                        userPref.email().get() + ", " + response.errorBody());

                                LykkeUtils.showError(getFragmentManager(), "Unexpected error while checking user documents.");

                                return;
                            }

                            Intent intent = new Intent();
                            intent.putExtra(Constants.EXTRA_CAMERA_DATA, response.body().getResult());
                            intent.setClass(getBaseContext(), CameraActivity_.class);
                            startActivity(intent);
                            finish();
                        }

                        @Override
                        public void onFailure(Call<CameraData> call, Throwable t) {
                            progressBar.setVisibility(View.GONE);

                            BaseAuthenticationActivity.this.onFail(null);
                        }
                    });
                }
                break;
        }
    }
}


