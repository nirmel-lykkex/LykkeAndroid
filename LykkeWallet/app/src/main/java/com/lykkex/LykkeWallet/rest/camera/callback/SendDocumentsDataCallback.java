package com.lykkex.LykkeWallet.rest.camera.callback;

import android.view.View;
import android.widget.ProgressBar;

import com.lykkex.LykkeWallet.gui.utils.validation.CallBackListener;
import com.lykkex.LykkeWallet.rest.camera.response.models.PersonData;
import com.lykkex.LykkeWallet.rest.registration.response.models.RegistrationData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by e.kazimirova on 10.02.2016.
 */
public class SendDocumentsDataCallback implements Callback<PersonData> {

    private CallBackListener listener;

    public SendDocumentsDataCallback(CallBackListener listener){
        this.listener = listener;
    }
    @Override
    public void onResponse(Call<PersonData> call, Response<PersonData> response) {
        if (response != null && response.body() != null && response.body().getError()==null) {
            listener.onSuccess(null);
        }else if (response != null){
            listener.onFail(null);
        }
    }

    @Override
    public void onFailure(Call<PersonData> call, Throwable t) {
        listener.onFail(null);
    }
}
