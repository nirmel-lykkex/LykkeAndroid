package com.lykkex.LykkeWallet.gui.fragments.guisegments;


import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.UiThread;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.gui.KysActivity_;
import com.lykkex.LykkeWallet.gui.LykkeApplication_;
import com.lykkex.LykkeWallet.gui.SelfieActivity;
import com.lykkex.LykkeWallet.gui.fragments.CameraPreview;
import com.lykkex.LykkeWallet.gui.fragments.controllers.CameraController;
import com.lykkex.LykkeWallet.gui.fragments.models.CameraModelGUI;
import com.lykkex.LykkeWallet.gui.fragments.statesegments.states.CameraState;
import com.lykkex.LykkeWallet.gui.fragments.statesegments.triggers.CameraTrigger;
import com.lykkex.LykkeWallet.gui.utils.Constants;
import com.lykkex.LykkeWallet.gui.utils.validation.CallBackListener;
import com.lykkex.LykkeWallet.rest.camera.callback.CheckDocumentCallBack;
import com.lykkex.LykkeWallet.rest.camera.callback.SendDocumentsDataCallback;
import com.lykkex.LykkeWallet.rest.camera.request.models.CameraModel;
import com.lykkex.LykkeWallet.rest.camera.request.models.CameraType;
import com.lykkex.LykkeWallet.rest.camera.response.models.CameraData;
import com.lykkex.LykkeWallet.rest.camera.response.models.CameraResult;
import com.lykkex.LykkeWallet.rest.camera.response.models.PersonData;
import com.lykkex.LykkeWallet.rest.login.response.model.PersonalData;
import com.lykkex.LykkeWallet.rest.registration.callback.RegistrationDataCallback;
import com.lykkex.LykkeWallet.rest.registration.response.models.AccountExistData;
import com.lykkex.LykkeWallet.rest.registration.response.models.RegistrationResult;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.io.ByteArrayOutputStream;

import retrofit2.Call;


/**
 * Created by e.kazimirova on 11.02.2016.
 */
@EBean
public class CameraGuiSegment implements CallBackListener {

    private CameraController controller;
    private CameraModelGUI model;
    private SelfieActivity activity;

    private FrameLayout camera_preview;
    private Button submit;
    private Button buttake_photo;
    private Button buttonFile;
    private Button buttonOpenSelfie;
    private Button retake;
    private ImageView imgPreview;
    private RegistrationResult info;
    private ImageView imgSecond;
    private ImageView imgThird;
    private ImageView imgForth;
    private TextView tvTitle;


    public void init(SelfieActivity activity, CameraController controller,
                    FrameLayout camera_preview,
                            Button submit,
                            Button buttake_photo,
                            Button buttonFile,
                            Button buttonOpenSelfie,
                             Button retake,
                             ImageView imgPreview, RegistrationResult info,
                     ImageView imgSecond, ImageView imgThird, ImageView imgForth,
                     TextView tvTitle
    ) {
        model = new CameraModelGUI();
        this.info = info;
        this.activity = activity;
        this.camera_preview = camera_preview;
        this.submit = submit;
        this.buttake_photo = buttake_photo;
        this.buttonFile = buttonFile;
        this.buttonOpenSelfie = buttonOpenSelfie;
        this.retake = retake;
        this.imgPreview = imgPreview;
        this.controller = controller;
        this.imgSecond = imgSecond;
        this.imgThird = imgThird;
        this.imgForth = imgForth;
        this.tvTitle = tvTitle;
        controller.init(activity, CameraState.Idle);
        CheckDocumentCallBack callback = new CheckDocumentCallBack(this);
        Call<CameraData> call  = LykkeApplication_.getInstance().getRestApi().
                checkDocuments(Constants.PART_AUTHORIZATION + info.getToken());
        call.enqueue(callback);
    }

