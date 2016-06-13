package com.lykkex.LykkeWallet.gui.activity;

import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.gui.fragments.mainfragments.history.BlockChainHistoryFragment_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

/**
 * Created by LIZA on 12.04.2016.
 */
@EActivity(R.layout.activity_main)
public class HistoryActivity extends BaseActivity {

    @AfterViews
    public void afterViews(){
        initFragment(new BlockChainHistoryFragment_(), getIntent().getExtras());
    }
}
