package com.lykkex.LykkeWallet.gui.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.inputmethod.InputMethodManager;

import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.gui.fragments.BaseFragment;

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
        if (currentFragment != null) {
            getFragmentManager().beginTransaction().remove(currentFragment);
        }
        currentFragment = fragment;
        currentFragment.setArguments(arg);
        ActionBar actionBar = getSupportActionBar();

        if(currentFragment instanceof  BaseFragment) {
            ((BaseFragment) currentFragment).setUpActionBar(actionBar);
        }

        android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();

        if(animate) {
            transaction.setCustomAnimations(R.animator.enter_anim, R.animator.exit_anim, R.animator.enter_anim_pop, R.animator.exit_anim_pop);
        }

        if(arg != null && arg.containsKey("replace") && arg.getBoolean("replace")) {
            transaction.replace(R.id.fragmentContainer, currentFragment);
        } else {
            transaction.add(R.id.fragmentContainer, currentFragment).addToBackStack(fragment.getClass().getName());
        }

        transaction.commit();
    }

    public void hideKeyboard(){
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);

        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
