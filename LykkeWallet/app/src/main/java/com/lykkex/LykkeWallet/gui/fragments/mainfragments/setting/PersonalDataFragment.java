package com.lykkex.LykkeWallet.gui.fragments.mainfragments.setting;

import android.support.v4.app.Fragment;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.gui.fragments.BaseFragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

/**
 * Created by LIZA on 29.02.2016.
 */
@EFragment(R.layout.personal_data_fragment)
public class PersonalDataFragment extends BaseFragment {

    @AfterViews
    public void afterViews(){
        actionBar.setTitle(R.string.personal_data_title);
    }

    @Override
    public void initOnBackPressed() {

    }

    @Override
    public void onSuccess(Object result) {

    }

    @Override
    public void onFail(Object error) {

    }

    @Override
    public void onConsume(Object o) {

    }
}
