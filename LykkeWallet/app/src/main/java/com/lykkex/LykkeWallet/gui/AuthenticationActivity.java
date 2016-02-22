package com.lykkex.LykkeWallet.gui;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.gui.BaseAuthenticationActivity;
import com.lykkex.LykkeWallet.gui.EnterPinActivity_;
import com.lykkex.LykkeWallet.gui.KysActivity_;
import com.lykkex.LykkeWallet.gui.LykkeApplication_;
import com.lykkex.LykkeWallet.gui.SelfieActivity_;
import com.lykkex.LykkeWallet.gui.SetUpPinActivity_;
import com.lykkex.LykkeWallet.gui.fragments.models.AuthModelGUI;
import com.lykkex.LykkeWallet.gui.fragments.models.KysStatusEnum;
import com.lykkex.LykkeWallet.gui.fragments.storage.UserPref_;
import com.lykkex.LykkeWallet.gui.utils.Constants;
import com.lykkex.LykkeWallet.gui.utils.validation.CallBackListener;
import com.lykkex.LykkeWallet.rest.base.models.Error;
import com.lykkex.LykkeWallet.rest.camera.callback.CheckDocumentCallBack;
import com.lykkex.LykkeWallet.rest.camera.response.models.CameraData;
import com.lykkex.LykkeWallet.rest.login.callback.LoginDataCallback;
import com.lykkex.LykkeWallet.rest.login.response.model.AuthModelData;
import com.lykkex.LykkeWallet.rest.login.response.model.AuthModelResult;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import retrofit2.Call;

/**
 * Created by LIZA on 22.02.2016.
 */
@EActivity(R.layout.restore_activity)
public class AuthenticationActivity extends BaseAuthenticationActivity implements CallBackListener{

    @ViewById  TextView textView;

    @AfterViews
    public void afterViews(){
        super.afterViews();
        LoginDataCallback callback = new LoginDataCallback(progressBar, this, this);
        AuthModelGUI authRequest = (AuthModelGUI) getIntent().getExtras().getSerializable(Constants.EXTRA_AUTH_REQUEST);
        Call<AuthModelData> call = LykkeApplication_.getInstance().getRestApi().getAuth(authRequest);
        call.enqueue(callback);
    }

    @Override
    public void onSuccess(Object result) {
        super.onSuccess(result);
        if (result != null && result instanceof AuthModelData) {
            AuthModelData res = (AuthModelData) result;
            switch (KysStatusEnum.valueOf(res.getResult().getKycStatus())){
                case NeedToFillData:
                    progressBar.setVisibility(View.VISIBLE);
                    textView.setText(R.string.check_documents);
                    CheckDocumentCallBack callback = new CheckDocumentCallBack(this, this);
                    Call<CameraData> call  = LykkeApplication_.getInstance().getRestApi().
                            checkDocuments(Constants.PART_AUTHORIZATION + userPref.authToken().get());
                    call.enqueue(callback);
                    break;
            }
        } else if (result != null && result instanceof CameraData) {
            Intent intent = new Intent();
            intent.putExtra(Constants.EXTRA_CAMERA_DATA, ((CameraData) result).getResult());
            intent.setClass(this, SelfieActivity_.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onFail(Error error) {
        super.onFail(error);
    }
}

