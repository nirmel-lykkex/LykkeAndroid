package com.lykkex.LykkeWallet.rest.wallet.callback;

import android.app.Activity;

import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.gui.utils.Constants;
import com.lykkex.LykkeWallet.gui.utils.validation.CallBackListener;
import com.lykkex.LykkeWallet.rest.base.models.BaseCallBack;
import com.lykkex.LykkeWallet.rest.base.models.Error;
import com.lykkex.LykkeWallet.rest.wallet.response.models.LykkeWallerData;

import retrofit2.Call;
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
        if ((response.code() == Constants.ERROR_500 ||
                response.code() == Constants.ERROR_401) && activity != null) {
            Error error = new Error();
            error.setCode(Constants.ERROR_401);
            listener.onFail(error);
            userPref.clear();
            userPref.isDepositVisible().put(false);
            setUpError(activity.getString(R.string.not_authorized));
            return;
        }
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
