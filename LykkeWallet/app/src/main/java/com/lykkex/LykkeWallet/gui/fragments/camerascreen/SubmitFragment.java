package com.lykkex.LykkeWallet.gui.fragments.camerascreen;

import android.content.Intent;
import android.widget.TextView;

import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.gui.LykkeApplication;
import com.lykkex.LykkeWallet.gui.LykkeApplication_;
import com.lykkex.LykkeWallet.gui.activity.KysActivity_;
import com.lykkex.LykkeWallet.gui.activity.selfie.CameraActivity;
import com.lykkex.LykkeWallet.gui.fragments.BaseFragment;
import com.lykkex.LykkeWallet.gui.fragments.models.CameraModelGUI;
import com.lykkex.LykkeWallet.gui.fragments.storage.UserPref_;
import com.lykkex.LykkeWallet.gui.utils.Constants;
import com.lykkex.LykkeWallet.gui.utils.validation.CallBackListener;
import com.lykkex.LykkeWallet.rest.camera.callback.SubmitDocumentsDataCallback;
import com.lykkex.LykkeWallet.rest.camera.response.models.CameraResult;
import com.lykkex.LykkeWallet.rest.login.response.model.PersonalData;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import retrofit2.Call;

/**
 * Created by LIZA on 23.02.2016.
 */
@EFragment(R.layout.documents_was_send)
public class SubmitFragment extends BaseFragment {

    @Pref
    UserPref_ userPref;

    @ViewById
    TextView textView3;

    @App
    LykkeApplication lykkeApplication;

    @AfterViews
    public void afterViews(){
        CameraResult model = (CameraResult) getArguments().getSerializable(Constants.EXTRA_CAMERA_MODEL_GUI);
        if (model.isProofOfAddress() || model.isIdCard() || model.isSelfie()) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        } else {
            actionBar.setDisplayHomeAsUpEnabled(false);
        }
        textView3.setText(String.format(getString(R.string.dear_it_checked),
                userPref.fullName().get()));
    }

    @Click(R.id.btnStart)
    public void clickBtnStart(){
        SubmitDocumentsDataCallback callback = new SubmitDocumentsDataCallback(new CallBackListener() {
            @Override
            public void onSuccess(Object result) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), KysActivity_.class);
                getActivity().startActivity(intent);
                getActivity().finish();
            }

            @Override
            public void onFail(Object error) {

            }
        }, getActivity());

        Call<PersonalData> call = lykkeApplication.getRestApi().kysDocuments();
        call.enqueue(callback);
    }
}
