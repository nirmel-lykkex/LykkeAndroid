package com.lykkex.LykkeWallet.gui.activity;

import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.InputMethodManager;

import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.gui.fragments.BaseFragment;
import com.lykkex.LykkeWallet.gui.utils.Constants;

import org.androidannotations.annotations.EActivity;

/**
 * Created by LIZA on 23.02.2016.
 */
@EActivity(R.layout.activity_main)
public class BaseActivity  extends AppCompatActivity {

    protected android.app.Fragment currentFragment;

    public void initFragment(android.app.Fragment fragment, Bundle arg) {
        initFragment(fragment, arg, true);
    }

    public void initFragment(android.app.Fragment fragment, Bundle arg, boolean animate) {
        fragment.setArguments(arg);
        ActionBar actionBar = getSupportActionBar();

        if(fragment instanceof  BaseFragment) {
            ((BaseFragment) fragment).setUpActionBar(actionBar);
        }

        android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();

        transaction.replace(R.id.fragmentContainer, fragment);

        if(animate) {
            transaction.setCustomAnimations(R.animator.enter_anim, R.animator.exit_anim, R.animator.enter_anim_pop, R.animator.exit_anim_pop);
        }

        if(arg == null || !arg.containsKey(Constants.SKIP_BACKSTACK) || !arg.getBoolean(Constants.SKIP_BACKSTACK)) {
            transaction.addToBackStack(fragment.getClass().getName());
        }

        transaction.commit();

        currentFragment = fragment;
    }

    public void hideKeyboard(){
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);

        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
