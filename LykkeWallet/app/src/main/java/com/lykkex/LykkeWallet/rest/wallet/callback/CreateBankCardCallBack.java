package com.lykkex.LykkeWallet.rest.wallet.callback;

import android.app.Activity;

import com.lykkex.LykkeWallet.gui.utils.validation.CallBackListener;
import com.lykkex.LykkeWallet.rest.base.models.BaseCallBack;
import com.lykkex.LykkeWallet.rest.wallet.response.models.BankCardsData;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by LIZA on 06.03.2016.
 */
public class CreateBankCardCallBack extends BaseCallBack<BankCardsData> {

    public CreateBankCardCallBack(CallBackListener listener, Activity activity) {
        super(listener, activity);
    }

    @Override
    public void onResponse(Call<BankCardsData> call, Response<BankCardsData> response) {
        if (response != null && response.errorBody() == null){
            listener.onSuccess(response.body());
        } else if (response != null && response.body() != null &&
                response.body().getError() != null) {
            setUpError(response.body().getError().getMessage());
        }
    }

    @Override
    public void onFailure(Call<BankCardsData> call, Throwable t) {
        listener.onFail(null);
    }
}