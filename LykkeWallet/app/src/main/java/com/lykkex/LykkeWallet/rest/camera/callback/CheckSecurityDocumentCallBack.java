package com.lykkex.LykkeWallet.rest.camera.callback;

import android.app.Activity;
import android.util.Log;

import com.lykkex.LykkeWallet.gui.utils.validation.CallBackListener;
import com.lykkex.LykkeWallet.rest.base.models.BaseCallBack;
import com.lykkex.LykkeWallet.rest.camera.response.models.CameraData;
import com.lykkex.LykkeWallet.rest.camera.response.models.DocumentAnswerData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by e.kazimirova on 14.02.2016.
 */
public class CheckSecurityDocumentCallBack extends BaseCallBack<DocumentAnswerData> {

    public CheckSecurityDocumentCallBack(CallBackListener listener, Activity activity) {
        super(listener, activity);
    }

    @Override
    public void onResponse(Call<DocumentAnswerData> call, Response<DocumentAnswerData> response) {
        super.onResponse(call, response);
        if (!isCancel) {
            if (response != null && response.body() != null && response.body().getError() == null) {
                listener.onSuccess(response.body().getResult());
            } else if (response != null && response.body() != null) {
                listener.onFail(response.body().getError());
            }
        }
    }

    @Override
    public void onFailure(Call<DocumentAnswerData> call, Throwable t) {
        if (!isCancel) {
            listener.onFail(null);
        }
    }
}