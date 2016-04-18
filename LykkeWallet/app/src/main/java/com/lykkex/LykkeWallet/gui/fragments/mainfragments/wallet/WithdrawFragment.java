package com.lykkex.LykkeWallet.gui.fragments.mainfragments.wallet;

import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.gui.activity.BaseActivity;
import com.lykkex.LykkeWallet.gui.fragments.BaseFragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;

/**
 * Created by LIZA on 18.04.2016.
 */
@EFragment(R.layout.withdraw_fragment)
public class WithdrawFragment extends BaseFragment {

    @AfterViews
    public void afterViews(){
        actionBar.show();
        actionBar.setTitle(R.string.withdraw_funds);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }

    @Override
    public void initOnBackPressed() {
        ((BaseActivity)getActivity()).initFragment(new TradingWalletFragment_(), getArguments());
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
