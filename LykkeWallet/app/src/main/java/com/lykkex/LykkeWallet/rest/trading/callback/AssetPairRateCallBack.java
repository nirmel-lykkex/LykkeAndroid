package com.lykkex.LykkeWallet.rest.trading.callback;

import android.app.Activity;

import com.lykkex.LykkeWallet.gui.utils.validation.CallBackListener;
import com.lykkex.LykkeWallet.rest.base.models.BaseCallBack;
import com.lykkex.LykkeWallet.rest.trading.response.model.RateData;
import com.lykkex.LykkeWallet.rest.trading.response.model.RatesData;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by e.kazimirova on 16.03.2016.
 */
public class AssetPairRateCallBack extends BaseCallBack<RateData> {

    public AssetPairRateCallBack(CallBackListener listener, Activity activity) {
        super(listener, activity);
    }

    @Override
    public void onResponse(Call<RateData> call, Response<RateData> response) {
        super.onResponse(call, response);
           if (response != null && response.body() != null && response.body().getError() == null) {
                listener.onSuccess(response.body().getResult());
            } else if (response != null && response.body() != null &&
                   response.body().getError() != null) {
               setUpError(response.body().getError().getMessage());
            }
    }

    @Override
    public void onFailure(Call<RateData> call, Throwable t) {
        listener.onFail(null);
    }
}