package com.lykkex.LykkeWallet.rest.history.callback;

import android.app.Activity;

import com.lykkex.LykkeWallet.gui.utils.validation.CallBackListener;
import com.lykkex.LykkeWallet.rest.base.models.BaseCallBack;
import com.lykkex.LykkeWallet.rest.history.reposnse.model.HistoryData;
import com.lykkex.LykkeWallet.rest.trading.response.model.RateData;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by e.kazimirova on 28.03.2016.
 */
public class HistoryCallBack extends BaseCallBack<HistoryData> {

    public HistoryCallBack(CallBackListener listener, Activity activity) {
        super(listener, activity);
    }

    @Override
    public void onResponse(Call<HistoryData> call, Response<HistoryData> response) {
        super.onResponse(call, response);
        if (response != null && response.body() != null && response.body().getError() == null) {
            listener.onSuccess(response.body().getResult());
        } else if (response != null && response.body() != null &&
                response.body().getError() != null) {
            setUpError(response.body().getError().getMessage());
        }
    }

    @Override
    public void onFailure(Call<HistoryData> call, Throwable t) {
        listener.onFail(null);
    }
}