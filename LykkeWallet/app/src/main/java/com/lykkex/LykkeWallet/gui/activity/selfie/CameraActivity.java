package com.lykkex.LykkeWallet.gui.activity.selfie;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.commonsware.cwac.camera.CameraHost;
import com.commonsware.cwac.camera.CameraHostProvider;
import com.commonsware.cwac.camera.CameraView;
import com.commonsware.cwac.camera.PictureTransaction;
import com.commonsware.cwac.camera.SimpleCameraHost;
import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.gui.LykkeApplication_;
import com.lykkex.LykkeWallet.gui.activity.BaseActivity;
import com.lykkex.LykkeWallet.gui.activity.selfie.util.FileUtil;
import com.lykkex.LykkeWallet.gui.fragments.BaseFragment;
import com.lykkex.LykkeWallet.gui.fragments.camerascreen.BaseCameraFragment;
import com.lykkex.LykkeWallet.gui.fragments.camerascreen.CameraSelfieFragment_;
import com.lykkex.LykkeWallet.gui.fragments.controllers.CameraController;
import com.lykkex.LykkeWallet.gui.fragments.models.CameraModelGUI;
import com.lykkex.LykkeWallet.gui.fragments.statesegments.states.CameraState;
import com.lykkex.LykkeWallet.gui.fragments.statesegments.triggers.CameraTrigger;
import com.lykkex.LykkeWallet.gui.fragments.storage.UserPref_;
import com.lykkex.LykkeWallet.gui.utils.Constants;
import com.lykkex.LykkeWallet.gui.utils.Consume;
import com.lykkex.LykkeWallet.gui.utils.validation.CallBackListener;
import com.lykkex.LykkeWallet.gui.widgets.DialogProgress;
import com.lykkex.LykkeWallet.rest.base.models.Error;
import com.lykkex.LykkeWallet.rest.camera.callback.CheckDocumentCallBack;
import com.lykkex.LykkeWallet.rest.camera.response.models.CameraData;
import com.lykkex.LykkeWallet.rest.camera.response.models.CameraResult;
import com.lykkex.LykkeWallet.rest.login.response.model.PersonalData;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.io.File;
import java.util.Random;

import retrofit2.Call;

/**
 * Created by e.kazimirova on 12.02.2016.
 */
@EActivity(R.layout.activity_main)
public class CameraActivity extends BaseActivity implements CameraHostProvider{

    protected CameraController controller;
    protected CameraModelGUI model;

    @AfterViews
    public void afterViews() {
        model = new CameraModelGUI();
        initFragment(new CameraSelfieFragment_(), null, false);
    }

    @Deprecated
    public void onBackPressed(){
        //((BaseFragment)currentFragment).initOnBackPressed();
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
    public CameraHost getCameraHost() {
        return ((BaseCameraFragment) currentFragment).getCameraHost();
    }

    public void initFragment(Fragment fragment, Bundle bundle, CameraController cameraController,
                             CameraModelGUI model){
        initFragment(fragment, bundle, false);
        this.controller = cameraController;
        this.model = model;
    }
    public CameraController getController() {
        return controller;
    }

    public CameraModelGUI getModel() {
        return model;
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
                        if (currentFragment instanceof BaseCameraFragment) {
                            ((BaseCameraFragment)currentFragment).setUpPhotoPath(new File(path));
                            ((BaseCameraFragment)currentFragment).showTakenFromFile(path);
                        }
                    } catch (IllegalStateException ex){}
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


}
