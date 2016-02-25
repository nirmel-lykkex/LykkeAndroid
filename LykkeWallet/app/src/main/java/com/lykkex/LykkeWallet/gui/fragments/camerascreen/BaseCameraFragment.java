package com.lykkex.LykkeWallet.gui.fragments.camerascreen;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.commonsware.cwac.camera.CameraHost;
import com.commonsware.cwac.camera.CameraHostProvider;
import com.commonsware.cwac.camera.CameraView;
import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.gui.KysActivity_;
import com.lykkex.LykkeWallet.gui.LykkeApplication_;
import com.lykkex.LykkeWallet.gui.activity.selfie.CameraActivity;
import com.lykkex.LykkeWallet.gui.activity.selfie.CameraHostSelfie;
import com.lykkex.LykkeWallet.gui.fragments.BaseFragment;
import com.lykkex.LykkeWallet.gui.fragments.controllers.CameraController;
import com.lykkex.LykkeWallet.gui.fragments.controllers.CameraController_;
import com.lykkex.LykkeWallet.gui.fragments.models.CameraModelGUI;
import com.lykkex.LykkeWallet.gui.fragments.statesegments.states.CameraState;
import com.lykkex.LykkeWallet.gui.fragments.statesegments.triggers.CameraTrigger;
import com.lykkex.LykkeWallet.gui.fragments.storage.UserPref_;
import com.lykkex.LykkeWallet.gui.utils.Constants;
import com.lykkex.LykkeWallet.gui.widgets.DialogProgress;
import com.lykkex.LykkeWallet.rest.base.models.Error;
import com.lykkex.LykkeWallet.rest.camera.callback.CheckDocumentCallBack;
import com.lykkex.LykkeWallet.rest.camera.callback.SendDocumentsDataCallback;
import com.lykkex.LykkeWallet.rest.camera.request.models.CameraModel;
import com.lykkex.LykkeWallet.rest.camera.request.models.CameraType;
import com.lykkex.LykkeWallet.rest.camera.response.models.CameraData;
import com.lykkex.LykkeWallet.rest.camera.response.models.CameraResult;
import com.lykkex.LykkeWallet.rest.camera.response.models.PersonData;
import com.lykkex.LykkeWallet.rest.login.response.model.PersonalData;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Random;

import retrofit2.Call;

/**
 * Created by LIZA on 23.02.2016.
 */
