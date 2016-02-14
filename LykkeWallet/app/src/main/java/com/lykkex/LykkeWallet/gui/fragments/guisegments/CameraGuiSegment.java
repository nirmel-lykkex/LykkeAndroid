package com.lykkex.LykkeWallet.gui.fragments.guisegments;


import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.lykkex.LykkeWallet.gui.LykkeApplication_;
import com.lykkex.LykkeWallet.gui.SelfieActivity;
import com.lykkex.LykkeWallet.gui.fragments.controllers.CameraController;
import com.lykkex.LykkeWallet.gui.fragments.models.CameraModelGUI;
import com.lykkex.LykkeWallet.gui.fragments.statesegments.states.CameraState;
import com.lykkex.LykkeWallet.gui.fragments.statesegments.triggers.CameraTrigger;
import com.lykkex.LykkeWallet.gui.utils.validation.CallBackListener;
import com.lykkex.LykkeWallet.rest.camera.callback.CheckDocumentCallBack;
import com.lykkex.LykkeWallet.rest.camera.response.models.CameraData;
import com.lykkex.LykkeWallet.rest.login.response.model.PersonalData;
import com.lykkex.LykkeWallet.rest.registration.response.models.AccountExistData;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

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


    public void init(SelfieActivity activity, CameraController controller,
                    FrameLayout camera_preview,
                            Button submit,
                            Button buttake_photo,
                            Button buttonFile,
                            Button buttonOpenSelfie,
                             Button retake,
                             ImageView imgPreview
    ) {
        model = new CameraModelGUI();
        this.activity = activity;
        this.camera_preview = camera_preview;
        this.submit = submit;
        this.buttake_photo = buttake_photo;
        this.buttonFile = buttonFile;
        this.buttonOpenSelfie = buttonOpenSelfie;
        this.retake = retake;
        this.imgPreview = imgPreview;
        this.controller = controller;
        controller.init(activity, CameraState.Idle);
       // CheckDocumentCallBack callback = new CheckDocumentCallBack(this);
        //TODO
        //Call<CameraData> call  = LykkeApplication_.getInstance().getRestApi().checkDocuments(new PersonalData());
        //call.enqueue(callback);
        controller.fire(CameraTrigger.Selfie);
    }

    public void submit(){
        if (model.isDone()){
            switch (controller.getCurrentState()){
                case Selfie:
                    if (model.isIdCard()){
                        controller.fire(CameraTrigger.IdCard);
                    }
                    break;
                case IdCard:
                    if (model.isProofOfAddress()){
                        controller.fire(CameraTrigger.ProofOfAddress);
                    }
                    break;
                case ProofOfAddress:
                    break;
            }
        }
    }


    public void changeCamera(){
        if(model.isFront()){
            activity.initBackCamera();
        } else {
            activity.openSelfie();
        }
    }

    public void initProofOfAddress(){
        model.setIsDone(false);
        model.setIsFront(false);
        initGuiPhoto();
        activity.initBackCamera();

    }

    public void initIdCard(){
        model.setIsDone(false);
        model.setIsFront(false);
        initGuiPhoto();
        activity.initBackCamera();
    }

    public void initSelfie(){
        model.setIsDone(false);
        model.setIsFront(true);
        initGuiPhoto();
        activity.openSelfie();
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

        imgPreview.setVisibility(View.GONE);
        retake.setVisibility(View.VISIBLE);
        submit.setVisibility(View.VISIBLE);

        buttake_photo.setVisibility(View.GONE);
        buttonFile.setVisibility(View.GONE);
        buttonOpenSelfie.setVisibility(View.GONE);
        //camera_preview.setVisibility(View.VISIBLE);

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
    }

    @Override
    public void onSuccess(Object result) {
        switch (controller.getCurrentState()) {
            case Idle:
                activity.dialog.dismiss();
                if (result != null) {
                    CameraData data = (CameraData) result;
                    if (data.getResult() != null) {
                        model.setIdCard(data.getResult().isIdCard());
                        model.setProofOfAddress(data.getResult().isProofOfAddress());
                        model.setSelfie(data.getResult().isSelfie());
                        if (data.getResult().isSelfie()){
                            controller.fire(CameraTrigger.Selfie);
                            model.setIsDone(false);
                        }
                    }
                }
                break;
        }
    }

    @Override
    public void onFail(com.lykkex.LykkeWallet.rest.base.models.Error error) {
        model.setIdCard(true);
        model.setProofOfAddress(true);
        model.setSelfie(true);
        controller.fire(CameraTrigger.Selfie);
        model.setIsDone(false);

    }
}
