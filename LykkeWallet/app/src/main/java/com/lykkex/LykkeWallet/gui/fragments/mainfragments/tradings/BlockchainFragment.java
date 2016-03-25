package com.lykkex.LykkeWallet.gui.fragments.mainfragments.tradings;

import android.view.View;
import android.widget.TextView;

import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.gui.LykkeApplication_;
import com.lykkex.LykkeWallet.gui.fragments.BaseFragment;
import com.lykkex.LykkeWallet.gui.fragments.storage.UserPref_;
import com.lykkex.LykkeWallet.gui.utils.Constants;
import com.lykkex.LykkeWallet.rest.trading.callback.TransactionCallBack;
import com.lykkex.LykkeWallet.rest.trading.response.model.Order;
import com.lykkex.LykkeWallet.rest.trading.response.model.TransactionData;
import com.lykkex.LykkeWallet.rest.trading.response.model.TransactionResult;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import retrofit2.Call;

/**
 * Created by e.kazimirova on 25.03.2016.
 */
@EFragment(R.layout.blockchaing_fragment)
public class BlockchainFragment extends BaseFragment {

    @Pref  UserPref_ userPref;
    @ViewById TextView labelHash;
    @ViewById TextView labelDate;
    @ViewById TextView labelConfirm;
    @ViewById TextView labelBlock;
    @ViewById TextView labelHeight;
    @ViewById TextView labelSender;
    @ViewById TextView labelAsset;
    @ViewById TextView labelQuantity;
    @ViewById TextView labelUrl;

    @AfterViews
    public void afterViews(){
        actionBar.setDisplayHomeAsUpEnabled(false);
        Order order = (Order) getArguments().getSerializable(Constants.EXTRA_ORDER);
        getTransaction(order.getId());
    }

    private void getTransaction(String id){
        TransactionCallBack callback = new TransactionCallBack(this, getActivity());
        Call<TransactionData> call  = LykkeApplication_.getInstance().getRestApi().
                getBlockChainTransaction(Constants.PART_AUTHORIZATION + userPref.authToken().get(),
                        "?orderId="+id);
        call.enqueue(callback);
    }

    @Click(R.id.btnClose)
    public void clickClose(){
        initOnBackPressed();
    }

    @Override
    public void initOnBackPressed() {
        getActivity().finish();
    }

    @Override
    public void onSuccess(Object result) {
        if (result instanceof TransactionResult) {
            if (((TransactionResult) result).getTransaction() != null) {
                if (((TransactionResult) result).getTransaction().getHash() == null ||
                        ((TransactionResult) result).getTransaction().getHash().isEmpty()) {
                    labelHash.setVisibility(View.GONE);
                } else {
                    labelHash.setText(((TransactionResult) result).getTransaction().getHash());
                }

                if (((TransactionResult) result).getTransaction().getDate() == null ||
                        ((TransactionResult) result).getTransaction().getDate().isEmpty()) {
                    labelDate.setVisibility(View.GONE);
                } else {
                    labelDate.setText(((TransactionResult) result).getTransaction().getDate());
                }

                if (((TransactionResult) result).getTransaction().getConfirmations() == null ||
                        ((TransactionResult) result).getTransaction().getConfirmations().isEmpty()) {
                    labelConfirm.setVisibility(View.GONE);
                } else {
                    labelConfirm.setText(((TransactionResult) result).getTransaction().getConfirmations());
                }

                if (((TransactionResult) result).getTransaction().getAssetId() == null ||
                        ((TransactionResult) result).getTransaction().getAssetId().isEmpty()) {
                    labelAsset.setVisibility(View.GONE);
                } else {
                    labelAsset.setTextColor(getActivity().getResources().getColor(R.color.blue_color));
                    labelAsset.setText(((TransactionResult) result).getTransaction().getAssetId());
                }

                if (((TransactionResult) result).getTransaction().getSenderId() == null ||
                        ((TransactionResult) result).getTransaction().getSenderId().isEmpty()) {
                    labelSender.setVisibility(View.GONE);
                } else {
                    labelSender.setTextColor(getActivity().getResources().getColor(R.color.blue_color));
                    labelSender.setText(((TransactionResult) result).getTransaction().getSenderId());
                }

                if (((TransactionResult) result).getTransaction().getBlock() == null ||
                        ((TransactionResult) result).getTransaction().getBlock().isEmpty()) {
                    labelBlock.setVisibility(View.GONE);
                } else {
                    labelBlock.setText(((TransactionResult) result).getTransaction().getBlock());
                }

                if (((TransactionResult) result).getTransaction().getHeight() == null ||
                        ((TransactionResult) result).getTransaction().getHeight().isEmpty()) {
                    labelHeight.setVisibility(View.GONE);
                } else {
                    labelHeight.setText(((TransactionResult) result).getTransaction().getHeight());
                }

                if (((TransactionResult) result).getTransaction().getQuality() == null ||
                        ((TransactionResult) result).getTransaction().getQuality().isEmpty()) {
                    labelQuantity.setVisibility(View.GONE);
                } else {
                    labelQuantity.setText(((TransactionResult) result).getTransaction().getQuality());
                }

                if (((TransactionResult) result).getTransaction().getUrl() == null ||
                        ((TransactionResult) result).getTransaction().getUrl().isEmpty()) {
                    labelUrl.setVisibility(View.GONE);
                } else {
                    labelUrl.setTextColor(getActivity().getResources().getColor(R.color.blue_color));
                    labelUrl.setText(((TransactionResult) result).getTransaction().getUrl());
                }
            }
        }
    }

    @Override
    public void onFail(Object error) {

    }

    @Override
    public void onConsume(Object o) {

    }
}