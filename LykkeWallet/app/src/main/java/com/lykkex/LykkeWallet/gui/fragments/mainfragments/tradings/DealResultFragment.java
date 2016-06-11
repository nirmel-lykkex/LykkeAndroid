package com.lykkex.LykkeWallet.gui.fragments.mainfragments.tradings;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.gui.LykkeApplication_;
import com.lykkex.LykkeWallet.gui.activity.BaseActivity;
import com.lykkex.LykkeWallet.gui.fragments.BaseFragment;
import com.lykkex.LykkeWallet.gui.fragments.storage.UserPref_;
import com.lykkex.LykkeWallet.gui.utils.Constants;
import com.lykkex.LykkeWallet.gui.utils.validation.CallBackListener;
import com.lykkex.LykkeWallet.rest.trading.callback.PurchaseAssetCallBack;
import com.lykkex.LykkeWallet.rest.trading.response.model.Order;
import com.lykkex.LykkeWallet.rest.trading.response.model.OrderData;
import com.lykkex.LykkeWallet.rest.trading.response.model.OrderResult;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.math.BigDecimal;
import java.math.RoundingMode;

import retrofit2.Call;

/**
 * Created by LIZA on 24.03.2016.
 */
@EFragment(R.layout.deal_fragment)
public class DealResultFragment extends BaseFragment implements
        SwipeRefreshLayout.OnRefreshListener, CallBackListener {

    @Pref UserPref_ userPref;
    @ViewById TextView labelAsset;
    @ViewById TextView labelUnits;
    @ViewById TextView labelPrice;
    @ViewById TextView labelComission;
    @ViewById TextView labelCost;
    @ViewById TextView labelBlockChain;
    @ViewById TextView labelBlockChainInProgress;
    @ViewById TextView labelPosition;

    @ViewById LinearLayout linearAssetName;
    @ViewById LinearLayout linearUnits;
    @ViewById LinearLayout linearPrice;
    @ViewById LinearLayout linearComission;
    @ViewById LinearLayout linearCost;
    @ViewById LinearLayout linearBlockChain;
    @ViewById LinearLayout linearBlockChainProgress;
    @ViewById LinearLayout linearPosition;

    private int accurancy;

    @ViewById
    SwipeRefreshLayout swipeRefresh;

    private Order order;
    @AfterViews
    public void afterViews(){
        order = (Order) getArguments().getSerializable(Constants.EXTRA_ORDER);
        actionBar.setDisplayHomeAsUpEnabled(false);
        swipeRefresh.setOnRefreshListener(this);
        swipeRefresh.setRefreshing(false);
        actionBar.setTitle(getArguments().getString(Constants.EXTRA_ASSETPAIR_NAME)
                + " " + getString(R.string.deal_result));
        if (order.getAssetPair()== null || order.getAssetPair().isEmpty()){
            linearAssetName.setVisibility(View.GONE);
        } else {
            labelAsset.setText(order.getAssetPair());
        }

        if (order.getVolume()== null || order.getVolume().isEmpty()){
            linearUnits.setVisibility(View.GONE);
        } else {
            labelUnits.setText(String.valueOf(new BigDecimal(order.getVolume()).intValue()));
        }

        if (order.getPrice()== null || order.getPrice().isEmpty()){
            linearPrice.setVisibility(View.GONE);
        } else {
            labelPrice.setText(new BigDecimal(order.getPrice()).setScale(getArguments().
                    getInt(Constants.EXTRA_ASSETPAIR_ACCURANCY), RoundingMode.HALF_EVEN).toString());
        }

        if (order.getComission()== null || order.getComission().isEmpty()){
            linearComission.setVisibility(View.GONE);
        } else {
            labelComission.setText(new BigDecimal(order.getComission())
                    .setScale(Constants.DEFAULT_PRECISION).toString());
        }


        if (order.getTotalCost() == null || order.getTotalCost().isEmpty()) {
            linearCost.setVisibility(View.GONE);
        } else {
            // TODO: Check error here
            labelCost.setText(new BigDecimal(order.getTotalCost()).
                    setScale(Constants.DEFAULT_PRECISION).toString());
        }

        if (Boolean.parseBoolean(order.getBlockchainSetteled())) {
            if (order.getBlockchainId() == null || order.getBlockchainId().isEmpty()) {
                linearBlockChain.setVisibility(View.GONE);
            } else {
                labelBlockChain.setText(order.getBlockchainId());
            }
        } else {
            labelBlockChain.setTextColor(getResources().getColor(R.color.grey_dark_text));
            labelBlockChain.setText(R.string.purchace_blockchain_progress);
        }

        if (order.getPosition()== null || order.getPosition().isEmpty()){
            linearPosition.setVisibility(View.GONE);
        } else {
            labelPosition.setText(order.getPosition());
        }

    }

    @Click(R.id.btnClose)
    public void btnClose(){
        getActivity().finish();
    }

    @Click(R.id.linearBlockChain)
    public void clickLinearBlockChain(){
        if (Boolean.parseBoolean(order.getBlockchainSetteled())) {
            Bundle bundle = new Bundle();
            bundle.putSerializable(Constants.EXTRA_ORDER, order);
            ((BaseActivity) getActivity()).initFragment(new BlockchainFragment_(), bundle);
       }
    }

    @Click(R.id.tvBlockChain)
    public void clickBlockChain(){
        clickLinearBlockChain();
    }

    @Click(R.id.labelBlockChain)
    public void clickBlockTvChain(){
        clickLinearBlockChain();
    }

    @Click(R.id.rel2BlockChain)
    public void clickrel2BlockChain(){
        clickLinearBlockChain();
    }

    @Click(R.id.rel1BlockChain)
    public void clickrel1BlockChain(){
        clickLinearBlockChain();
    }

    @Override
    public void onSuccess(Object result) {
        if (result instanceof OrderResult) {
            afterViews();
        }
    }

    @Override
    public void onFail(Object error) {

    }

    private void refreshContent(){
        PurchaseAssetCallBack callback = new PurchaseAssetCallBack(this, getActivity());
        Call<OrderData> call  = LykkeApplication_.getInstance().getRestApi().
                getMarketOrder(Constants.PART_AUTHORIZATION + userPref.authToken().get(),
                        order.getId());
        call.enqueue(callback);
    }

    @Override
    public void onRefresh() {
        refreshContent();
    }
}
