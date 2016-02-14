package com.lykkex.LykkeWallet.gui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.gui.fragments.CameraPreview;
import com.lykkex.LykkeWallet.gui.fragments.controllers.CameraController;
import com.lykkex.LykkeWallet.gui.fragments.guisegments.CameraGuiSegment;
import com.lykkex.LykkeWallet.gui.utils.Constants;

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
public class SelfieActivity extends Activity {

    private android.hardware.Camera mCamera;
    private CameraPreview mCameraPreview;
    public ProgressDialog dialog;

    private CameraGuiSegment guiSegment;
    @ViewById FrameLayout camera_preview;
    @ViewById Button submit;
    @ViewById Button buttake_photo;
    @ViewById Button buttonFile;
    @ViewById Button buttonOpenSelfie;
    @ViewById Button retake;
    @ViewById ImageView imgPreview;
    @Bean CameraController controller;

    /** Called when the activity is first created. */
    @AfterViews
    public void afterViews() {
        dialog = new ProgressDialog(this);
        guiSegment = new CameraGuiSegment();
        dialog.show();
        guiSegment.init(this, controller,camera_preview,
                submit,
                buttake_photo,
                buttonFile,
                buttonOpenSelfie,
                retake,
                imgPreview);

    }

    public void initBackCamera(){
        mCamera = getCameraInstance();
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
        mCamera = openFrontFacingCameraGingerbread();
    }

    @Click(R.id.buttonFile)
    public void clickFile(){
        showFileChooser();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
       switch (requestCode) {
            case Constants.FILE_SELECT_CODE:
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();

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
        guiSegment.submit();
    }

    @Click(R.id.retake)
    public void clickReTake(){
        mCamera.startPreview();
        guiSegment.retake();
    }

    public void initSelfie(){
        guiSegment.initSelfie();
        initBackCamera();

        mCameraPreview = new CameraPreview(this, mCamera);
        camera_preview.addView(mCameraPreview);
    }

    public void initIdCard(){
        guiSegment.initIdCard();
    }

    public void init(){
        dialog.dismiss();
    }

    public void initProofOfAddress(){
        guiSegment.initProofOfAddress();
    }
}
