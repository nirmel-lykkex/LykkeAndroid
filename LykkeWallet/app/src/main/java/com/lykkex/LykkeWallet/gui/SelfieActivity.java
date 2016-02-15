package com.lykkex.LykkeWallet.gui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.gui.fragments.CameraPreview;
import com.lykkex.LykkeWallet.gui.fragments.controllers.CameraController;
import com.lykkex.LykkeWallet.gui.fragments.guisegments.CameraGuiSegment;
import com.lykkex.LykkeWallet.gui.utils.Constants;
import com.lykkex.LykkeWallet.rest.registration.response.models.RegistrationResult;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by e.kazimirova on 12.02.2016.
 */
@EActivity(R.layout.selfie_activity)
public class SelfieActivity extends ActionBarActivity {

    public android.hardware.Camera mCamera;
    public CameraPreview mCameraPreview;
    public ProgressDialog dialog;

    private CameraGuiSegment guiSegment;
    @ViewById FrameLayout camera_preview;
    @ViewById Button submit;
    @ViewById TextView tvTitle;
    @ViewById Button buttake_photo;
    @ViewById Button buttonFile;
    @ViewById Button buttonOpenSelfie;
    @ViewById Button retake;
    @ViewById ImageView imgPreview;
    @ViewById ImageView imgSecond;
    @ViewById ImageView imgThird;
    @ViewById ImageView imgForth;
    @Bean CameraController controller;
    @ViewById RelativeLayout wasCreateRel;
    @ViewById  RelativeLayout sendDocumentRel;

    /** Called when the activity is first created. */
    @AfterViews
    public void afterViews() {
        RegistrationResult info = null;
        if (getIntent() != null && getIntent().getExtras() != null) {
            info = (RegistrationResult)getIntent().getExtras().getSerializable(Constants.EXTRA_PERSON_DATA);
        }
        dialog = new ProgressDialog(this);
        dialog.setMessage(getString(R.string.loading));
        showProgress();
        guiSegment = new CameraGuiSegment();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.registration);
        guiSegment.init(this, controller,camera_preview,
                submit,
                buttake_photo,
                buttonFile,
                buttonOpenSelfie,
                retake,
                imgPreview,
                info,
                imgSecond,
                imgThird,
                imgForth, tvTitle);

    }

    public void initBackCamera(){
        Camera cam = getCameraInstance();
        if (cam != null) {
            mCamera = cam;
        }
    }

    public void showProgress(){
        dialog.show();
    }

    public void dismissProgress(){
        dialog.dismiss();
    }

    @Click(R.id.buttake_photo)
    public void takePhoto(){
        mCamera.takePicture(null, null, mPicture);
    }

    @Click(R.id.buttonOpenSelfie)
    public void clickChange(){
        guiSegment.changeCamera();
    }

    public void openSelfie(){
        Camera cam = openFrontFacingCameraGingerbread();
        if (cam != null) {
            mCamera = cam;
        }
    }

    @Click(R.id.buttonFile)
    public void clickFile(){
        showFileChooser();
    }

    public void checkStatus(){
        sendDocumentRel.setVisibility(View.GONE);
        wasCreateRel.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
       switch (requestCode) {
            case Constants.FILE_SELECT_CODE:
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                    String path = uri.getPath();
                    guiSegment.initPhotoTaken(path);
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }



    private void showFileChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        try {
            startActivityForResult(
                    Intent.createChooser(intent, "Select a File to Upload"),
                    Constants.FILE_SELECT_CODE);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "Please install a File Manager.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private android.hardware.Camera getCameraInstance() {
        if (mCamera != null) {
            mCamera.stopPreview();
            mCameraPreview.getHolder().removeCallback(mCameraPreview);
            mCamera.release();
        }
        android.hardware.Camera camera = null;
        try {
            camera = android.hardware.Camera.open();
        } catch (Exception e) {
            // cannot get camera or does not exist
        }
        return camera;
    }

    android.hardware.Camera.PictureCallback mPicture = new android.hardware.Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, android.hardware.Camera camera) {
            File pictureFile = getOutputMediaFile();
            if (pictureFile == null) {
                return;
            }
            try {
                FileOutputStream fos = new FileOutputStream(pictureFile);
                fos.write(data);
                fos.close();
                guiSegment.initPhotoTaken(pictureFile.getAbsolutePath());
            } catch (FileNotFoundException e) {

            } catch (IOException e) {
            }
        }
    };

    private static File getOutputMediaFile() {
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                "MyCameraApp");
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }
        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                .format(new Date());
        File mediaFile;
        mediaFile = new File(mediaStorageDir.getPath() + File.separator
                + "IMG_" + timeStamp + ".jpg");

        return mediaFile;
    }

    private Camera openFrontFacingCameraGingerbread() {
        if (mCamera != null) {
            mCamera.stopPreview();
            mCamera.release();
        }
        int cameraCount = 0;
        Camera cam = null;
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        cameraCount = Camera.getNumberOfCameras();
        for (int camIdx = 0; camIdx < cameraCount; camIdx++) {
            Camera.getCameraInfo(camIdx, cameraInfo);
            if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                try {
                    cam = Camera.open(camIdx);
                } catch (RuntimeException e) {

                }
            }
        }

        return cam;
    }

    @Click(R.id.submit)
    public void clickSubmit(){
        mCamera.startPreview();
        guiSegment.retake();
        guiSegment.submit();
    }

    @Click(R.id.retake)
    public void clickReTake(){
        mCamera.startPreview();
        guiSegment.retake();
    }

    public void initSelfie(){
        sendDocumentRel.setVisibility(View.VISIBLE);
        guiSegment.initSelfie();
        openSelfie();
        if (mCamera == null) {
            initBackCamera();
        }
        mCameraPreview = new CameraPreview(this, mCamera);
        camera_preview.addView(mCameraPreview);
    }

    public void initSelfieBack(){
        sendDocumentRel.setVisibility(View.VISIBLE);
        guiSegment.initSelfie();
    }

    public void initIdCard(){
        sendDocumentRel.setVisibility(View.VISIBLE);
        guiSegment.initIdCard();
    }

    public void init(){
       dismissProgress();
    }

    public void initProofOfAddress(){
        sendDocumentRel.setVisibility(View.VISIBLE);
        guiSegment.initProofOfAddress();
    }

    @Override
    public void onBackPressed(){
        guiSegment.onBackPress();
    }
}
