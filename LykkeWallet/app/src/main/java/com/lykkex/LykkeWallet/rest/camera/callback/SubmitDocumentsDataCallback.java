package com.lykkex.LykkeWallet.rest.camera.callback;

import android.app.Activity;

import com.lykkex.LykkeWallet.gui.utils.validation.CallBackListener;
import com.lykkex.LykkeWallet.rest.base.models.BaseCallBack;
import com.lykkex.LykkeWallet.rest.camera.response.models.PersonData;
import com.lykkex.LykkeWallet.rest.login.response.model.PersonalData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by e.kazimirova on 10.02.2016.
 */
public class SubmitDocumentsDataCallback extends BaseCallBack<PersonalData> {

    public SubmitDocumentsDataCallback(CallBackListener listener, Activity activity) {
        super(listener, activity);
    }

    @Override
    public void onResponse(Call<PersonalData> call, Response<PersonalData> response) {
        super.onResponse(call, response);
        if (!isCancel) {
            if (response != null && response.body() != null) {
                listener.onSuccess(null);
            }else if (response != null){
                listener.onFail(null);
            }
        }
    }

    @Override
    public void onFailure(Call<PersonalData> call, Throwable t) {
        if (!isCancel) {
            listener.onFail(null);
        }
    }
}
