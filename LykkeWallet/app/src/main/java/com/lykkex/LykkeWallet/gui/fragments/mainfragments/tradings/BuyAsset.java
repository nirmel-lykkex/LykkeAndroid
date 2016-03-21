package com.lykkex.LykkeWallet.gui.fragments.mainfragments.tradings;

import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.gui.fragments.BaseFragment;
import com.lykkex.LykkeWallet.gui.utils.Constants;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;

/**
 * Created by e.kazimirova on 21.03.2016.
 */
@EFragment(R.layout.buy_asset)
public class BuyAsset  extends BaseFragment {

    @AfterViews
    public void afterViews(){
        actionBar.setTitle(getString(R.string.buy) + " " +
                getArguments().getString(Constants.EXTRA_ASSETPAIR_NAME));
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
