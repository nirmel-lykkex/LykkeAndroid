package com.lykkex.LykkeWallet.rest.trading.callback;

import android.app.Activity;

import com.lykkex.LykkeWallet.gui.utils.validation.CallBackListener;
import com.lykkex.LykkeWallet.rest.base.models.BaseCallBack;
import com.lykkex.LykkeWallet.rest.trading.response.model.AssetPairData;
import com.lykkex.LykkeWallet.rest.trading.response.model.EmailData;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by LIZA on 15.04.2016.
 */
public class EmailCallBack extends BaseCallBack<EmailData> {

    public EmailCallBack(CallBackListener listener, Activity activity) {
        super(listener, activity);
    }

    @Override
    public void onResponse(Call<EmailData> call, Response<EmailData> response) {
        super.onResponse(call, response);
        if (!isCancel) {
            if (response != null && response.body() != null && response.body().getError() == null) {
                listener.onSuccess(response.body().getResult());
            } else if (response != null && response.body() != null) {
                listener.onFail(response.body().getError());
            }
        }
    }

    @Override
    public void onFailure(Call<EmailData> call, Throwable t) {
        if (!isCancel) {
            listener.onFail(null);
        }
    }
}
