package com.lykkex.LykkeWallet.rest.registration.callback;

import com.lykkex.LykkeWallet.gui.utils.validation.ValidationListener;
import com.lykkex.LykkeWallet.rest.registration.models.AccountExisData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by e.kazimirova on 10.02.2016.
 */
public class AccountExisDataCallback implements Callback<AccountExisData> {

    private ValidationListener listener;

    public AccountExisDataCallback(ValidationListener listener) {
        this.listener = listener;
    }

    @Override
    public void onResponse(Call<AccountExisData> call, Response<AccountExisData> response) {
        if (response != null && response.body() != null && response.body().getError()==null) {
            listener.onSuccess(response.body().getResult());
        }else if (response != null && response.body() != null){
            listener.onFail(response.body().getError());
        }
    }

    @Override
    public void onFailure(Call<AccountExisData> call, Throwable t) {
        listener.onFail(null); //TODO
    }
}
