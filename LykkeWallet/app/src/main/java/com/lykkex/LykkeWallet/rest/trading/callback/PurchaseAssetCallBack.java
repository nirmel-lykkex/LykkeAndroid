package com.lykkex.LykkeWallet.rest.trading.callback;

import android.app.Activity;

import com.lykkex.LykkeWallet.gui.utils.validation.CallBackListener;
import com.lykkex.LykkeWallet.rest.base.models.BaseCallBack;
import com.lykkex.LykkeWallet.rest.trading.response.model.AssetPairData;
import com.lykkex.LykkeWallet.rest.trading.response.model.OrderData;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by e.kazimirova on 16.03.2016.
 */
public class PurchaseAssetCallBack extends BaseCallBack<OrderData> {

    public PurchaseAssetCallBack(CallBackListener listener, Activity activity) {
        super(listener, activity);
    }

    @Override
    public void onResponse(Call<OrderData> call, Response<OrderData> response) {
        super.onResponse(call, response);
        if (response != null && response.body() != null && response.body().getError() == null) {
            listener.onSuccess(response.body().getResult());
        } else if (response != null && response.body() != null &&
                response.body().getError() != null) {
            setUpError(response.body().getError().getMessage());
        }
    }

    @Override
    public void onFailure(Call<OrderData> call, Throwable t) {
        listener.onFail(null);
    }
}