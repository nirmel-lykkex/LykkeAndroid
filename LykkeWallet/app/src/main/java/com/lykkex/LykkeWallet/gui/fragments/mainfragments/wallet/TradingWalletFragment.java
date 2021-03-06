package com.lykkex.LykkeWallet.gui.fragments.mainfragments.wallet;

import android.app.ProgressDialog;
import android.widget.ListView;

import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.gui.LykkeApplication_;
import com.lykkex.LykkeWallet.gui.activity.BaseActivity;
import com.lykkex.LykkeWallet.gui.adapters.HistoryAdapter;
import com.lykkex.LykkeWallet.gui.fragments.BaseFragment;
import com.lykkex.LykkeWallet.gui.fragments.storage.UserPref_;
import com.lykkex.LykkeWallet.gui.utils.Constants;
import com.lykkex.LykkeWallet.rest.history.callback.HistoryCallBack;
import com.lykkex.LykkeWallet.rest.history.reposnse.model.History;
import com.lykkex.LykkeWallet.rest.history.reposnse.model.HistoryData;
import com.lykkex.LykkeWallet.rest.wallet.response.models.AssetsWallet;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import retrofit2.Call;

/**
 * Created by LIZA on 18.04.2016.
 */
@EFragment(R.layout.trading_wallet_fragment)
public class TradingWalletFragment extends BaseFragment {

    private AssetsWallet assetsWallet;

    @Pref UserPref_ userPref;
    @ViewById ListView listView;
    private ProgressDialog dialog;
    private HistoryAdapter adapter;


    @AfterViews
    public void afterViews(){
        actionBar.show();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(R.string.trading_wallet);
        dialog = new ProgressDialog(getActivity());
        dialog.setMessage(getString(R.string.waiting));
        dialog.show();
        assetsWallet = (AssetsWallet) getArguments().getSerializable(Constants.EXTRA_ASSET);

        getHistory();

    }

    @Override
    public void initOnBackPressed() {
        getActivity().finish();
    }

    @Override
    public void onSuccess(Object result) {
        if (result instanceof History) {
            dialog.hide();
            adapter = new HistoryAdapter(((History) result).getList(), getActivity());
            listView.setAdapter(adapter);
        }
    }

    @Override
    public void onFail(Object error) {

    }

    @Override
    public void onConsume(Object o) {

    }

    private void getHistory(){
        HistoryCallBack callBack = new HistoryCallBack(this, getActivity());
        Call<HistoryData> call = LykkeApplication_.getInstance().getRestApi().getHistory
                (Constants.PART_AUTHORIZATION + userPref.authToken().get(),
                        "?assetId=" + assetsWallet.getAssetPairId());
        call.enqueue(callBack);
    }

    @Click(R.id.btnWithdraw)
    public void btnWithDraw(){
        ((BaseActivity)getActivity()).initFragment(new WithdrawFragment_(), getArguments());
    }

    @Click(R.id.btnDeposit)
    public void btnDeposit(){
        ((BaseActivity)getActivity()).initFragment(new QrCodeFragment_(), getArguments());
    }
}
