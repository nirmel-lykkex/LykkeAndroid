package com.lykkex.LykkeWallet.gui.activity.authentication;

import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.gui.activity.BaseActivity;
import com.lykkex.LykkeWallet.gui.fragments.BaseFragment;
import com.lykkex.LykkeWallet.gui.fragments.controllers.CameraController;
import com.lykkex.LykkeWallet.gui.fragments.controllers.FieldController;
import com.lykkex.LykkeWallet.gui.fragments.models.AuthModelGUI;
import com.lykkex.LykkeWallet.gui.fragments.models.CameraModelGUI;
import com.lykkex.LykkeWallet.gui.fragments.models.RegistrationModelGUI;
import com.lykkex.LykkeWallet.gui.fragments.startscreen.FieldFragment_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

@EActivity(R.layout.activity_main)
public class FieldActivity extends BaseActivity {

    private FieldController controller;
    private RegistrationModelGUI model;

    @AfterViews
    public void afterViews() {
        initFragment(new FieldFragment_(), null);
    }

    @Override
    public void onBackPressed(){
        ((BaseFragment)currentFragment).initOnBackPressed();
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

    public void initFragment(Fragment fragment, Bundle bundle, FieldController fieldController,
                             RegistrationModelGUI model){
        super.initFragment(fragment, bundle);
        this.controller = fieldController;
        this.model = model;
    }

    public FieldController getController() {
        return controller;
    }

    public RegistrationModelGUI getModel(){
        return model;
    }

}
