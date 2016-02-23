package com.lykkex.LykkeWallet.gui.activity.selfie;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.hardware.Camera;

import com.commonsware.cwac.camera.CameraHost;
import com.commonsware.cwac.camera.PictureTransaction;
import com.commonsware.cwac.camera.SimpleCameraHost;
import com.lykkex.LykkeWallet.gui.fragments.camerascreen.BaseCameraFragment;
import com.lykkex.LykkeWallet.gui.fragments.statesegments.states.CameraState;

import org.androidannotations.annotations.AfterViews;

/**
 * Created by LIZA on 22.02.2016.
 */
public class CameraHostBack  extends SimpleCameraHost {

    protected Camera.Size previewSize;
    protected BaseCameraFragment fragment;

    public CameraHostBack(Context ctxt, BaseCameraFragment fragment) {
        super(ctxt);
        this.fragment = fragment;
    }

    @Override
    public boolean useFullBleedPreview() {
        return true;
    }

    @Override
    public Camera.Size getPictureSize(PictureTransaction xact, Camera.Parameters parameters) {
        return previewSize;
    }

    @Override
    public Camera.Parameters adjustPreviewParameters(Camera.Parameters parameters) {
        Camera.Parameters parameters1 = super.adjustPreviewParameters(parameters);
        previewSize = parameters1.getPreviewSize();
        return parameters1;
    }

    @Override
    public void saveImage(PictureTransaction xact, final Bitmap bitmap) {
        fragment.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                fragment.showTakenPicture(bitmap);
            }
        });
    }

    @Override
    public void saveImage(PictureTransaction xact, byte[] image) {
        super.saveImage(xact, image);
        fragment.setUpPhotoPath(getPhotoPath());
    }
}