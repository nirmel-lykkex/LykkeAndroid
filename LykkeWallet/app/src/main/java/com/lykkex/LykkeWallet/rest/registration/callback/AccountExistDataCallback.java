package com.lykkex.LykkeWallet.rest.registration.callback;

import android.app.Activity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.lykkex.LykkeWallet.gui.LykkeApplication_;
import com.lykkex.LykkeWallet.gui.utils.validation.CallBackListener;
import com.lykkex.LykkeWallet.rest.base.models.BaseCallBack;
import com.lykkex.LykkeWallet.rest.registration.response.models.AccountExistData;
import com.lykkex.LykkeWallet.rest.registration.response.models.AcountExistResult;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by e.kazimirova on 10.02.2016.
 */
public class AccountExistDataCallback extends BaseCallBack<AccountExistData> {

    private ProgressBar progressBar;
    private String email;

    public AccountExistDataCallback(CallBackListener listener, ProgressBar progressBar, Activity activity) {
        super(listener, activity);
        this.progressBar = progressBar;
    }

    @Override
    public void onResponse(Call<AccountExistData> call, Response<AccountExistData> response) {
        super.onResponse(call, response);
        progressBar.setVisibility(View.GONE);
        if (!isCancel) {
            if (response != null && response.body() != null && response.body().getError() == null) {
                AcountExistResult data = response.body().getResult();
                data.setEmail(email);
                listener.onSuccess(data);
            } else if (response != null && response.body() != null &&
                    response.body().getError() != null) {
                setUpError(response.body().getError().getMessage());
            }
        }
    }

    @Override
    public void onFailure(Call<AccountExistData> call, Throwable t) {
        progressBar.setVisibility(View.GONE);
        if (!isCancel) {
            listener.onFail(null);
            Toast.makeText(LykkeApplication_.getInstance(), "Something going wrong. Try again", Toast.LENGTH_LONG).show();
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
