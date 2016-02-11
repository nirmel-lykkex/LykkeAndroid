package com.lykkex.LykkeWallet.rest.registration.callback;

import com.lykkex.LykkeWallet.gui.utils.validation.ValidationListener;
import com.lykkex.LykkeWallet.rest.registration.response.models.AccountExistData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by e.kazimirova on 10.02.2016.
 */
public class AccountExistDataCallback implements Callback<AccountExistData> {

    private ValidationListener listener;

    public AccountExistDataCallback(ValidationListener listener) {
        this.listener = listener;
    }

    @Override
    public void onResponse(Call<AccountExistData> call, Response<AccountExistData> response) {
        if (response != null && response.body() != null && response.body().getError()==null) {
            listener.onSuccess(response.body().getResult());
        }else if (response != null && response.body() != null){
            listener.onFail(response.body().getError());
        }
    }

    @Override
    public void onFailure(Call<AccountExistData> call, Throwable t) {
        listener.onFail(null); //TODO
    }
}
