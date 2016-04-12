package com.lykkex.LykkeWallet.gui.fragments.mainfragments.history;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.gui.LykkeApplication_;
import com.lykkex.LykkeWallet.gui.fragments.BaseFragment;
import com.lykkex.LykkeWallet.gui.fragments.storage.UserPref_;
import com.lykkex.LykkeWallet.gui.utils.Constants;
import com.lykkex.LykkeWallet.rest.history.reposnse.model.CashInOut;
import com.lykkex.LykkeWallet.rest.history.reposnse.model.ItemHistory;
import com.lykkex.LykkeWallet.rest.history.reposnse.model.Trading;
import com.lykkex.LykkeWallet.rest.trading.callback.TransactionCallBack;
import com.lykkex.LykkeWallet.rest.trading.response.model.TransactionData;
import com.lykkex.LykkeWallet.rest.trading.response.model.TransactionResult;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.math.BigDecimal;

import retrofit2.Call;

/**
 * Created by LIZA on 12.04.2016.
 */
@EFragment(R.layout.blockchaing_history_fragment)
public class BlockChainHistoryFragment extends BaseFragment {

    @Pref UserPref_ userPref;
    private ItemHistory itemHistory;

    @ViewById LinearLayout linearBlockChainProgress;
    @ViewById TextView labelAmount;
    @ViewById LinearLayout linearAssetName;
    @ViewById TextView labelAssetName;
    @ViewById LinearLayout linearButton;
    @ViewById LinearLayout linearAmount;
    @ViewById TextView labelHash;
    @ViewById TextView labelDate;
    @ViewById TextView labelConfirm;
    @ViewById TextView labelBlock;
    @ViewById TextView labelHeight;
    @ViewById TextView labelSender;
    @ViewById TextView labelAsset;
    @ViewById TextView labelQuantity;
    @ViewById TextView labelUrl;

    @ViewById LinearLayout linearHash;
    @ViewById LinearLayout linearDate;
    @ViewById LinearLayout linearConfirm;
    @ViewById LinearLayout linearBlock;
    @ViewById LinearLayout linearHeight;
    @ViewById LinearLayout linearSender;
    @ViewById LinearLayout linearAsset;
    @ViewById LinearLayout linearQuantity;
    @ViewById LinearLayout linearUrl;


    @AfterViews
    public void afterViews(){
        itemHistory = (ItemHistory) getArguments().getSerializable(Constants.EXTRA_HISTORY_ITEM);
        hideAllInfo();

        getTransaction();
    }

    private void getTransaction(){
        if (itemHistory instanceof CashInOut) {
            if (new BigDecimal(((CashInOut) itemHistory).getAmount()).compareTo(BigDecimal.ZERO) > 1){
                actionBar.setTitle(itemHistory.getAsset() + " " + getString(R.string.cash_in));
            } else {
                actionBar.setTitle(itemHistory.getAsset() + " " + getString(R.string.cash_out));
            }
            TransactionCallBack callback = new TransactionCallBack(this, getActivity());
            Call<TransactionData> call = LykkeApplication_.getInstance().getRestApi().
                    getBcnTransactionByCashOperation(Constants.PART_AUTHORIZATION + userPref.authToken().get(),
                            "?id=" + itemHistory.getId());
            call.enqueue(callback);
        } else {
            if (new BigDecimal(((Trading) itemHistory).getVolume()).compareTo(BigDecimal.ZERO) > 1){
                actionBar.setTitle(itemHistory.getAsset() + " " + getString(R.string.exchange_in));
            } else {
                actionBar.setTitle(itemHistory.getAsset() + " " + getString(R.string.exchange_out));
            }
            TransactionCallBack callback = new TransactionCallBack(this, getActivity());
            Call<TransactionData> call = LykkeApplication_.getInstance().getRestApi().
                    getBcnTransactionByExchange(Constants.PART_AUTHORIZATION + userPref.authToken().get(),
                            "?id=" + itemHistory.getId());
            call.enqueue(callback);
        }
    }

    @Override
    public void initOnBackPressed() {

    }

    @Click(R.id.btnClose)
    public void btnClose(){
        getActivity().finish();
    }

