package com.lykkex.LykkeWallet.gui.fragments.mainfragments.tradings;

import android.view.View;
import android.widget.RelativeLayout;

import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.gui.fragments.BaseFragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

/**
 * Created by e.kazimirova on 18.03.2016.
 */
@EFragment(R.layout.trading_description_fragment)
public class TradingDescription  extends BaseFragment {

    @ViewById  RelativeLayout relProgress;

    @AfterViews
    public void afterViews(){
        relProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void initOnBackPressed() {
        getActivity().finish();
    }

    @Override
    public void onSuccess(Object result) {
        relProgress.setVisibility(View.GONE);
    }

    @Override
    public void onFail(Object error) {

    }

    @Override
    public void onConsume(Object o) {

    }
}
