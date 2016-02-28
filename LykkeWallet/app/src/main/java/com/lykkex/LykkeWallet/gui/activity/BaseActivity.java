package com.lykkex.LykkeWallet.gui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;

import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.gui.fragments.BaseFragment;

import org.androidannotations.annotations.EActivity;

/**
 * Created by LIZA on 23.02.2016.
 */
@EActivity(R.layout.activity_main)
public class BaseActivity  extends ActionBarActivity {

    protected android.app.Fragment currentFragment;

    public void initFragment(android.app.Fragment fragment, Bundle arg) {
        if (currentFragment != null) {
            getFragmentManager().beginTransaction().remove(currentFragment);
        }
        currentFragment = fragment;
        currentFragment.setArguments(arg);
        ActionBar actionBar = getSupportActionBar();
        ((BaseFragment) currentFragment).setUpActionBar(actionBar);
        android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_anim, R.anim.exit_anim);
        transaction
                .replace(R.id.fragmentContainer, currentFragment);
        transaction.commit();
    }

    public void initFragmentWithoutAnim(android.app.Fragment fragment, Bundle arg) {
        if (currentFragment != null) {
            getFragmentManager().beginTransaction().remove(currentFragment);
        }
        currentFragment = fragment;
        currentFragment.setArguments(arg);
        ActionBar actionBar = getSupportActionBar();
        ((BaseFragment) currentFragment).setUpActionBar(actionBar);
        android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction
                .replace(R.id.fragmentContainer, currentFragment);
        transaction.commit();
    }
}
