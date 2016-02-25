package com.lykkex.LykkeWallet.gui.activity.selfie;

import android.content.Context;
import android.graphics.Bitmap;
import android.hardware.Camera;

import com.commonsware.cwac.camera.PictureTransaction;
import com.commonsware.cwac.camera.SimpleCameraHost;
import com.lykkex.LykkeWallet.gui.fragments.camerascreen.BaseCameraFragment;

/**
 * Created by LIZA on 22.02.2016.
 */
public class CameraHostSelfie extends CameraHostBack {

    public CameraHostSelfie(Context ctxt, BaseCameraFragment fragment) {
        super(ctxt, fragment);
    }

    @Override
    public boolean useFrontFacingCamera() {
        return true;
    }

    @Override
    public boolean mirrorFFC() {
        return true;
    }

}