    public void submit(){
        if (model.isDone()){
            switch (controller.getCurrentState()){
                case Selfie:
                    if (!model.isSelfieSend()) {
                        activity.showProgress();
                        sendImage(model.getPathSelfie(), CameraType.Selfie);
                    }
                    break;
                case SelfieBack:
                    if (!model.isSelfieSend()) {
                        activity.showProgress();
                        sendImage(model.getPathSelfie(), CameraType.Selfie);
                    }
                    break;
                case IdCard:
                    if (!model.isSelfieSend()) {
                        activity.showProgress();
                        sendImage(model.getPathIdCard(), CameraType.IdCard);
                    }
                    break;
                case ProofOfAddress:
                    if (!model.isSelfieSend()) {
                        activity.showProgress();
                        sendImage(model.getPathProofAddress(), CameraType.ProofOfAddress);
                    }
                    break;
            }
        }
    }


    public void changeCamera(){
        if(model.isFront()){
            activity.initBackCamera();
            model.setIsFront(false);
        } else {
            activity.openSelfie();
            if (activity.mCamera == null) {
                activity.initBackCamera();
            } else {
                model.setIsFront(true);
            }
        }
        activity.mCameraPreview = new CameraPreview(activity, activity.mCamera);
        camera_preview.addView(activity.mCameraPreview);
        activity.mCamera.startPreview();
    }

    public void initProofOfAddress(){
        model.setIsDone(false);
        model.setIsFront(false);
        imgSecond.setBackgroundResource(R.drawable.ready);
        imgThird.setBackgroundResource(R.drawable.ready);
        imgForth.setBackgroundResource(R.drawable.submit_form_circle);
        tvTitle.setText(R.string.proof_adress);
        initGuiPhoto();
        if (model.getPathProofAddress() != null && !model.getPathProofAddress().isEmpty()) {
            initPhotoTaken(model.getPathSelfie());
        }
    }

    public void initIdCard(){
        model.setIsDone(false);
        model.setIsFront(false);
        imgSecond.setBackgroundResource(R.drawable.ready);
        imgThird.setBackgroundResource(R.drawable.submit_form_circle);
        tvTitle.setText(R.string.id_card);
        initGuiPhoto();
        if (model.getPathIdCard() != null && !model.getPathIdCard().isEmpty()) {
            initPhotoTaken(model.getPathSelfie());
        }
    }

    public void initSelfie(){
        model.setIsDone(false);
        model.setIsFront(true);
        tvTitle.setText(R.string.make_selfie);
        initGuiPhoto();
        if (model != null && model.getPathSelfie() != null && !model.getPathSelfie().isEmpty()) {
            initPhotoTaken(model.getPathSelfie());
        }
    }

    private void initGuiPhoto(){
        imgPreview.setVisibility(View.GONE);
        retake.setVisibility(View.GONE);
        submit.setVisibility(View.GONE);

        buttake_photo.setVisibility(View.VISIBLE);
        buttonFile.setVisibility(View.VISIBLE);
        buttonOpenSelfie.setVisibility(View.VISIBLE);
        camera_preview.setVisibility(View.VISIBLE);

    }


    public void initPhotoTaken(String path){
        model.setIsDone(true);

        imgPreview.setVisibility(View.VISIBLE);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        options.outHeight = camera_preview.getMeasuredHeight();
        options.outWidth = camera_preview.getMeasuredWidth();
        Bitmap bitmap = BitmapFactory.decodeFile(path, options);
        Drawable drawable = new BitmapDrawable(activity.getResources(), bitmap);
        imgPreview.setBackgroundDrawable(drawable);
        retake.setVisibility(View.VISIBLE);
        submit.setVisibility(View.VISIBLE);

        buttake_photo.setVisibility(View.GONE);
        buttonFile.setVisibility(View.GONE);
        buttonOpenSelfie.setVisibility(View.GONE);
        camera_preview.setVisibility(View.GONE);

        switch (controller.getCurrentState()){
            case Selfie:
                model.setPathSeldie(path);
                break;
            case ProofOfAddress:
                model.setPathProofAddress(path);
                break;
            case IdCard:
                model.setPathIdCard(path);
                break;
        }
    }

