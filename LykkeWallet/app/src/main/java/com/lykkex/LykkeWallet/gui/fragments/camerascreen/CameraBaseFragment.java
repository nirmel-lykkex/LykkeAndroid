package com.lykkex.LykkeWallet.gui.fragments.camerascreen;

import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.widget.Button;
import android.widget.ImageView;

import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.gui.LykkeApplication;
import com.lykkex.LykkeWallet.gui.customviews.StepsIndicator;
import com.lykkex.LykkeWallet.gui.managers.UserManager;
import com.lykkex.LykkeWallet.gui.utils.Constants;
import com.lykkex.LykkeWallet.gui.widgets.DialogProgress;
import com.lykkex.LykkeWallet.rest.camera.callback.SendDocumentsDataCallback;
import com.lykkex.LykkeWallet.rest.camera.request.models.CameraModel;
import com.lykkex.LykkeWallet.rest.camera.request.models.CameraType;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.io.ByteArrayOutputStream;
import java.util.Random;

import retrofit2.Call;

/**
 * Created by Murtic on 31/05/16.
 */
@EFragment
public abstract class CameraBaseFragment extends Fragment {

    protected static final int REQUEST_IMAGE_CAPTURE = 101;
    protected static final int REQUEST_CAMERA_PERMISSION = 102;

    @Bean
    UserManager userManager;

    @ViewById
    ImageView imageView;

    @ViewById
    Button buttonShoot;

    @ViewById
    Button buttonAction;

    @ViewById
    StepsIndicator stepsIndicator;

    @App
    LykkeApplication lykkeApplication;

    protected ProgressDialog dialog;

    protected DialogProgress progressDialog = new DialogProgress();

    @AfterViews
    void afterViews() {
        stepsIndicator.setCurrentStep(2);

        userManager.setCameraModel(new CameraModel());

        CameraModel model = new CameraModel();
        model.setExt(Constants.JPG);
        model.setType(CameraType.Selfie.toString());

        userManager.setCameraModel(model);

        dialog = new ProgressDialog(getActivity());
        dialog.setMessage(getString(R.string.waiting));
    }

    @Click(R.id.buttonAction)
    public void onSubmit() {
        sendImage();
    }

    @Click({ R.id.buttonShoot })
    public void onShoot() {
        dispatchTakePictureIntent();
    }

    @Click({ R.id.imageView })
    public void onClickImageView() {
        if(userManager.getCameraModel().getData() == null) {
            dispatchTakePictureIntent();
        }
    }

    private void dispatchTakePictureIntent() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (getActivity().checkSelfPermission(Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {

                requestPermissions(new String[]{Manifest.permission.CAMERA},
                        REQUEST_CAMERA_PERMISSION);

                return;
            }
        }

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(imageBitmap);
            imageView.setPadding(0, 0, 0, 0);

            buttonAction.setEnabled(true);

            applyImage(imageBitmap);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CAMERA_PERMISSION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    dispatchTakePictureIntent();
                }
                return;
            }
        }
    }

    abstract protected void sendImage();

    public void showProgress(Call call, SendDocumentsDataCallback callback){
        progressDialog.setUpCurrentCall(call, callback);
        progressDialog.show(getActivity().getFragmentManager(),
                    "dlg1" +new Random((int)Constants.DELAY_5000));
    }

    @Background
    void applyImage(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        userManager.getCameraModel().setData(Base64.encodeToString(byteArray, Base64.DEFAULT));
    }
}
