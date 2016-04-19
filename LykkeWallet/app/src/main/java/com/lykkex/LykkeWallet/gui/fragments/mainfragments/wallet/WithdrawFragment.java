package com.lykkex.LykkeWallet.gui.fragments.mainfragments.wallet;

import android.widget.Button;
import android.widget.EditText;

import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.gui.activity.BaseActivity;
import com.lykkex.LykkeWallet.gui.fragments.BaseFragment;
import com.lykkex.LykkeWallet.gui.utils.Constants;
import com.lykkex.LykkeWallet.gui.utils.validation.SimpleTextWatcher;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

/**
 * Created by LIZA on 18.04.2016.
 */
@EFragment(R.layout.withdraw_fragment)
public class WithdrawFragment extends BaseFragment {

    @ViewById EditText etHashBitcoin;
    @ViewById Button btnProceed;

    @AfterViews
    public void afterViews(){
        actionBar.show();
        actionBar.setTitle(R.string.withdraw_funds);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        btnProceed.addTextChangedListener(new SimpleTextWatcher(null,
                null, etHashBitcoin,
        Constants.MIN_COUNT_SYMBOL, this,
                null));
    }

    @Override
    public void initOnBackPressed() {
        ((BaseActivity)getActivity()).initFragment(new TradingWalletFragment_(), getArguments());
    }

    @Click(R.id.btnScan)
    public void clickBtnScan(){
        ((BaseActivity)getActivity()).initFragment(new QrCodeScanFragment_(), getArguments());
    }

    @Click(R.id.btnProceed)
    public void clickBtnProceed(){

    }

    @Override
    public void onSuccess(Object result) {
        if (result == null) {
            btnProceed.setEnabled(true);
        }
    }

    @Override
    public void onFail(Object error) {
        if (error == null) {
            btnProceed.setEnabled(false);
        }
    }

    @Override
    public void onConsume(Object o) {

    }
}
