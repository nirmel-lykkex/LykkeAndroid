package com.lykkex.LykkeWallet;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import com.lykkex.LykkeWallet.gui.fragments.FieldFragment_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_main)
public class FieldActivity extends FragmentActivity {

    private Fragment currentFragment;

    @AfterViews
    public void afterViews(){
        currentFragment = new FieldFragment_();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragmentContainer, currentFragment).commit();
    }

    @Override
    public void onBackPressed(){
        if (currentFragment instanceof FieldFragment_) {
            ((FieldFragment_)currentFragment).initOnBackPressed();
        }
    }


}
