package com.lykkex.LykkeWallet.gui.fragments.addcard;

import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.gui.fragments.BaseFragment;
import com.lykkex.LykkeWallet.rest.base.models.Error;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;

/**
 * Created by LIZA on 06.03.2016.
 */
@EFragment(R.layout.card_add_success)
public class CardAddSuccess extends BaseFragment {

    @AfterViews
    public void afterViews(){
        actionBar.hide();
        actionBar.hide();
    }

    @Click(R.id.btnStart)
    public void clickStart(){
        getActivity().finish();
    }
}
