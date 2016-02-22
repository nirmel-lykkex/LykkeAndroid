package com.lykkex.LykkeWallet.rest.pin.callback;

import android.app.Activity;

import com.lykkex.LykkeWallet.gui.utils.validation.CallBackListener;
import com.lykkex.LykkeWallet.rest.base.models.BaseCallBack;
import com.lykkex.LykkeWallet.rest.base.models.Error;
import com.lykkex.LykkeWallet.rest.pin.response.model.SecurityData;
import com.lykkex.LykkeWallet.rest.pin.response.model.SecurityResult;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by LIZA on 19.02.2016.
 */
public class CallBackPinSignIn extends BaseCallBack<SecurityData> {

    public CallBackPinSignIn(CallBackListener listener, Activity activity) {
        super(listener, activity);
    }

    @Override
    public void onResponse(Call<SecurityData> call, Response<SecurityData> response) {
        super.onResponse(call, response);
        if (!isCancel) {
            if (response != null && response.errorBody() == null) {
                listener.onSuccess(response);
            } else {
                listener.onFail(null);
            }
        }
    }

    @Override
    public void onFailure(Call<SecurityData> call, Throwable t) {
        if (!isCancel) {
            listener.onFail(null);
        }
    }
}
