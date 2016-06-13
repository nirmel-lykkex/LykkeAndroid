package com.lykkex.LykkeWallet.gui.fragments;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.view.ViewGroup;
import com.lykkex.LykkeWallet.gui.activity.BaseActivity;

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

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(view != null && view instanceof ViewGroup && getActivity() != null && getActivity() instanceof BaseActivity) {
            ((BaseActivity)getActivity()).hideSoftKeyFromViewGroup((ViewGroup) view);
        }
    }
}
