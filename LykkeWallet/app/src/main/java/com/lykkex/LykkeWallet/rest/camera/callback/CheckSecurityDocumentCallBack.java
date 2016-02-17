package com.lykkex.LykkeWallet.rest.camera.callback;

import android.util.Log;

import com.lykkex.LykkeWallet.gui.utils.validation.CallBackListener;
import com.lykkex.LykkeWallet.rest.camera.response.models.CameraData;
import com.lykkex.LykkeWallet.rest.camera.response.models.DocumentAnswerData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by e.kazimirova on 14.02.2016.
 */
public class CheckSecurityDocumentCallBack implements Callback<DocumentAnswerData> {

    private CallBackListener listener;

    public CheckSecurityDocumentCallBack(CallBackListener listener) {
        this.listener = listener;
    }

    @Override
    public void onResponse(Call<DocumentAnswerData> call, Response<DocumentAnswerData> response) {
        if (response != null && response.body() != null && response.body().getError()==null) {
            Log.e("Liza ", "onSucess ");
            listener.onSuccess(response.body().getResult());
        }else if (response != null && response.body() != null){
            Log.e("Liza ", "onFail ");
            listener.onFail(response.body().getError());
        }
    }

    @Override
    public void onFailure(Call<DocumentAnswerData> call, Throwable t) {
        Log.e("Liza ", "onFail ");
        listener.onFail(null);
    }
}