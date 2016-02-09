package com.lykkex.LykkeWallet;

import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.lykkex.LykkeWallet.gui.fragments.FieldFragment;
import com.lykkex.LykkeWallet.gui.fragments.FieldFragment_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_main)
public class FieldActivity extends FragmentActivity {


    @AfterViews
    public void afterViews(){
        FieldFragment_ fragment = new FieldFragment_();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragmentContainer, fragment).commit();
    }


}
