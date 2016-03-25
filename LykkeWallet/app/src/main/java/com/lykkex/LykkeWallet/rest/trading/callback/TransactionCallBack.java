package com.lykkex.LykkeWallet.rest.trading.callback;

import android.app.Activity;

import com.lykkex.LykkeWallet.gui.utils.validation.CallBackListener;
import com.lykkex.LykkeWallet.rest.base.models.BaseCallBack;
import com.lykkex.LykkeWallet.rest.trading.response.model.DescriptionData;
import com.lykkex.LykkeWallet.rest.trading.response.model.TransactionData;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by e.kazimirova on 16.03.2016.
 */
public class TransactionCallBack extends BaseCallBack<TransactionData> {

    public TransactionCallBack(CallBackListener listener, Activity activity) {
        super(listener, activity);
    }

    @Override
    public void onResponse(Call<TransactionData> call, Response<TransactionData> response) {
        super.onResponse(call, response);

        if (response != null && response.body() != null && response.body().getError() == null) {
            listener.onSuccess(response.body().getResult());
        } else if (response != null && response.body() != null) {
            listener.onFail(response.body().getError());
        }

    }

    @Override
    public void onFailure(Call<TransactionData> call, Throwable t) {

        listener.onFail(null);

    }
}