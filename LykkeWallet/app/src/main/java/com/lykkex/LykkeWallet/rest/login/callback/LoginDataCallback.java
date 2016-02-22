package com.lykkex.LykkeWallet.rest.login.callback;

import android.app.Activity;
import android.view.View;
import android.widget.ProgressBar;

import com.lykkex.LykkeWallet.gui.utils.validation.CallBackListener;
import com.lykkex.LykkeWallet.rest.base.models.BaseCallBack;
import com.lykkex.LykkeWallet.rest.login.response.model.AuthModelData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by e.kazimirova on 10.02.2016.
 */
public class LoginDataCallback extends BaseCallBack<AuthModelData> {

    private ProgressBar progressBar;

    public LoginDataCallback(ProgressBar progressBar, CallBackListener listener, Activity activity) {
        super(listener, activity);
        this.progressBar = progressBar;
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onResponse(Call<AuthModelData> call, Response<AuthModelData> response) {
        super.onResponse(call, response);
        progressBar.setVisibility(View.GONE);
        if (!isCancel) {
            if (response != null && response.body() != null && response.body().getError() == null) {
                listener.onSuccess(response);
            } else if (response != null && response.body() != null) {
                listener.onFail(null);
            }
        }
    }

    @Override
    public void onFailure(Call<AuthModelData> call, Throwable t) {
        progressBar.setVisibility(View.GONE);
        if (!isCancel) {
            listener.onFail(null);
        }
    }
}
