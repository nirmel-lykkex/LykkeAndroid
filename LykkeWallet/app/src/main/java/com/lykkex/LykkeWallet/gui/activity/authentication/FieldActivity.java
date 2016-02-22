package com.lykkex.LykkeWallet.gui.activity.authentication;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.gui.fragments.BaseFragment;
import com.lykkex.LykkeWallet.gui.fragments.FieldFragment;
import com.lykkex.LykkeWallet.gui.fragments.FieldFragment_;
import com.lykkex.LykkeWallet.gui.fragments.models.KysStatusEnum;
import com.lykkex.LykkeWallet.gui.fragments.storage.UserPref_;
import com.lykkex.LykkeWallet.gui.utils.Constants;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

@EActivity(R.layout.activity_main)
public class FieldActivity extends ActionBarActivity {

    private Fragment currentFragment;


    public void initFragment(Fragment fragment, Bundle arg){
        currentFragment = fragment;
        currentFragment.setArguments(arg);
        ActionBar actionBar = getSupportActionBar();
        ((BaseFragment) currentFragment).setUpActionBar(actionBar);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, currentFragment).commit();
    }

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


}
