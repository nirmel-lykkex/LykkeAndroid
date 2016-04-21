package com.lykkex.LykkeWallet.rest.registration.callback;

import android.app.Activity;
import android.view.View;
import android.widget.ProgressBar;

import com.lykkex.LykkeWallet.gui.utils.validation.CallBackListener;
import com.lykkex.LykkeWallet.rest.base.models.BaseCallBack;
import com.lykkex.LykkeWallet.rest.registration.response.models.RegistrationData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by e.kazimirova on 10.02.2016.
 */
public class RegistrationDataCallback extends BaseCallBack<RegistrationData> {


    public RegistrationDataCallback(ProgressBar progressBar, CallBackListener listener, Activity activity) {
        super(listener, activity);
    }

    @Override
    public void onResponse(Call<RegistrationData> call, Response<RegistrationData> response) {
        super.onResponse(call, response);
        if (!isCancel) {
            if (response != null && response.body() != null && response.body().getError() == null) {
                listener.onSuccess(response.body());
            } else if (response != null && response.body() != null &&
                    response.body().getError() != null) {
                setUpError(response.body().getError().getMessage());
            }
        }
    }

    @Override
    public void onFailure(Call<RegistrationData> call, Throwable t) {
        //progressBar.setVisibility(View.GONE);
        if (!isCancel) {
            listener.onFail(null);
        }
    }
}
