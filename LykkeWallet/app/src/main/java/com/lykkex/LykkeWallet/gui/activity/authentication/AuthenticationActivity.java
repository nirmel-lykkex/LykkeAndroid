package com.lykkex.LykkeWallet.gui.activity.authentication;

import android.content.Intent;
import android.graphics.Point;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.gui.LykkeApplication;
import com.lykkex.LykkeWallet.gui.LykkeApplication_;
import com.lykkex.LykkeWallet.gui.activity.pin.EnterPinActivity_;
import com.lykkex.LykkeWallet.gui.activity.pin.SetUpPinActivity_;
import com.lykkex.LykkeWallet.gui.activity.selfie.CameraActivity_;
import com.lykkex.LykkeWallet.gui.fragments.models.AuthModelGUI;
import com.lykkex.LykkeWallet.gui.fragments.models.KysStatusEnum;
import com.lykkex.LykkeWallet.gui.fragments.registration.RegistrationStep3Fragment_;
import com.lykkex.LykkeWallet.gui.utils.Constants;
import com.lykkex.LykkeWallet.gui.utils.LykkeUtils;
import com.lykkex.LykkeWallet.gui.utils.validation.CallBackListener;
import com.lykkex.LykkeWallet.rest.base.models.Error;
import com.lykkex.LykkeWallet.rest.camera.callback.CheckDocumentCallBack;
import com.lykkex.LykkeWallet.rest.camera.response.models.CameraData;
import com.lykkex.LykkeWallet.rest.camera.response.models.CameraResult;
import com.lykkex.LykkeWallet.rest.login.callback.LoginDataCallback;
import com.lykkex.LykkeWallet.rest.login.response.model.AuthModelData;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by LIZA on 22.02.2016.
 */
@EActivity(R.layout.restore_activity)
public class AuthenticationActivity extends BaseAuthenticationActivity {

    public static Integer AUTHENTICATION_REQUEST_CODE = 901;

    public void onStart(){
        super.onStart();
        startRequest();
    }

    protected void startRequest(){
        AuthModelGUI authRequest = (AuthModelGUI) getIntent().getExtras().getSerializable(Constants.EXTRA_AUTH_REQUEST);

        Point point = new Point();

        getWindowManager().getDefaultDisplay().getSize(point);

        authRequest.setClientInfo("<android>; Model:<" + Build.MODEL +">; Os:<android>; Screen:<"+
                point.x+"x" + point.y+">;");

        Call<AuthModelData> call = lykkeApplication.getRestApi().getAuth(authRequest);

        call.enqueue(new Callback<AuthModelData>() {
            @Override
            public void onResponse(Call<AuthModelData> call, Response<AuthModelData> response) {
                if(!response.isSuccess() || response.body() == null || (response.body().getError() != null && response.body().getError().getCode() != 2)) {
                    Log.e("ERROR", "Unexpected error while authenticating user: " +
                            userPref.email().get() + ", " + response.errorBody());

                    LykkeUtils.showError(getFragmentManager(), "Unexpected error while authenticating user.", new Runnable() {
                        @Override
                        public void run() {
                            finish();
                        }
                    });

                    return;
                }

                if(response.body().getError() != null && response.body().getError().getCode() == 2) {
                    LykkeUtils.showError(getFragmentManager(), response.body().getError().getMessage(), new Runnable() {
                        @Override
                        public void run() {
                            finish();
                        }
                    });

                    return;
                }

                finishActivity(AUTHENTICATION_REQUEST_CODE);

                AuthenticationActivity.this.onSuccess(response.body());

                handleNextStep(response.body());
            }

            @Override
            public void onFailure(Call<AuthModelData> call, Throwable t) {
                AuthenticationActivity.this.onFail(null);
            }
        });
    }
}
