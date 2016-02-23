package com.lykkex.LykkeWallet.gui.fragments.camerascreen;

import android.graphics.Bitmap;

import com.commonsware.cwac.camera.CameraHost;
import com.commonsware.cwac.camera.CameraHostProvider;
import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.gui.activity.selfie.CameraActivity;
import com.lykkex.LykkeWallet.gui.activity.selfie.CameraHostBack;
import com.lykkex.LykkeWallet.gui.activity.selfie.CameraHostSelfie;
import com.lykkex.LykkeWallet.gui.fragments.statesegments.states.CameraState;
import com.lykkex.LykkeWallet.rest.base.models.Error;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;

/**
 * Created by LIZA on 23.02.2016.
 */
@EFragment(R.layout.selfie_activity)
public class CameraBackFragment extends BaseCameraFragment implements
        CameraHostProvider {

    @Override
    public CameraHost getCameraHost() {
        return new CameraHostBack(getActivity(), this);
    }
}
