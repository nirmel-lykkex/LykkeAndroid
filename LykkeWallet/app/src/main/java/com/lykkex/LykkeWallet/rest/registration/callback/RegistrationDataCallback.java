package com.lykkex.LykkeWallet.rest.registration.callback;

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
    public RegistrationDataCallback(ProgressBar progressBar){
        this.progressBar = progressBar;
        progressBar.setVisibility(View.VISIBLE);
    }
    @Override
    public void onResponse(Call<RegistrationData> call, Response<RegistrationData> response) {
        progressBar.setVisibility(View.GONE);
        if (response != null && response.body() != null && response.body().getError()==null) {
            Toast.makeText(LykkeApplication_.getInstance(), "Success", Toast.LENGTH_SHORT).show();
        }else if (response != null && response.body() != null){
            Toast.makeText(LykkeApplication_.getInstance(), "Fail", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFailure(Call<RegistrationData> call, Throwable t) {
        progressBar.setVisibility(View.GONE);
        Toast.makeText(LykkeApplication_.getInstance(), "Fail", Toast.LENGTH_SHORT).show();
    }
}
