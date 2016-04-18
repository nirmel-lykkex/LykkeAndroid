package com.lykkex.LykkeWallet.rest.history.callback;

import android.app.Activity;
import android.widget.Toast;

import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.gui.utils.validation.CallBackListener;
import com.lykkex.LykkeWallet.rest.base.models.BaseCallBack;
import com.lykkex.LykkeWallet.rest.history.reposnse.model.HistoryData;
import com.lykkex.LykkeWallet.rest.history.reposnse.model.MarketData;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by e.kazimirova on 28.03.2016.
 */
public class MarketOrderCallBack extends BaseCallBack<MarketData> {

    public MarketOrderCallBack(CallBackListener listener, Activity activity) {
        super(listener, activity);
    }

    @Override
    public void onResponse(Call<MarketData> call, Response<MarketData> response) {
        if (response != null && response.body() != null && response.body().getError() == null) {
            listener.onSuccess(response.body().getResult());
        } else if (response != null && response.body() != null) {
            listener.onFail(response.body().getError());
        }
    }

    @Override
    public void onFailure(Call<MarketData> call, Throwable t) {
        Toast.makeText(activity, activity.getString(R.string.server_error), Toast.LENGTH_LONG);
        listener.onFail(null);
    }
}