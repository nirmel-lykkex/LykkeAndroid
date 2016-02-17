package com.lykkex.LykkeWallet.rest.camera.callback;

import com.lykkex.LykkeWallet.gui.utils.validation.CallBackListener;
import com.lykkex.LykkeWallet.rest.camera.response.models.PersonData;
import com.lykkex.LykkeWallet.rest.login.response.model.PersonalData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by e.kazimirova on 10.02.2016.
 */
public class SubmitDocumentsDataCallback implements Callback<PersonalData> {

    private CallBackListener listener;

    public SubmitDocumentsDataCallback(CallBackListener listener){
        this.listener = listener;
    }
    @Override
    public void onResponse(Call<PersonalData> call, Response<PersonalData> response) {
        if (response != null && response.body() != null) {
            listener.onSuccess(null);
        }else if (response != null){
            listener.onFail(null);
        }
    }

    @Override
    public void onFailure(Call<PersonalData> call, Throwable t) {
        listener.onFail(null);
    }
}
