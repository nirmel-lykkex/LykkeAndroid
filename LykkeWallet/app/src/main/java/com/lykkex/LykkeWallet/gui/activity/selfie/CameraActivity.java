package com.lykkex.LykkeWallet.gui.activity.selfie;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.gui.activity.BaseActivity;
import com.lykkex.LykkeWallet.gui.activity.selfie.util.FileUtil;
import com.lykkex.LykkeWallet.gui.fragments.camerascreen.CameraIdCardFragment;
import com.lykkex.LykkeWallet.gui.fragments.camerascreen.CameraIdCardFragment_;
import com.lykkex.LykkeWallet.gui.fragments.camerascreen.CameraProofOfAddressFragment;
import com.lykkex.LykkeWallet.gui.fragments.camerascreen.CameraProofOfAddressFragment_;
import com.lykkex.LykkeWallet.gui.fragments.camerascreen.CameraSelfieFragment_;
import com.lykkex.LykkeWallet.gui.fragments.camerascreen.SubmitFragment_;
import com.lykkex.LykkeWallet.gui.managers.UserManager;
import com.lykkex.LykkeWallet.gui.utils.Constants;
import com.lykkex.LykkeWallet.rest.camera.response.models.CameraResult;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;

/**
 * Created by e.kazimirova on 12.02.2016.
 */
@EActivity(R.layout.activity_main)
public class CameraActivity extends BaseActivity {

    @Bean
    UserManager userManager;

    @AfterViews
    public void afterViews() {
        CameraResult cameraResult = (CameraResult) getIntent().getSerializableExtra(Constants.EXTRA_CAMERA_DATA);

        userManager.setCameraResult(cameraResult);

        if(userManager.getCameraResult().isSelfie()) {
            initFragment(new CameraSelfieFragment_(), null, false);
        } else if(userManager.getCameraResult().isIdCard()) {
            initFragment(new CameraIdCardFragment_(), null, false);
        } else if(userManager.getCameraResult().isProofOfAddress()) {
            initFragment(new CameraProofOfAddressFragment_(), null, false);
        } else {
            Bundle bundle = new Bundle();
            bundle.putSerializable(Constants.EXTRA_CAMERA_MODEL_GUI, cameraResult);
            initFragment(new SubmitFragment_(), bundle);
        }
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case Constants.FILE_SELECT_CODE:
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                    String path;
                    try {
                        path = FileUtil.getPath(this, uri);
                        //if (currentFragment instanceof BaseCameraFragment) {
                          //  ((BaseCameraFragment)currentFragment).setUpPhotoPath(new File(path));
                            //((BaseCameraFragment)currentFragment).showTakenFromFile(path);
                        //}
                    } catch (IllegalStateException ex){}
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
