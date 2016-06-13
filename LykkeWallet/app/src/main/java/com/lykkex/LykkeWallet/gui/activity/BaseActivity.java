package com.lykkex.LykkeWallet.gui.activity;

import android.app.FragmentManager;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.gui.LykkeApplication;
import com.lykkex.LykkeWallet.gui.fragments.BaseFragment;
import com.lykkex.LykkeWallet.gui.utils.Constants;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.EActivity;

/**
 * Created by LIZA on 23.02.2016.
 */
@EActivity(R.layout.activity_main)
public class BaseActivity  extends AppCompatActivity {

    protected BaseFragment currentFragment;

    public void initFragment(BaseFragment fragment, Bundle arg) {
        initFragment(fragment, arg, false);
    }

    public void initFragment(BaseFragment fragment, Bundle arg, boolean skipAnimation) {
        fragment.setArguments(arg);
        ActionBar actionBar = getSupportActionBar();

        fragment.setUpActionBar(actionBar);

        android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();

        if(!skipAnimation) {
            if(arg != null && arg.getBoolean(Constants.VERTICAL_ANIMATION, false)) {
                transaction.setCustomAnimations(R.animator.enter_anim_y, R.animator.exit_anim_y, R.animator.enter_anim_pop_y, R.animator.exit_anim_pop_y);
            } else {
                transaction.setCustomAnimations(R.animator.enter_anim, R.animator.exit_anim, R.animator.enter_anim_pop, R.animator.exit_anim_pop);
            }
        }

        transaction.replace(R.id.fragmentContainer, fragment, fragment.getClass().getSimpleName());

        if(currentFragment != null && (arg == null || !arg.containsKey(Constants.SKIP_BACKSTACK) || !arg.getBoolean(Constants.SKIP_BACKSTACK))) {
            transaction.addToBackStack(fragment.getClass().getSimpleName());
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

    public void hideSoftKeyboard(View v){
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    @Override
    public void onBackPressed() {
        if(currentFragment != null) {
            currentFragment.onBackPressed();
        }

        super.onBackPressed();

        if(getFragmentManager().getBackStackEntryCount() > 0) {
            String fragmentName = getFragmentManager().getBackStackEntryAt(getFragmentManager().getBackStackEntryCount() - 1).getName();

            android.app.Fragment fragment = getFragmentManager().findFragmentByTag(fragmentName);

            if(fragment instanceof BaseFragment) {
                currentFragment = (BaseFragment) fragment;
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

        View view = findViewById(android.R.id.content);

        if(view != null && view instanceof ViewGroup) {
            hideSoftKeyFromViewGroup((ViewGroup) view);
        }
    }

    public void hideSoftKeyFromViewGroup(ViewGroup viewGroup) {
        for(int i = 0; i < viewGroup.getChildCount(); i++) {
            View child = viewGroup.getChildAt(i);

            if(child instanceof EditText) {
                child.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if(! hasFocus) {
                            hideSoftKeyboard(v);
                        }
                    }
                });
            }

            if(child instanceof ViewGroup) {
                hideSoftKeyFromViewGroup((ViewGroup) child);
            }
        }
    }
}
