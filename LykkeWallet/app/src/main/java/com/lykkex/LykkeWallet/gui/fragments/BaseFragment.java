package com.lykkex.LykkeWallet.gui.fragments;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;

import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.gui.utils.Consume;
import com.lykkex.LykkeWallet.gui.utils.validation.CallBackListener;

import org.androidannotations.annotations.EFragment;

/**
 * Created by e.kazimirova on 09.02.2016.
 */
public abstract class BaseFragment extends android.app.Fragment {

    protected ActionBar actionBar;

    public void setUpActionBar(ActionBar actionBar){
        this.actionBar = actionBar;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser) {
            Activity a = getActivity();
            if(a != null) a.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    public void onBackPressed() {
    }
}
