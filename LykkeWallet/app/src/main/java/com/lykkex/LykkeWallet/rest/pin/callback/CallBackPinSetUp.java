package com.lykkex.LykkeWallet.rest.pin.callback;

import android.app.Activity;

import com.lykkex.LykkeWallet.gui.utils.validation.CallBackListener;
import com.lykkex.LykkeWallet.rest.base.models.BaseCallBack;
import com.lykkex.LykkeWallet.rest.base.models.Error;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by LIZA on 19.02.2016.
 */
public class CallBackPinSetUp extends BaseCallBack<Error> {

    public CallBackPinSetUp(CallBackListener listener, Activity activity) {
        super(listener, activity);
    }

    @Override
    public void onResponse(Call<Error> call, Response<Error> response) {
        super.onResponse(call, response);
        if (!isCancel) {
            if (response != null && response.errorBody() == null) {
                listener.onSuccess(null);
            } else {
                listener.onFail(null);
            }
        }
    }

    @Override
    public void onFailure(Call<Error> call, Throwable t) {
        if (!isCancel) {
            listener.onFail(null);
        }
    }
}