    public void retake(){
        retake.setVisibility(View.GONE);
        submit.setVisibility(View.GONE);

        buttake_photo.setVisibility(View.VISIBLE);
        buttonFile.setVisibility(View.VISIBLE);
        buttonOpenSelfie.setVisibility(View.VISIBLE);
        initGuiPhoto();
        switch (controller.getCurrentState()) {
            case Selfie:
                model.setIsSelfieSend(false);
                break;
            case IdCard:
                model.setIsCardIdeSend(false);
                break;
            case ProofOfAddress:
                model.setIsProofAddressSend(false);
                break;
        }
    }

    private String compressImage(String path){
        Bitmap bm = BitmapFactory.decodeFile(path);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] byteArrayImage = baos.toByteArray();
        return Base64.encodeToString(byteArrayImage, Base64.DEFAULT);
    }

    private void sendImage(String path, CameraType type){
        CameraModel model = new CameraModel();
        model.setExt(Constants.JPG);
        model.setData(compressImage(path));
        model.setType(type.toString());
        SendDocumentsDataCallback callback = new SendDocumentsDataCallback(this);
        Call<PersonData> call  = LykkeApplication_.getInstance().getRestApi().
                kysDocuments(Constants.PART_AUTHORIZATION + info.getToken(), model);
        call.enqueue(callback);
    }

    public void onBackPress(){
        switch (controller.getCurrentState()) {
            case Idle:
                activity.finish();
                break;
            case Selfie:
                activity.finish();
                break;
            case IdCard:
                controller.fire(CameraTrigger.SelfieBack);
                break;
            case ProofOfAddress:
                controller.fire(CameraTrigger.IdCard);
                break;
        }
    }

    @Override
    public void onSuccess(Object result) {
        switch (controller.getCurrentState()) {
            case Idle:
                activity.dialog.dismiss();
                if (result != null) {
                    CameraResult data = (CameraResult) result;
                    if (data != null) {
                        model.setIdCard(data.isIdCard());
                        model.setProofOfAddress(data.isProofOfAddress());
                        model.setSelfie(data.isSelfie());
                        if (data.isSelfie()){
                            controller.fire(CameraTrigger.Selfie);
                            model.setIsDone(false);
                        } else if (data.isIdCard()) {
                            controller.fire(CameraTrigger.IdCard);
                            model.setIsDone(false);
                        } else if (data.isProofOfAddress()) {
                            controller.fire(CameraTrigger.ProofOfAddress);
                            model.setIsDone(false);
                        }
                    }
                }
                break;
            case Selfie:
                activity.dismissProgress();
                if (model.isIdCard()){
                    controller.fire(CameraTrigger.IdCard);
                }
                break;
            case SelfieBack:
                if (!model.isSelfieSend()) {
                    activity.dismissProgress();
                    model.setIsSelfieSend(true);
                }
                if (model.isIdCard()){
                    controller.fire(CameraTrigger.IdCard);
                }
                break;
            case IdCard:
                if (!model.isCardIdeSend()) {
                    activity.dismissProgress();
                    model.setIsCardIdeSend(true);
                }
                if (model.isIdCard()){
                    controller.fire(CameraTrigger.ProofOfAddress);
                }
                break;
            case ProofOfAddress:
                if (!model.isProofAddressSend()) {
                    activity.dismissProgress();
                    model.setIsProofAddressSend(true);
                }
                controller.fire(CameraTrigger.CheckStatus);
                break;
            case CheckStatus:

                Intent intent = new Intent();
                intent.setClass(activity, KysActivity_.class);
                activity.finish();
                activity.startActivity(intent);
                break;
        }
    }

    @Override
    public void onFail(com.lykkex.LykkeWallet.rest.base.models.Error error) {
        activity.dismissProgress();
    }
}
