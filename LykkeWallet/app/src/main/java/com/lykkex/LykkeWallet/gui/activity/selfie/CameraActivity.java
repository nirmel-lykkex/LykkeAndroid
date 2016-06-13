package com.lykkex.LykkeWallet.gui.activity.selfie;

import android.os.Bundle;
import android.util.Log;

import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.gui.LykkeApplication;
import com.lykkex.LykkeWallet.gui.activity.BaseActivity;
import com.lykkex.LykkeWallet.gui.fragments.camerascreen.CameraIdCardFragment_;
import com.lykkex.LykkeWallet.gui.fragments.camerascreen.CameraProofOfAddressFragment_;
import com.lykkex.LykkeWallet.gui.fragments.camerascreen.CameraSelfieFragment_;
import com.lykkex.LykkeWallet.gui.fragments.camerascreen.SubmitFragment_;
import com.lykkex.LykkeWallet.gui.managers.UserManager;
import com.lykkex.LykkeWallet.gui.utils.Constants;
import com.lykkex.LykkeWallet.gui.utils.LykkeUtils;
import com.lykkex.LykkeWallet.rest.camera.response.models.CameraData;
import com.lykkex.LykkeWallet.rest.camera.response.models.CameraResult;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by e.kazimirova on 12.02.2016.
 */
@EActivity(R.layout.activity_main)
public class CameraActivity extends BaseActivity {

    @Bean
    UserManager userManager;

    @App
    LykkeApplication lykkeApplication;

    @AfterViews
    public void afterViews() {
        CameraResult cameraResult = (CameraResult) getIntent().getSerializableExtra(Constants.EXTRA_CAMERA_DATA);

        if(cameraResult == null) {
            Call<CameraData> callCamera = lykkeApplication.getRestApi().checkDocuments();
            callCamera.enqueue(new Callback<CameraData>() {
                @Override
                public void onResponse(Call<CameraData> call, Response<CameraData> response) {
                    if (!response.isSuccess() || response.body() == null) {
                        Log.e("ERROR", "Unexpected error while checking user documents, " + response.errorBody());

                        LykkeUtils.showError(getFragmentManager(), "Unexpected error while checking user documents.", new Runnable() {
                            @Override
                            public void run() {
                                finish();
                            }
                        });

                        return;
                    }

                    userManager.setCameraResult(response.body().getResult());

                    getIntent().putExtra(Constants.EXTRA_CAMERA_DATA, userManager.getCameraResult());

                    goToNextStep();
                }

                @Override
                public void onFailure(Call<CameraData> call, Throwable t) {
                    finish();
                }
            });
        } else {
            userManager.setCameraResult(cameraResult);

            goToNextStep();
        }
    }

    public void goToNextStep() {
        if(userManager.getCameraResult().isSelfie()) {
            initFragment(new CameraSelfieFragment_(), null, false);
        } else if(userManager.getCameraResult().isIdCard()) {
            initFragment(new CameraIdCardFragment_(), null, false);
        } else if(userManager.getCameraResult().isProofOfAddress()) {
            initFragment(new CameraProofOfAddressFragment_(), null, false);
        } else {
            Bundle bundle = new Bundle();
            bundle.putSerializable(Constants.EXTRA_CAMERA_MODEL_GUI, getIntent().getSerializableExtra(Constants.EXTRA_CAMERA_DATA));
            initFragment(new SubmitFragment_(), bundle);
        }
    }
}
