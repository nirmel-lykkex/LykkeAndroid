package com.lykkex.LykkeWallet.rest.wallet.callback;

import android.app.Activity;

import com.lykkex.LykkeWallet.gui.utils.validation.CallBackListener;
import com.lykkex.LykkeWallet.rest.base.models.BaseCallBack;
import com.lykkex.LykkeWallet.rest.wallet.response.models.LykkeWallerData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by LIZA on 01.03.2016.
 */
public class WalletCallback extends BaseCallBack<LykkeWallerData> {

    public WalletCallback(CallBackListener listener, Activity activity) {
        super(listener, activity);
    }

    @Override
    public void onResponse(Call<LykkeWallerData> call, Response<LykkeWallerData> response) {
        super.onResponse(call, response);
        if (response != null && response.errorBody() == null){
            listener.onSuccess(response.body());
        } else {
            listener.onFail(null);
        }
    }

    @Override
    public void onFailure(Call<LykkeWallerData> call, Throwable t) {
        listener.onFail(null);
    }
}
