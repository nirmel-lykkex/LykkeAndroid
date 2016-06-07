package com.lykkex.LykkeWallet.gui.fragments.camerascreen;

import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraDevice;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.ListViewCompat;
import android.util.Base64;
import android.view.Surface;
import android.view.SurfaceView;
import android.view.TextureView;
import android.widget.Button;
import android.widget.ImageView;

import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.gui.LykkeApplication;
import com.lykkex.LykkeWallet.gui.activity.BaseActivity;
import com.lykkex.LykkeWallet.gui.adapters.CountryPhoneCodesAdapter;
import com.lykkex.LykkeWallet.gui.customviews.StepsIndicator;
import com.lykkex.LykkeWallet.gui.fragments.models.CameraModelGUI;
import com.lykkex.LykkeWallet.gui.managers.UserManager;
import com.lykkex.LykkeWallet.gui.utils.Constants;
import com.lykkex.LykkeWallet.gui.utils.LykkeUtils;
import com.lykkex.LykkeWallet.gui.utils.validation.CallBackListener;
import com.lykkex.LykkeWallet.gui.widgets.DialogProgress;
import com.lykkex.LykkeWallet.rest.camera.callback.SendDocumentsDataCallback;
import com.lykkex.LykkeWallet.rest.camera.request.models.CameraModel;
import com.lykkex.LykkeWallet.rest.camera.request.models.CameraType;
import com.lykkex.LykkeWallet.rest.camera.response.models.PersonData;
import com.lykkex.LykkeWallet.rest.login.response.model.PersonalData;
import com.lykkex.LykkeWallet.rest.registration.response.models.CountryPhoneCodeData;
import com.lykkex.LykkeWallet.rest.registration.response.models.CountryPhoneCodesResult;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.io.ByteArrayOutputStream;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Murtic on 31/05/16.
 */
@EFragment(R.layout.camera_selfie_fragment)
public class CameraSelfieFragment extends CameraBaseFragment {

    @AfterViews
    void afterViews() {
        super.afterViews();

        userManager.getCameraModel().setType(CameraType.Selfie.toString());
    }

    protected void sendImage(){
        Call<PersonData> call  = lykkeApplication.getRestApi().kysDocuments(userManager.getCameraModel());

        SendDocumentsDataCallback callback = new SendDocumentsDataCallback(new CallBackListener<PersonData>() {
            @Override
            public void onSuccess(PersonData result) {
                progressDialog.dismiss();
                userManager.getCameraResult().setSelfie(false);

                ((BaseActivity)getActivity()).initFragment(new CameraIdCardFragment_(), null, false);
            }

            @Override
            public void onFail(Object error) {
                dialog.dismiss();
                progressDialog.dismiss();
            }
        }, getActivity());

        call.enqueue(callback);

        showProgress(call, callback);
    }
}
