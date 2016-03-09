package com.lykkex.LykkeWallet.gui.activity.authentication;

import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.widget.TextView;

import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.gui.LykkeApplication_;
import com.lykkex.LykkeWallet.gui.activity.pin.EnterPinActivity_;
import com.lykkex.LykkeWallet.gui.activity.pin.SetUpPinActivity_;
import com.lykkex.LykkeWallet.gui.activity.selfie.CameraActivity_;
import com.lykkex.LykkeWallet.gui.fragments.models.AuthModelGUI;
import com.lykkex.LykkeWallet.gui.fragments.models.KysStatusEnum;
import com.lykkex.LykkeWallet.gui.utils.Constants;
import com.lykkex.LykkeWallet.gui.utils.validation.CallBackListener;
import com.lykkex.LykkeWallet.rest.base.models.Error;
import com.lykkex.LykkeWallet.rest.camera.callback.CheckDocumentCallBack;
import com.lykkex.LykkeWallet.rest.camera.response.models.CameraData;
import com.lykkex.LykkeWallet.rest.camera.response.models.CameraResult;
import com.lykkex.LykkeWallet.rest.login.callback.LoginDataCallback;
import com.lykkex.LykkeWallet.rest.login.response.model.AuthModelData;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import retrofit2.Call;

/**
 * Created by LIZA on 22.02.2016.
 */
@EActivity(R.layout.restore_activity)
public class AuthenticationActivity extends BaseAuthenticationActivity implements CallBackListener{

    @ViewById  TextView textView;


    public void onStart(){
        super.onStart();
        startRequest();
    }

    protected void startRequest(){
        LoginDataCallback callback = new LoginDataCallback(progressBar, this, this);
        AuthModelGUI authRequest = (AuthModelGUI) getIntent().getExtras().getSerializable(Constants.EXTRA_AUTH_REQUEST);
        authRequest.setClientInfo("<android>; Model:<" + Build.MODEL +">; Os:<android>; Screen:<"+
                getWindowManager().getDefaultDisplay().getWidth()+"x" +
                getWindowManager().getDefaultDisplay().getHeight()+">;");
        Call<AuthModelData> call = LykkeApplication_.getInstance().getRestApi().getAuth(authRequest);
        call.enqueue(callback);
    }

    @Override
    public void onSuccess(Object result) {
        super.onSuccess(result);
        if (result != null && result instanceof AuthModelData) {
            AuthModelData res = (AuthModelData) result;
            switch (KysStatusEnum.valueOf(res.getResult().getKycStatus())){
                case Ok:
                    if (res.getResult().getPinIsEntered()) {
                        Intent intent = new Intent();
                        intent.setClass(this, EnterPinActivity_.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Intent intent = new Intent();
                        intent.setClass(this, SetUpPinActivity_.class);
                        startActivity(intent);
                        finish();
                    }
                    break;
                case NeedToFillData:
                    progressBar.setVisibility(View.VISIBLE);
                    textView.setText(R.string.check_documents);
                    CheckDocumentCallBack callback = new CheckDocumentCallBack(this, this);
                    Call<CameraData> call  = LykkeApplication_.getInstance().getRestApi().
                            checkDocuments(Constants.PART_AUTHORIZATION + userPref.authToken().get());
                    call.enqueue(callback);
                    break;
            }
        } else if (result != null && result instanceof CameraResult) {
            Intent intent = new Intent();
            intent.putExtra(Constants.EXTRA_CAMERA_DATA, ((CameraResult) result));
            intent.setClass(this,  CameraActivity_.class);
            startActivity(intent);
            finish();
        }
    }


}

