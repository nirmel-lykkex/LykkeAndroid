package com.lykkex.LykkeWallet.gui.fragments.mainfragments.setting;

import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.gui.fragments.BaseFragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;

/**
 * Created by LIZA on 15.03.2016.
 */
@EFragment(R.layout.push_fragment)
public class PushFragment extends BaseFragment {

    @AfterViews
    public void afterViews(){
        actionBar.setTitle(R.string.push_title);
    }
    @Override
    public void initOnBackPressed() {
        getActivity().finish();
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