@EFragment(R.layout.selfie_activity_time)
public abstract class BaseCameraFragment extends BaseFragment<CameraState> implements
        CameraHostProvider {

    protected @ViewById CameraView cameraView;
    protected @ViewById ImageButton ivTakenPhoto;
    protected @ViewById TextView tvTitle;
    protected @ViewById Button buttonFile;
    protected @ViewById Button buttonOpenSelfie;
    protected @ViewById Button retake;
    protected @ViewById Button submit;
    protected @ViewById Button buttake_photo;

    protected @Pref UserPref_ userPref;

    protected CameraController controller;
    protected CameraModelGUI model;
    protected File photoPath;
    private ProgressDialog dialog;

    public DialogProgress progressDialog = new DialogProgress();

    @AfterViews
    public void afterViews(){
        actionBar.setTitle(R.string.registration);
        controller = ((CameraActivity)getActivity()).getController();
        model = ((CameraActivity)getActivity()).getModel();
        dialog = new ProgressDialog(getContext());
        dialog.setMessage(getString(R.string.waiting));

        if (controller == null) {
            controller = CameraController_.getInstance_(getActivity());
            controller.init(this, CameraState.Idle);
            if (getActivity().getIntent() != null && getActivity().getIntent().getExtras() != null
                    && getActivity().getIntent().getExtras().getSerializable(Constants.EXTRA_CAMERA_DATA) != null) {
                onSuccess(getActivity().getIntent().getExtras().getSerializable(Constants.EXTRA_CAMERA_DATA));
            } else {
                CheckDocumentCallBack callback = new CheckDocumentCallBack(this, getActivity());
                Call<CameraData> call = LykkeApplication_.getInstance().getRestApi().
                        checkDocuments(Constants.PART_AUTHORIZATION + userPref.authToken().get());
                call.enqueue(callback);
            }
        }
        switch (controller.getCurrentState()){
            case Selfie:
                if (!model.getPathSelfie().isEmpty()){
                    setUpViewsReadyPhoto();
                } else {
                    setUpViewsMakingPhoto();
                }
                break;
            case IdCard:
                if (!model.getPathIdCard().isEmpty()){
                    setUpViewsReadyPhoto();
                } else {
                    setUpViewsMakingPhoto();
                }
                break;
            case ProofOfAddress:
                if (!model.getPathProofAddress().isEmpty()){
                    setUpViewsReadyPhoto();
                } else {
                    setUpViewsMakingPhoto();
                }
                break;
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        try {
            if (cameraView != null) {
                cameraView.onResume();
            }
        } catch (IllegalStateException ex){}
    }

    @Override
    public void onPause(){
        super.onPause();
        try {
            if (cameraView != null) {
                cameraView.onPause();
            }
        } catch (IllegalStateException ex){}
    }

    public void showTakenPicture(Bitmap bitmap) {
        cameraView.setVisibility(View.GONE);
        if (this instanceof CameraSelfieFragment_) {
            Matrix m = new Matrix();
            m.preScale(-1, 1);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m, false);
            bitmap.setDensity(DisplayMetrics.DENSITY_DEFAULT);
        }
        ivTakenPhoto.setImageBitmap(bitmap);
        ivTakenPhoto.setVisibility(View.VISIBLE);
        buttake_photo.setVisibility(View.GONE);
        retake.setVisibility(View.VISIBLE);
        submit.setVisibility(View.VISIBLE);
        buttonFile.setVisibility(View.GONE);
        buttonOpenSelfie.setVisibility(View.GONE);
    }

    private void setUpViewsMakingPhoto(){
        switch (controller.getCurrentState()){
            case Selfie:
                actionBar.setDisplayHomeAsUpEnabled(false);
                cameraView.setVisibility(View.VISIBLE);
                cameraView.onPause();
                cameraView.onResume();
                ivTakenPhoto.setVisibility(View.GONE);
                buttake_photo.setVisibility(View.VISIBLE);
                retake.setVisibility(View.GONE);
                submit.setVisibility(View.GONE);
                buttonFile.setVisibility(View.GONE);
                buttonOpenSelfie.setVisibility(View.GONE);
                tvTitle.setText(R.string.make_selfie);
                break;
            case IdCard:
                if (model.isSelfie()) {
                    actionBar.setDisplayHomeAsUpEnabled(true);
                } else {
                    actionBar.setDisplayHomeAsUpEnabled(false);
                }
                cameraView.setVisibility(View.VISIBLE);
                cameraView.onPause();
                cameraView.onResume();
                ivTakenPhoto.setVisibility(View.GONE);
                buttake_photo.setVisibility(View.VISIBLE);
                retake.setVisibility(View.GONE);
                submit.setVisibility(View.GONE);
                buttonFile.setVisibility(View.VISIBLE);
                buttonOpenSelfie.setVisibility(View.VISIBLE);
                tvTitle.setText(R.string.id_card);
                break;
            case ProofOfAddress:
                if (model.isIdCard()) {
                    actionBar.setDisplayHomeAsUpEnabled(true);
                } else {
                    actionBar.setDisplayHomeAsUpEnabled(false);
                }
                cameraView.setVisibility(View.VISIBLE);
                ivTakenPhoto.setVisibility(View.GONE);
                cameraView.onPause();
                cameraView.onResume();
                buttake_photo.setVisibility(View.VISIBLE);
                retake.setVisibility(View.GONE);
                submit.setVisibility(View.GONE);
                buttonFile.setVisibility(View.VISIBLE);
                buttonOpenSelfie.setVisibility(View.VISIBLE);
                tvTitle.setText(R.string.proof_adress);
                break;
        }
    }

    private void setUpViewsReadyPhoto(){
        switch (controller.getCurrentState()){
            case Selfie:
                actionBar.setDisplayHomeAsUpEnabled(false);
                cameraView.setVisibility(View.GONE);
                ivTakenPhoto.setVisibility(View.VISIBLE);
                ivTakenPhoto.setImageBitmap(BitmapFactory.decodeFile(model.getPathSelfie()));
                buttake_photo.setVisibility(View.GONE);
                retake.setVisibility(View.VISIBLE);
                submit.setVisibility(View.VISIBLE);
                buttonFile.setVisibility(View.GONE);
                buttonOpenSelfie.setVisibility(View.GONE);
                tvTitle.setText(R.string.make_selfie);
                break;
            case IdCard:
                actionBar.setDisplayHomeAsUpEnabled(true);
                cameraView.setVisibility(View.GONE);
                ivTakenPhoto.setVisibility(View.VISIBLE);
                ivTakenPhoto.setImageBitmap(BitmapFactory.decodeFile(model.getPathIdCard()));
                buttake_photo.setVisibility(View.GONE);
                retake.setVisibility(View.VISIBLE);
                submit.setVisibility(View.VISIBLE);
                buttonFile.setVisibility(View.GONE);
                buttonOpenSelfie.setVisibility(View.GONE);
                tvTitle.setText(R.string.id_card);
                break;
            case ProofOfAddress:
                actionBar.setDisplayHomeAsUpEnabled(true);
                cameraView.setVisibility(View.GONE);
                ivTakenPhoto.setVisibility(View.VISIBLE);
                ivTakenPhoto.setImageBitmap(BitmapFactory.decodeFile(model.getPathIdCard()));
                buttake_photo.setVisibility(View.GONE);
                retake.setVisibility(View.VISIBLE);
                submit.setVisibility(View.VISIBLE);
                buttonFile.setVisibility(View.GONE);
                buttonOpenSelfie.setVisibility(View.GONE);
                tvTitle.setText(R.string.proof_adress);
                break;
        }
    }

    public void setUpPhotoPath(File path){
        photoPath = path;
        model.setIsDone(true);
        switch (controller.getCurrentState()){
            case Selfie:
                model.setPathSeldie(path.getAbsolutePath());
                break;
            case ProofOfAddress:
                model.setPathProofAddress(path.getAbsolutePath());
                break;
            case IdCard:
                model.setPathIdCard(path.getAbsolutePath());
                break;
        }
    }

    @Click(R.id.buttake_photo)
    public void clickTakePhoto(){
        try {
            cameraView.takePicture(true, true);
        } catch (IllegalStateException e){
            cameraView.onPause();
            cameraView.onResume();
        }
    }

    @Click(R.id.retake)
    public void clickRetakePhoto(){
        setUpViewsMakingPhoto();
    }

    @Click(R.id.buttonOpenSelfie)
    public void changeViewCamera(){
        if (this instanceof CameraBackFragment) {
            ((CameraActivity)getActivity()).initFragment(new CameraSelfieFragment_(),
                    null, controller, model);
        } else {
            ((CameraActivity)getActivity()).initFragment(new CameraBackFragment_(),
                    null, controller, model);
        }
    }

    @Click(R.id.submit)
    public void clickSubmit(){
        if (model.isDone()) {
            new Handler().
                    postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            switch (controller.getCurrentState()){
                                case Selfie:
                                    if (!model.isSelfieSend()) {
                                        sendImage(model.getPathSelfie(), CameraType.Selfie);
                                    }else {
                                        controller.fire(CameraTrigger.IdCard);
                                        ((CameraActivity)getActivity()).initFragment(new CameraBackFragment_(),
                                                null, controller, model);
                                    }
                                    break;
                                case IdCard:
                                    if (!model.isCardIdeSend()) {
                                        sendImage(model.getPathIdCard(), CameraType.IdCard);
                                    } else {
                                        controller.fire(CameraTrigger.ProofOfAddress);
                                        ((CameraActivity)getActivity()).initFragment(new CameraBackFragment_(),
                                                null, controller, model);
                                    }
                                    break;
                                case ProofOfAddress:
                                    if (!model.isProofAddressSend()) {
                                        sendImage(model.getPathProofAddress(), CameraType.ProofOfAddress);
                                    } else {
                                        Bundle bundle = new Bundle();
                                        bundle.putSerializable(Constants.EXTRA_CAMERA_MODEL_GUI, model);
                                        ((CameraActivity)getActivity()).initFragment(new SubmitFragment_(),
                                                bundle, controller, model);
                                    }
                                    break;
                            }

                        }
                    }, Constants.DELAY_500);
        }
    }

    public void showProgress(Call call, SendDocumentsDataCallback callback){
        progressDialog.setUpCurrentCall(call,
                callback);
        progressDialog.show(getActivity().getFragmentManager(),
                "dlg1" +new Random((int)Constants.DELAY_5000));
    }

    //TODO
    // in utils

    private void sendImage(String path, CameraType type){
        CameraModel model = new CameraModel();
        model.setExt(Constants.JPG);
        model.setData(compressImage(path));
        model.setType(type.toString());
        SendDocumentsDataCallback callBackSendDocuments = new SendDocumentsDataCallback(this,
                getActivity());
        Call<PersonData> call  = LykkeApplication_.getInstance().getRestApi().
                kysDocuments(Constants.PART_AUTHORIZATION + userPref.authToken().get(), model);
        call.enqueue(callBackSendDocuments);
        showProgress(call, callBackSendDocuments);
    }

    private String compressImage(String path){
        Bitmap bm = BitmapFactory.decodeFile(path);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 40, baos);
        byte[] byteArrayImage = baos.toByteArray();
        return Base64.encodeToString(byteArrayImage, Base64.DEFAULT);
    }


    @Override
    public void onSuccess(Object result) {
        switch (controller.getCurrentState()) {
            case Idle:
                dialog.dismiss();
                if (result != null) {
                    CameraResult data = (CameraResult) result;
                    if (data != null) {
                        model.setIdCard(data.isIdCard());
                        model.setProofOfAddress(data.isProofOfAddress());
                        model.setSelfie(data.isSelfie());
                        if (data.isSelfie()) {
                            controller.fire(CameraTrigger.Selfie);
                            model.setIsDone(false);
                            ((CameraActivity)getActivity()).initFragment(new CameraSelfieFragment_(),
                                    null, controller, model);
                        } else if (data.isIdCard()) {
                            controller.fire(CameraTrigger.IdCard);
                            model.setIsDone(false);
                            ((CameraActivity)getActivity()).initFragment(new CameraBackFragment_(),
                                    null, controller, model);
                        } else if (data.isProofOfAddress()) {
                            controller.fire(CameraTrigger.ProofOfAddress);
                            model.setIsDone(false);
                            ((CameraActivity)getActivity()).initFragment(new CameraBackFragment_(),
                                    null, controller, model);
                        }

                    }
                }
                break;
            case Selfie:
                if (!model.isSelfieSend()) {
                    progressDialog.dismiss();
                    model.setIsSelfieSend(true);
                }

                if (model.isIdCard()) {
                    controller.fire(CameraTrigger.IdCard);
                    ((CameraActivity)getActivity()).initFragment(new CameraBackFragment_(),
                            null, controller, model);
                } else if (model.isProofOfAddress()) {
                    controller.fire(CameraTrigger.ProofOfAddress);
                    ((CameraActivity)getActivity()).initFragment(new CameraBackFragment_(),
                            null, controller, model);
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(Constants.EXTRA_CAMERA_MODEL_GUI, model);
                    ((CameraActivity)getActivity()).initFragment(new SubmitFragment_(),
                            bundle, controller, model);
                }
                break;
            case IdCard:
                if (!model.isCardIdeSend()) {
                    progressDialog.dismiss();
                    model.setIsCardIdeSend(true);
                }
                if (model.isProofOfAddress()) {
                    controller.fire(CameraTrigger.ProofOfAddress);
                    ((CameraActivity)getActivity()).initFragment(new CameraBackFragment_(),
                            null, controller, model);
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(Constants.EXTRA_CAMERA_MODEL_GUI, model);
                    ((CameraActivity)getActivity()).initFragment(new SubmitFragment_(),
                            bundle, controller, model);
                }
                break;
            case ProofOfAddress:
                if (!model.isProofAddressSend()) {
                    progressDialog.dismiss();
                    model.setIsProofAddressSend(true);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(Constants.EXTRA_CAMERA_MODEL_GUI, model);
                    ((CameraActivity)getActivity()).initFragment(new SubmitFragment_(),
                            bundle, controller, model);
                }
                break;


        }
    }

    @Override
    public void initOnBackPressed() {
        switch (controller.getCurrentState()) {
            case Idle:
                getActivity().finish();
                break;
            case Selfie:
                getActivity().finish();
                break;
            case IdCard:
                if (model.isSelfie()) {
                    controller.fire(CameraTrigger.Selfie);
                    ((CameraActivity)getActivity()).initFragment(new CameraSelfieFragment_(),
                            null, controller, model);
                } else {
                    getActivity().finish();
                }

                break;
            case ProofOfAddress:
                if (model.isIdCard()) {
                    controller.fire(CameraTrigger.IdCard);
                    ((CameraActivity)getActivity()).initFragment(new CameraBackFragment_(),
                            null, controller, model);
                } else if (model.isSelfie()) {
                    controller.fire(CameraTrigger.Selfie);
                    ((CameraActivity)getActivity()).initFragment(new CameraSelfieFragment_(),
                            null, controller, model);
                } else {
                    getActivity().finish();
                }
                break;
        }
    }


    @Override
    public void onFail(Error error) {
        dialog.dismiss();
        progressDialog.dismiss();
    }

    @Override
    public void onConsume(CameraState cameraState) {

    }


}
