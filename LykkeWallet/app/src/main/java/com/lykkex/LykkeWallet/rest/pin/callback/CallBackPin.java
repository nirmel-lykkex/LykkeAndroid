package com.lykkex.LykkeWallet.rest.pin.callback;

import com.lykkex.LykkeWallet.gui.utils.validation.CallBackListener;
import com.lykkex.LykkeWallet.rest.base.models.Error;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by LIZA on 19.02.2016.
 */
public class CallBackPin implements Callback<Error> {

    private CallBackListener listener;

    public CallBackPin(CallBackListener listener){
        this.listener = listener;
    }

    @Override
    public void onResponse(Call<Error> call, Response<Error> response) {
        if (response != null && response.errorBody() == null) {
            listener.onSuccess(null);
        } else {
            listener.onFail(null);
        }
    }

    @Override
    public void onFailure(Call<Error> call, Throwable t) {
        listener.onFail(null);
    }
}
