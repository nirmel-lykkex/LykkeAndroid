package com.lykkex.LykkeWallet.rest.camera.callback;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.lykkex.LykkeWallet.gui.utils.validation.CallBackListener;
import com.lykkex.LykkeWallet.rest.base.models.BaseCallBack;
import com.lykkex.LykkeWallet.rest.camera.response.models.PersonData;
import com.lykkex.LykkeWallet.rest.registration.response.models.RegistrationData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by e.kazimirova on 10.02.2016.
 */
public class SendDocumentsDataCallback extends BaseCallBack<PersonData> {

    public SendDocumentsDataCallback(CallBackListener listener, Activity activity) {
        super(listener, activity);
    }

    @Override
    public void onResponse(Call<PersonData> call, Response<PersonData> response) {
        super.onResponse(call, response);
        if (!isCancel) {
            Log.e("qa ", "inside success");
            if (response != null && response.body() != null && response.body().getError() == null) {
                listener.onSuccess(response.body());
            } else if (response != null && response.body() != null &&
                    response.body().getError() != null) {
                setUpError(response.body().getError().getMessage());
            }
        }
    }

    @Override
    public void cancel(){
       super.cancel();
        Log.e("qa send", String.valueOf(isCancel));
    }

    @Override
    public void onFailure(Call<PersonData> call, Throwable t) {
        if (!isCancel) {
            listener.onFail(null);
        }
    }
}
