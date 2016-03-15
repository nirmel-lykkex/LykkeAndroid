package com.lykkex.LykkeWallet.rest.internal.callback;

import android.app.Activity;

import com.lykkex.LykkeWallet.gui.utils.validation.CallBackListener;
import com.lykkex.LykkeWallet.rest.base.models.BaseCallBack;
import com.lykkex.LykkeWallet.rest.internal.response.model.SettingData;
import com.lykkex.LykkeWallet.rest.internal.response.model.SettingSignOrderData;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by LIZA on 03.03.2016.
 */
public class SignSettingOrderCallBack extends BaseCallBack<SettingSignOrderData> {

    public SignSettingOrderCallBack(CallBackListener listener, Activity activity) {
        super(listener, activity);
    }

    @Override
    public void onResponse(Call<SettingSignOrderData> call, Response<SettingSignOrderData> response) {
        super.onResponse(call, response);
        if (response != null && response.body() != null && response.body().getError() == null) {
            listener.onSuccess(response.body().getResult());
        } else if (response != null && response.body() != null) {
            listener.onFail(response.body().getError());
        }
    }

    @Override
    public void onFailure(Call<SettingSignOrderData> call, Throwable t) {
        listener.onFail(null);
    }
}