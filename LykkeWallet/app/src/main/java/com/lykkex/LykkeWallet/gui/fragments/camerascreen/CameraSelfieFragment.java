package com.lykkex.LykkeWallet.gui.fragments.camerascreen;

import android.graphics.Bitmap;

import com.commonsware.cwac.camera.CameraHost;
import com.commonsware.cwac.camera.CameraHostProvider;
import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.gui.activity.selfie.CameraActivity;
import com.lykkex.LykkeWallet.gui.activity.selfie.CameraHostSelfie;
import com.lykkex.LykkeWallet.gui.fragments.BaseFragment;
import com.lykkex.LykkeWallet.gui.fragments.statesegments.states.CameraState;
import com.lykkex.LykkeWallet.gui.fragments.statesegments.triggers.CameraTrigger;
import com.lykkex.LykkeWallet.rest.base.models.Error;
import com.lykkex.LykkeWallet.rest.camera.request.models.CameraType;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;

import java.io.File;

/**
 * Created by LIZA on 23.02.2016.
 */
@EFragment(R.layout.selfie_activity)
public class CameraSelfieFragment extends BaseCameraFragment implements
        CameraHostProvider {


    @Override
    public CameraHost getCameraHost() {
        return new CameraHostSelfie(getActivity(), this);
    }
}
