package com.lykkex.LykkeWallet.rest.wallet.callback;

import android.app.Activity;

import com.lykkex.LykkeWallet.gui.utils.validation.CallBackListener;
import com.lykkex.LykkeWallet.rest.base.models.BaseCallBack;
import com.lykkex.LykkeWallet.rest.history.reposnse.model.MarketData;
import com.lykkex.LykkeWallet.rest.wallet.response.models.BankCardsData;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by e.kazimirova on 19.04.2016.
 */
public class CashOutCallBack  extends BaseCallBack<MarketData> {

    public CashOutCallBack(CallBackListener listener, Activity activity) {
        super(listener, activity);
    }

    @Override
    public void onResponse(Call<MarketData> call, Response<MarketData> response) {
        if (response != null && response.errorBody() == null){
            listener.onSuccess(response.body());
        }
    }

    @Override
    public void onFailure(Call<MarketData> call, Throwable t) {
        listener.onFail(null);
    }
}