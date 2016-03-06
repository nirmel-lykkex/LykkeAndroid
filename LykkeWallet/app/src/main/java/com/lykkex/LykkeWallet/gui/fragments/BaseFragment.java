package com.lykkex.LykkeWallet.gui.fragments;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.support.v7.app.ActionBar;

import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.gui.utils.Consume;
import com.lykkex.LykkeWallet.gui.utils.validation.CallBackListener;

import org.androidannotations.annotations.EFragment;

/**
 * Created by e.kazimirova on 09.02.2016.
 */
@EFragment(R.layout.auth_fragment)
public abstract  class BaseFragment<State> extends android.app.Fragment implements CallBackListener, Consume<State> {

    protected ActionBar actionBar;

    public void setUpActionBar(ActionBar actionBar){
        this.actionBar = actionBar;
    }

    public abstract void initOnBackPressed();

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser) {
            Activity a = getActivity();
            if(a != null) a.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }
}
