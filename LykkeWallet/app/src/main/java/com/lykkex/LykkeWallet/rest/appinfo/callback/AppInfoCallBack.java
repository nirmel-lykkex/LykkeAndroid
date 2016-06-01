package com.lykkex.LykkeWallet.rest.appinfo.callback;

import android.app.Activity;

import com.lykkex.LykkeWallet.gui.utils.validation.CallBackListener;
import com.lykkex.LykkeWallet.rest.appinfo.response.model.AppInfoData;
import com.lykkex.LykkeWallet.rest.base.models.BaseCallBack;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Murtic on 30/05/16.
 */
public class AppInfoCallBack extends BaseCallBack<AppInfoData> {

    public AppInfoCallBack(CallBackListener listener, Activity activity) {
        super(listener, activity);
    }

    @Override
    public void onResponse(Call<AppInfoData> call, Response<AppInfoData> response) {
        if (response != null && response.errorBody() == null) {
            listener.onSuccess(response.body());
        } else if (response != null && response.body() != null &&
                response.body().getError() != null) {
            setUpError(response.body().getError().getMessage());
        }
    }

    @Override
    public void onFailure(Call<AppInfoData> call, Throwable t) {
        listener.onFail(null);
    }
}
