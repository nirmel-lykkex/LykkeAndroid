package com.lykkex.LykkeWallet.gui.activity.authentication;

import android.util.Log;

import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.gui.LykkeApplication;
import com.lykkex.LykkeWallet.gui.utils.LykkeUtils;
import com.lykkex.LykkeWallet.rest.login.response.model.AuthModelData;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.EActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by LIZA on 22.02.2016.
 */
@EActivity(R.layout.restore_activity)
public class RestoreActivity extends BaseAuthenticationActivity {

    @App
    LykkeApplication application;

    public void onStart(){
        super.onStart();

        startRequest();
    }

    public void startRequest(){
        Call<AuthModelData> call = application.getRestApi().getRegistrationData();

        call.enqueue(new Callback<AuthModelData>() {
            @Override
            public void onResponse(Call<AuthModelData> call, Response<AuthModelData> response) {
                if(!response.isSuccess() || response.body() == null || response.body().getResult() == null) {
                    Log.e("ERROR", "Unexpected error while restoring session: " +
                            userPref.email().get() + ", " + response.errorBody());

                    LykkeUtils.showError(getFragmentManager(), "Unexpected error while restoring session.");

                    return;
                }

                RestoreActivity.this.onSuccess(response.body());

                handleNextStep(response.body());
            }

            @Override
            public void onFailure(Call<AuthModelData> call, Throwable t) {
                RestoreActivity.this.onFail(null);
            }
        });
    }
}
