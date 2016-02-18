package com.lykkex.LykkeWallet.gui;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.text.Layout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.gui.fragments.CameraPreview;
import com.lykkex.LykkeWallet.gui.fragments.controllers.CameraController;
import com.lykkex.LykkeWallet.gui.fragments.guisegments.CameraGuiSegment;
import com.lykkex.LykkeWallet.gui.fragments.statesegments.states.CameraState;
import com.lykkex.LykkeWallet.gui.fragments.statesegments.triggers.CameraTrigger;
import com.lykkex.LykkeWallet.gui.fragments.storage.UserPref_;
import com.lykkex.LykkeWallet.gui.utils.Constants;
import com.lykkex.LykkeWallet.rest.camera.callback.SendDocumentsDataCallback;
import com.lykkex.LykkeWallet.rest.camera.response.models.PersonData;
import com.lykkex.LykkeWallet.rest.registration.response.models.RegistrationResult;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;

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
    @ViewById Button btnStart;
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
    @ViewById ProgressBar progressBar;
    @Bean CameraController controller;
    @ViewById RelativeLayout wasCreateRel;
    @ViewById  RelativeLayout sendDocumentRel;
    @ViewById TextView textView3;

    /** Called when the activity is first created. */
    @AfterViews
    public void afterViews() {
        RegistrationResult info = null;
        dialog = new ProgressDialog(this);
        dialog.setMessage(getString(R.string.loading));
        showProgress();
        guiSegment = new CameraGuiSegment();
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setTitle(R.string.registration);
        guiSegment.init(this, controller, camera_preview,
                submit,
                buttake_photo,
                buttonFile,
                buttonOpenSelfie,
                retake,
                imgPreview,
                imgSecond,
                imgThird,
                imgForth, tvTitle, progressBar);

    }

    public void initBackCamera(){
        if(mCamera != null)
        {
            // Destroy previuos Holder
            mCameraPreview.surfaceDestroyed(mCameraPreview.getHolder());
            mCameraPreview.getHolder().removeCallback(mCameraPreview);
            camera_preview.removeView(mCameraPreview);
        }
        Camera cam = getCameraInstance();
        if (cam != null) {
            mCamera = cam;
            mCameraPreview = new CameraPreview(this, mCamera);
            mCameraPreview.getHolder().addCallback(mCameraPreview);
            camera_preview.addView(mCameraPreview);
            setOrientation();
            mCamera.startPreview();
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
        if(mCamera != null)
        {
            mCameraPreview.surfaceDestroyed(mCameraPreview.getHolder());
            mCameraPreview.getHolder().removeCallback(mCameraPreview);
            camera_preview.removeView(mCameraPreview);
        }
        Camera cam = openFrontFacingCameraGingerbread();
        if (cam != null) {
            mCamera = cam;
            mCameraPreview = new CameraPreview(this, mCamera);
            camera_preview.addView(mCameraPreview);
            setOrientation();
            mCamera.startPreview();
        }
    }

    @Click(R.id.buttonFile)
    public void clickFile(){
        showFileChooser();
    }

    public void onPause(){
        super.onPause();
        if(mCamera != null)
        {
            mCameraPreview.surfaceDestroyed(mCameraPreview.getHolder());
            mCameraPreview.getHolder().removeCallback(mCameraPreview);
            camera_preview.removeView(mCameraPreview);
        }
    }

    public void onStart(){
        super.onStart();
        if (controller.getCurrentState() == CameraState.Selfie ||
                controller.getCurrentState() == CameraState.SelfieBack) {
            openSelfie();
        } else {
            initBackCamera();
        }
    }

    public void checkStatus(){
        getSupportActionBar().setTitle(R.string.send_document);
        sendDocumentRel.setVisibility(View.GONE);
        wasCreateRel.setVisibility(View.VISIBLE);
        textView3.setText(String.format(getString(R.string.dear_it_checked),
                new UserPref_(this).fullName().get()));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case Constants.FILE_SELECT_CODE:
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                    String path;
                    try {
                        if (Integer.parseInt(Build.VERSION.SDK) >= 11 && (Integer.parseInt(Build.VERSION.SDK) <= 18)) {
                            path = getPath11to18(this, uri);
                        } else if (Integer.parseInt(Build.VERSION.SDK) <= 11) {
                            path = getPath11(this, uri);
                        } else {
                            path = getPath19(this, uri);
                        }
                        guiSegment.initPhotoTakenFromFile(path, true);
                    } catch (IllegalStateException ex){}
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    public static String getPath11to18(Context context, Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };
        String result = null;

        CursorLoader cursorLoader = new CursorLoader(
                context,
                contentUri, proj, null, null, null);
        Cursor cursor = cursorLoader.loadInBackground();

        if(cursor != null){
            int column_index =
                    cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            result = cursor.getString(column_index);
        }
        return result;
    }

    public static String getPath11(Context context, Uri contentUri){
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
        int column_index
                = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static String getPath19(final Context context, final Uri uri) {

        String filePath = "";
        String wholeID = DocumentsContract.getDocumentId(uri);

        // Split at colon, use second item in the array
        String id = wholeID.split(":")[1];

        String[] column = { MediaStore.Images.Media.DATA };

        // where id is equal to
        String sel = MediaStore.Images.Media._ID + "=?";

        Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                column, sel, new String[]{ id }, null);

        int columnIndex = cursor.getColumnIndex(column[0]);

        if (cursor.moveToFirst()) {
            filePath = cursor.getString(columnIndex);
        }
        cursor.close();
        return filePath;
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
                guiSegment.initPhotoTaken(pictureFile.getAbsolutePath(), false);
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

    private void setOrientation(){
        if (Integer.parseInt(Build.VERSION.SDK) >= 8)
            setDisplayOrientation(mCamera, 90);
        else
        {
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            {
                mCamera.getParameters().set("orientation", "portrait");
                mCamera.getParameters().set("rotation", 90);
            }
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
            {
                mCamera.getParameters().set("orientation", "landscape");
                mCamera.getParameters().set("rotation", 90);
            }
        }

       /* initPreview(getWindowManager().getDefaultDisplay().getWidth(),
                getWindowManager().getDefaultDisplay().getHeight());*/

    }

    private void initPreview(int width, int height) {
        if (mCamera != null && mCameraPreview != null && mCameraPreview.getHolder() != null) {

            Camera.Parameters parameters = mCamera.getParameters();
            Camera.Size size = getBestPreviewSize(width, height, parameters);

            if (size != null) {
                parameters.setPreviewSize(size.width, size.height);
                mCamera.setParameters(parameters);

                // Setting up correctly the view
                double ratio = size.height / (double) size.width;
                ViewGroup.LayoutParams params = mCameraPreview.getLayoutParams();
                params.height = getWindowManager().getDefaultDisplay().getHeight();
                params.width = (int) ( getWindowManager().getDefaultDisplay().getHeight() * ratio);
                mCameraPreview.setLayoutParams(params);
                int deslocationX = (int) (params.width / 2.0 -  getWindowManager().getDefaultDisplay().getWidth() / 2.0);
                mCameraPreview.animate().translationX(-deslocationX);
            }
        }

        try {
            mCamera.setPreviewDisplay(mCameraPreview.getHolder());
            mCamera.setDisplayOrientation(90);

        } catch (Throwable t) {

        }
    }

    private Camera.Size getBestPreviewSize(int width, int height,
                                           Camera.Parameters parameters) {
        Camera.Size result = null;

        for (Camera.Size size : parameters.getSupportedPreviewSizes()) {
            if (size.width <= width && size.height <= height) {
                if (result == null) {
                    result = size;
                } else {
                    int resultArea = result.width * result.height;
                    int newArea = size.width * size.height;

                    if (newArea > resultArea) {
                        result = size;
                    }
                }
            }
        }

        return (result);
    }

    protected void setDisplayOrientation(Camera camera, int angle) {
        Method downPolymorphic;
        try {
            downPolymorphic = camera.getClass().getMethod("setDisplayOrientation", new Class[]{int.class});
            if (downPolymorphic != null)
                downPolymorphic.invoke(camera, new Object[]{angle});
        } catch (Exception e1) {
        }
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
                    return null;
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

    @Click(R.id.btnStart)
    public void clickBtnStart(){
        controller.fire(CameraTrigger.SubmitStatus);
    }

    private Handler mHandler = new Handler();

    public void sendRequestForCheck(){
        Log.e("Liza ", "send document started");
        guiSegment.sendDocumentForCheck();
        if (isShouldContinue) {
            initHandler();
        }
    }

    private boolean isShouldContinue = true;
    public void stopHandler(){
        isShouldContinue = false;
        mHandler.removeCallbacks(run);
    }

    private Runnable run = new Runnable() {
        @Override
        public void run() {
            sendRequestForCheck();
        }
    };

    private void initHandler(){
        mHandler.postDelayed(run, Constants.DELAY_15000);
    }

    public void getDocument(){
        btnStart.setEnabled(false);
        guiSegment.getDocument();
    }

    public void initSelfie(){
        wasCreateRel.setVisibility(View.GONE);
        sendDocumentRel.setVisibility(View.VISIBLE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setTitle(R.string.registration);
        guiSegment.initSelfie();
        openSelfie();
        if (mCamera == null) {
            initBackCamera();
        }
    }

    public void initSelfieBack(){
        wasCreateRel.setVisibility(View.GONE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setTitle(R.string.registration);
        openSelfie();
        if (mCamera == null) {
            initBackCamera();
        }
        sendDocumentRel.setVisibility(View.VISIBLE);
        guiSegment.initSelfie();
    }

    public void initIdCard(){
        wasCreateRel.setVisibility(View.GONE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.registration);
        sendDocumentRel.setVisibility(View.VISIBLE);
        initBackCamera();
        guiSegment.initIdCard();
    }

    public void init(){
        dismissProgress();
    }

    public void initProofOfAddress(){
        wasCreateRel.setVisibility(View.GONE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.registration);
        sendDocumentRel.setVisibility(View.VISIBLE);
        initBackCamera();
        guiSegment.initProofOfAddress();
    }

    @Override
    public void onBackPressed(){
        guiSegment.onBackPress();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