    @Override
    public void onSuccess(Object result) {
        if (result instanceof TransactionResult) {
            if (((TransactionResult) result).getTransaction() != null) {
                linearAmount.setVisibility(View.GONE);
                linearAssetName.setVisibility(View.GONE);
                linearBlockChainProgress.setVisibility(View.GONE);
                if (((TransactionResult) result).getTransaction().getHash() == null ||
                        ((TransactionResult) result).getTransaction().getHash().isEmpty()) {
                    linearHash.setVisibility(View.GONE);
                } else {
                    linearHash.setVisibility(View.VISIBLE);
                    labelHash.setText(((TransactionResult) result).getTransaction().getHash());
                }

                if (((TransactionResult) result).getTransaction().getDate() == null ||
                        ((TransactionResult) result).getTransaction().getDate().isEmpty()) {
                    linearDate.setVisibility(View.GONE);
                } else {
                    linearDate.setVisibility(View.VISIBLE);
                    labelDate.setText(((TransactionResult) result).getTransaction().getDate());
                }

                if (((TransactionResult) result).getTransaction().getConfirmations() == null ||
                        ((TransactionResult) result).getTransaction().getConfirmations().isEmpty()) {
                    linearConfirm.setVisibility(View.GONE);
                } else {
                    linearConfirm.setVisibility(View.VISIBLE);
                    labelConfirm.setText(((TransactionResult) result).getTransaction().getConfirmations());
                }

                if (((TransactionResult) result).getTransaction().getAssetId() == null ||
                        ((TransactionResult) result).getTransaction().getAssetId().isEmpty()) {
                    linearAsset.setVisibility(View.GONE);
                } else {
                    linearAsset.setVisibility(View.VISIBLE);
                    labelAsset.setTextColor(getActivity().getResources().getColor(R.color.blue_color));
                    labelAsset.setText(((TransactionResult) result).getTransaction().getAssetId());
                }

                if (((TransactionResult) result).getTransaction().getSenderId() == null ||
                        ((TransactionResult) result).getTransaction().getSenderId().isEmpty()) {
                    linearSender.setVisibility(View.GONE);
                } else {
                    linearSender.setVisibility(View.VISIBLE);
                    labelSender.setTextColor(getActivity().getResources().getColor(R.color.blue_color));
                    labelSender.setText(((TransactionResult) result).getTransaction().getSenderId());
                }

                if (((TransactionResult) result).getTransaction().getBlock() == null ||
                        ((TransactionResult) result).getTransaction().getBlock().isEmpty()) {
                    linearBlock.setVisibility(View.GONE);
                } else {
                    linearBlock.setVisibility(View.VISIBLE);
                    labelBlock.setText(((TransactionResult) result).getTransaction().getBlock());
                }

                if (((TransactionResult) result).getTransaction().getHeight() == null ||
                        ((TransactionResult) result).getTransaction().getHeight().isEmpty()) {
                    linearHeight.setVisibility(View.GONE);
                } else {
                    linearHeight.setVisibility(View.VISIBLE);
                    labelHeight.setText(((TransactionResult) result).getTransaction().getHeight());
                }

                if (((TransactionResult) result).getTransaction().getQuality() == null ||
                        ((TransactionResult) result).getTransaction().getQuality().isEmpty()) {
                    linearQuantity.setVisibility(View.GONE);
                } else {
                    linearQuantity.setVisibility(View.VISIBLE);
                    labelQuantity.setText(((TransactionResult) result).getTransaction().getQuality());
                }

                if (((TransactionResult) result).getTransaction().getUrl() == null ||
                        ((TransactionResult) result).getTransaction().getUrl().isEmpty()) {
                    linearUrl.setVisibility(View.GONE);
                } else {
                    linearUrl.setVisibility(View.VISIBLE);
                    labelUrl.setTextColor(getActivity().getResources().getColor(R.color.blue_color));
                    labelUrl.setText(((TransactionResult) result).getTransaction().getUrl());
                }
            } else {
                hideAllInfo();
                initViewData();
            }
        }
    }

    private void initViewData(){
        linearAmount.setVisibility(View.VISIBLE);
        linearBlockChainProgress.setVisibility(View.VISIBLE);
        linearAssetName.setVisibility(View.VISIBLE);
        linearButton.setVisibility(View.GONE);
        if (itemHistory instanceof CashInOut) {
            labelAmount.setText(((CashInOut)itemHistory).getAmount());
        } else {
            labelAmount.setText(((Trading)itemHistory).getVolume());
        }

        labelAssetName.setText(itemHistory.getAsset());

    }

    private void hideAllInfo(){
        linearAmount.setVisibility(View.GONE);
        linearAssetName.setVisibility(View.GONE);
        linearBlockChainProgress.setVisibility(View.GONE);
        linearHash.setVisibility(View.GONE);
        linearDate.setVisibility(View.GONE);
        linearConfirm.setVisibility(View.GONE);
        linearBlock.setVisibility(View.GONE);
        linearHeight.setVisibility(View.GONE);
        linearSender.setVisibility(View.GONE);
        linearAsset.setVisibility(View.GONE);
        linearQuantity.setVisibility(View.GONE);
        linearUrl.setVisibility(View.GONE);
    }

    @Override
    public void onFail(Object error) {

    }

    @Override
    public void onConsume(Object o) {

    }
}
