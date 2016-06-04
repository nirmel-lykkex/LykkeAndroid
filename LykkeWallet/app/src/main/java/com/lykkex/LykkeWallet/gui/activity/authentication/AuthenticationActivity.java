package com.lykkex.LykkeWallet.gui.activity.authentication;

import android.graphics.Point;
import android.os.Build;
import android.util.Log;

import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.gui.fragments.models.AuthModelGUI;
import com.lykkex.LykkeWallet.gui.managers.UserManager;
import com.lykkex.LykkeWallet.gui.utils.Constants;
import com.lykkex.LykkeWallet.gui.utils.LykkeUtils;
import com.lykkex.LykkeWallet.rest.login.response.model.AuthModelData;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by LIZA on 22.02.2016.
 */
@EActivity(R.layout.restore_activity)
public class AuthenticationActivity extends BaseAuthenticationActivity {

    public static Integer AUTHENTICATION_REQUEST_CODE = 901;

    @Bean
    UserManager userManager;

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

                // Close parent activity in case everything is fines
                setResult(RESULT_OK);

                userManager.setRegistrationResult(response.body().getResult());

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
