package com.lykkex.LykkeWallet.rest.base.models;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.lykkex.LykkeWallet.R;

import com.lykkex.LykkeWallet.gui.LykkeApplication_;
import com.lykkex.LykkeWallet.gui.fragments.storage.UserPref_;
import com.lykkex.LykkeWallet.gui.managers.SettingManager;
import com.lykkex.LykkeWallet.gui.utils.Constants;
import com.lykkex.LykkeWallet.gui.utils.LykkeUtils;
import com.lykkex.LykkeWallet.gui.utils.validation.CallBackListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by LIZA on 22.02.2016.
 */
public abstract class BaseCallBack<BaseModel> implements Callback<BaseModel> {

    protected boolean isCancel = false;
    protected CallBackListener listener;
    protected  Activity activity;
    protected UserPref_ userPref;

    public BaseCallBack(CallBackListener listener, Activity activity) {
        this.activity = activity;
        this.listener = listener;
        userPref = new UserPref_(LykkeApplication_.getInstance());
    }

    @Override
    public void onResponse(Call<BaseModel> call, Response<BaseModel> response) {
        if ((response.code() == Constants.ERROR_500 ||
                response.code() == Constants.ERROR_401) && activity != null) {
            Error error = new Error();
            error.setCode(Constants.ERROR_401);
            listener.onFail(error);

            setUpError(activity.getString(R.string.not_authorized));

            return;
        }
    }

    protected void setUpError(String error) {
        if (SettingManager.getInstance().isDebugMode()) {
            Toast.makeText(activity, error, Toast.LENGTH_LONG).show();
        } else {
            LykkeUtils.showError(activity.getFragmentManager(), error);
        }
    }


    public void cancel(){
        Log.e("qa ", "setup cancel");
        this.isCancel = true;
        Log.e("qa base ", String.valueOf(isCancel));
    }

    public boolean isCanceled(){
        return isCancel;
    }

}
