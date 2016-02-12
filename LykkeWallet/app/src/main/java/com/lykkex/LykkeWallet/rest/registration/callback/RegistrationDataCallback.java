package com.lykkex.LykkeWallet.rest.registration.callback;

import android.app.Activity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.lykkex.LykkeWallet.gui.LykkeApplication_;
import com.lykkex.LykkeWallet.gui.utils.validation.ValidationListener;
import com.lykkex.LykkeWallet.rest.registration.response.models.AccountExistData;
import com.lykkex.LykkeWallet.rest.registration.response.models.RegistrationData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by e.kazimirova on 10.02.2016.
 */
public class RegistrationDataCallback implements Callback<RegistrationData> {

    private ProgressBar progressBar;
    private ValidationListener listener;

    public RegistrationDataCallback(ProgressBar progressBar, ValidationListener listener){
        this.progressBar = progressBar;
        progressBar.setVisibility(View.VISIBLE);
        this.listener = listener;
    }
    @Override
    public void onResponse(Call<RegistrationData> call, Response<RegistrationData> response) {
        progressBar.setVisibility(View.GONE);
        if (response != null && response.body() != null && response.body().getError()==null) {
            listener.onSuccess(null);
        }else if (response != null && response.body() != null){
            listener.onFail(null);
        }
    }

    @Override
    public void onFailure(Call<RegistrationData> call, Throwable t) {
        progressBar.setVisibility(View.GONE);
        listener.onFail(null);
    }
}
