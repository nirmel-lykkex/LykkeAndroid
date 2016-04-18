package com.lykkex.LykkeWallet.gui.fragments.mainfragments.history;

import android.app.ProgressDialog;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.gui.LykkeApplication_;
import com.lykkex.LykkeWallet.gui.fragments.BaseFragment;
import com.lykkex.LykkeWallet.gui.fragments.storage.UserPref_;
import com.lykkex.LykkeWallet.gui.utils.Constants;
import com.lykkex.LykkeWallet.rest.history.callback.MarketOrderCallBack;
import com.lykkex.LykkeWallet.rest.history.reposnse.model.CashInOut;
import com.lykkex.LykkeWallet.rest.history.reposnse.model.ItemHistory;
import com.lykkex.LykkeWallet.rest.history.reposnse.model.MarketData;
import com.lykkex.LykkeWallet.rest.history.reposnse.model.MarketResult;
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
public class BlockChainHistoryFragment extends BaseFragment implements   SwipeRefreshLayout.OnRefreshListener{


    @ViewById
    SwipeRefreshLayout swipeRefresh;
    @Pref UserPref_ userPref;
    private ItemHistory itemHistory;

    @ViewById TextView tvTitleMain;
    @ViewById TextView tvTitle;

    @ViewById RelativeLayout tvTitle2;
    @ViewById RelativeLayout relImage;
    @ViewById LinearLayout linearInfo;
    @ViewById TextView labelHash;
    @ViewById TextView labelDate;
    @ViewById TextView labelConfirm;
    @ViewById TextView labelBlock;
    @ViewById TextView labelHeight;
    @ViewById TextView labelSender;
    @ViewById TextView labelAsset;
    @ViewById TextView labelQuantity;
    @ViewById TextView labelUrl;
    @ViewById View viewHash;
    @ViewById View viewDate;
    @ViewById View viewConfirm;
    @ViewById View viewBlock;
    @ViewById View viewHeight;
    @ViewById View viewSender;
    @ViewById View viewAsset;
    @ViewById View viewQuatity;
    @ViewById View viewUrl;
    @ViewById View viewBlockChainInProgress;
    @ViewById View viewAssetName;
    @ViewById View viewAmount;

    @ViewById LinearLayout linearHash;
    @ViewById LinearLayout linearDate;
    @ViewById LinearLayout linearConfirm;
    @ViewById LinearLayout linearBlock;
    @ViewById LinearLayout linearHeight;
    @ViewById LinearLayout linearSender;
    @ViewById LinearLayout linearAsset;
    @ViewById LinearLayout linearQuantity;
    @ViewById LinearLayout linearUrl;
    @ViewById ScrollView scrollViewParent;
    @ViewById TextView labelAmount;
    @ViewById LinearLayout linearAssetName;
    @ViewById LinearLayout linearBlockChainProgress;
    @ViewById TextView labelAssetName;
    @ViewById LinearLayout linearButton;
    @ViewById LinearLayout linearAmount;
    @ViewById ScrollView scrollViewParent2;
    @ViewById LinearLayout linearAssetTrading;
    @ViewById TextView labelAssetTrading;
    @ViewById View viewAssetTrading;
    @ViewById LinearLayout linearUnitPurchaesed;
    @ViewById TextView labelUnitPurchaesed;
    @ViewById View viewUnitPurchaesed;
    @ViewById LinearLayout linearExecutionPrice;
    @ViewById TextView labelExecutionPrice;
    @ViewById View viewExecutionPrice;
    @ViewById LinearLayout linearComissionPaid;
    @ViewById TextView labelComissionPaid;
    @ViewById View viewComissionPaid;
    @ViewById LinearLayout linearTotalCost;
    @ViewById TextView labelTotalCost;
    @ViewById View viewTotalCost;
    @ViewById LinearLayout linearBlockChainSettlement;
    @ViewById TextView labelBlockChainSettlement;
    @ViewById View viewBlockChainSettlement;
    @ViewById LinearLayout linearPosition;
    @ViewById TextView labelPosition;
    @ViewById View viewPosition;

    private int prevScroll = 0;
    private ProgressDialog dialog;
    private boolean isMarketOrder = false;
    private ScrollChangeListener listener = new ScrollChangeListener();

    @AfterViews
    public void afterViews(){
        dialog = new ProgressDialog(getActivity());
        dialog.setMessage(getString(R.string.waiting));
        dialog.show();
        scrollViewParent.setVisibility(View.GONE);
        scrollViewParent2.setVisibility(View.GONE);
        itemHistory = (ItemHistory) getArguments().getSerializable(Constants.EXTRA_HISTORY_ITEM);
        hideAllInfo();


        scrollViewParent.getViewTreeObserver().addOnScrollChangedListener(listener);
        getTransaction();
    }

    private class ScrollChangeListener implements ViewTreeObserver.OnScrollChangedListener {
        @Override
        public void onScrollChanged() {
            int scrollY = scrollViewParent.getScrollY();
            int defaultHeight = (int) TypedValue.
                    applyDimension(TypedValue.COMPLEX_UNIT_DIP, 180, getResources().getDisplayMetrics());

            if (scrollY > prevScroll) {
                if (scrollY > defaultHeight) {
                    RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) scrollViewParent.getLayoutParams();
                    lp.topMargin = (int) TypedValue.
                            applyDimension(TypedValue.COMPLEX_UNIT_DIP, 60, getResources().getDisplayMetrics());
                    scrollViewParent.setLayoutParams(lp);
                    relImage.setVisibility(View.INVISIBLE);

                    tvTitle2.setVisibility(View.VISIBLE);
                } else if (prevScroll - scrollY < 100) {
                    RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) scrollViewParent.getLayoutParams();
                    lp.topMargin = (int) TypedValue.
                            applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0, getResources().getDisplayMetrics());
                    scrollViewParent.setLayoutParams(lp);
                    relImage.setVisibility(View.VISIBLE);
                    tvTitle2.setVisibility(View.GONE);
                }
            } else {
                RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) scrollViewParent.getLayoutParams();
                lp.topMargin = (int) TypedValue.
                        applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0, getResources().getDisplayMetrics());
                scrollViewParent.setLayoutParams(lp);
                relImage.setVisibility(View.VISIBLE);
                tvTitle2.setVisibility(View.GONE);
            }
            prevScroll = scrollY;
            Log.d("Liza ", "getScrollY: " + scrollY);
        }
    };

    private void getTransaction(){
        if (!isMarketOrder) {
            if (itemHistory instanceof CashInOut) {
                actionBar.hide();
                TransactionCallBack callback = new TransactionCallBack(this, getActivity());
                Call<TransactionData> call = LykkeApplication_.getInstance().getRestApi().
                        getBcnTransactionByCashOperation(Constants.PART_AUTHORIZATION + userPref.authToken().get(),
                                "?id=" + itemHistory.getId());
                call.enqueue(callback);
            } else {
                actionBar.show();
                scrollViewParent.getViewTreeObserver().removeOnScrollChangedListener(listener);
                relImage.setVisibility(View.GONE);
                tvTitle2.setVisibility(View.GONE);
                actionBar.setHomeButtonEnabled(true);
                actionBar.setDisplayHomeAsUpEnabled(true);
                if ((((Trading) itemHistory).getVolume()).compareTo(BigDecimal.ZERO) > 1) {
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
        } else {
            MarketOrderCallBack callback = new MarketOrderCallBack(this, getActivity());
            Call<MarketData> call = LykkeApplication_.getInstance().getRestApi().
                    getMarketOder(Constants.PART_AUTHORIZATION + userPref.authToken().get(),
                            "?orderId=" + itemHistory.getId());
            call.enqueue(callback);

        }
    }

    @Override
    public void initOnBackPressed() {
        getActivity().finish();
    }

    @Click(R.id.btnClose)
    public void btnClose(){
        getActivity().finish();
    }

    @Override
    public void onRefresh() {
        getTransaction();
    }

    @Override
    public void onSuccess(Object result) {
        dialog.hide();
        if (result instanceof TransactionResult) {
            if (((TransactionResult) result).getTransaction() != null) {
                isMarketOrder = false;
                scrollViewParent.setVisibility(View.VISIBLE);
                scrollViewParent2.setVisibility(View.GONE);

                swipeRefresh.setRefreshing(false);
                linearAmount.setVisibility(View.GONE);
                linearAssetName.setVisibility(View.GONE);
                linearBlockChainProgress.setVisibility(View.GONE);
                linearButton.setVisibility(View.VISIBLE);
                if (((TransactionResult) result).getTransaction().getHash() == null ||
                        ((TransactionResult) result).getTransaction().getHash().isEmpty()) {
                    linearHash.setVisibility(View.GONE);
                   viewHash.setVisibility(View.GONE);
                } else {
                    linearHash.setVisibility(View.VISIBLE);
                    viewHash.setVisibility(View.VISIBLE);
                    labelHash.setText(((TransactionResult) result).getTransaction().getHash());
                }

                if (((TransactionResult) result).getTransaction().getDate() == null ||
                        ((TransactionResult) result).getTransaction().getDate().isEmpty()) {
                    linearDate.setVisibility(View.GONE);
                    viewDate.setVisibility(View.GONE);
                } else {
                    linearDate.setVisibility(View.VISIBLE);
                    viewDate.setVisibility(View.VISIBLE);
                    labelDate.setText(((TransactionResult) result).getTransaction().getDate());
                }

                if (((TransactionResult) result).getTransaction().getConfirmations() == null ||
                        ((TransactionResult) result).getTransaction().getConfirmations().isEmpty()) {
                    linearConfirm.setVisibility(View.GONE);
                    viewConfirm.setVisibility(View.GONE);
                } else {
                    linearConfirm.setVisibility(View.VISIBLE);
                    viewConfirm.setVisibility(View.VISIBLE);
                    labelConfirm.setText(((TransactionResult) result).getTransaction().getConfirmations());
                }

                if (((TransactionResult) result).getTransaction().getAssetId() == null ||
                        ((TransactionResult) result).getTransaction().getAssetId().isEmpty()) {
                    linearAsset.setVisibility(View.GONE);
                    viewAsset.setVisibility(View.GONE);
                } else {
                    linearAsset.setVisibility(View.VISIBLE);
                    viewAsset.setVisibility(View.VISIBLE);
                    labelAsset.setTextColor(getActivity().getResources().getColor(R.color.blue_color));
                    labelAsset.setText(((TransactionResult) result).getTransaction().getAssetId());
                }

                if (((TransactionResult) result).getTransaction().getSenderId() == null ||
                        ((TransactionResult) result).getTransaction().getSenderId().isEmpty()) {
                    linearSender.setVisibility(View.GONE);
                    viewSender.setVisibility(View.GONE);
                } else {
                    linearSender.setVisibility(View.VISIBLE);
                    viewSender.setVisibility(View.VISIBLE);
                    labelSender.setTextColor(getActivity().getResources().getColor(R.color.blue_color));
                    labelSender.setText(((TransactionResult) result).getTransaction().getSenderId());
                }

                if (((TransactionResult) result).getTransaction().getBlock() == null ||
                        ((TransactionResult) result).getTransaction().getBlock().isEmpty()) {
                    linearBlock.setVisibility(View.GONE);
                    viewBlock.setVisibility(View.GONE);
                } else {
                    linearBlock.setVisibility(View.VISIBLE);
                    viewBlock.setVisibility(View.VISIBLE);
                    labelBlock.setText(((TransactionResult) result).getTransaction().getBlock());
                }

                if (((TransactionResult) result).getTransaction().getHeight() == null ||
                        ((TransactionResult) result).getTransaction().getHeight().isEmpty()) {
                    linearHeight.setVisibility(View.GONE);
                    viewHeight.setVisibility(View.GONE);
                } else {
                    linearHeight.setVisibility(View.VISIBLE);
                    viewHeight.setVisibility(View.VISIBLE);
                    labelHeight.setText(((TransactionResult) result).getTransaction().getHeight());
                }

                if (((TransactionResult) result).getTransaction().getQuality() == null ||
                        ((TransactionResult) result).getTransaction().getQuality().isEmpty()) {
                    linearQuantity.setVisibility(View.GONE);
                    viewQuatity.setVisibility(View.GONE);
                } else {
                    linearQuantity.setVisibility(View.VISIBLE);
                    viewQuatity.setVisibility(View.VISIBLE);
                    labelQuantity.setText(((TransactionResult) result).getTransaction().getQuality());
                }

                if (((TransactionResult) result).getTransaction().getUrl() == null ||
                        ((TransactionResult) result).getTransaction().getUrl().isEmpty()) {
                    linearUrl.setVisibility(View.GONE);
                    viewUrl.setVisibility(View.GONE);
                } else {
                    linearUrl.setVisibility(View.VISIBLE);
                    labelUrl.setTextColor(getActivity().getResources().getColor(R.color.blue_color));
                    labelUrl.setText(((TransactionResult) result).getTransaction().getUrl());
                    viewUrl.setVisibility(View.VISIBLE);
                }
                linearAssetName.setVisibility(View.GONE);
                viewAssetName.setVisibility(View.GONE);
                linearAmount.setVisibility(View.GONE);
                viewAmount.setVisibility(View.GONE);
                linearBlockChainProgress.setVisibility(View.GONE);
                swipeRefresh.setRefreshing(false);
                swipeRefresh.setEnabled(false);
                viewBlockChainInProgress.setVisibility(View.GONE);
            } else {
                if (itemHistory instanceof CashInOut) {
                    scrollViewParent.setVisibility(View.VISIBLE);
                    scrollViewParent2.setVisibility(View.GONE);

                    initNull();
                } else {
                    scrollViewParent.setVisibility(View.GONE);
                    scrollViewParent2.setVisibility(View.GONE);
                    dialog.show();
                    isMarketOrder = true;
                    getTransaction();
               }
            }
        } else if (result instanceof MarketResult) {
            if (((MarketResult) result).getMarketOrder() != null) {
                initMarketOrder((MarketResult) result);
            } else {
                initNull();
            }

        }
    }

    private void initMarketOrder(MarketResult result){
        initField(linearAssetTrading, labelAssetTrading,
                viewAssetTrading, result.getMarketOrder().getAsset());

        initField(linearUnitPurchaesed, labelUnitPurchaesed,
                viewUnitPurchaesed, result.getMarketOrder().getVolume());

        initField(linearExecutionPrice, labelExecutionPrice,
                viewExecutionPrice, result.getMarketOrder().getPrice());

        initField(linearComissionPaid, labelComissionPaid,
                viewComissionPaid, result.getMarketOrder().getComission());

        initField(linearTotalCost, labelTotalCost,
                viewTotalCost, result.getMarketOrder().getTotalCost());

        initField(linearBlockChainSettlement, labelBlockChainSettlement,
                viewBlockChainSettlement, result.getMarketOrder().getBlockChainSetteled());

        initField(linearPosition, labelPosition, viewPosition,
                result.getMarketOrder().getPosition());

        scrollViewParent.setVisibility(View.GONE);
        scrollViewParent2.setVisibility(View.VISIBLE);

    }

    private void initField(LinearLayout linearLayout, TextView textView, View view,
                           String data) {
        if (data != null ){
            linearLayout.setVisibility(View.VISIBLE);
            textView.setText(data);
            view.setVisibility(View.VISIBLE);
        } else {
            linearLayout.setVisibility(View.GONE);
            view.setVisibility(View.GONE);
        }
    }

    private void initNull(){
        swipeRefresh.setOnRefreshListener(this);
        swipeRefresh.setEnabled(true);
        swipeRefresh.setRefreshing(false);
        hideAllInfo();
        initViewData();
    }

    private void initViewData(){
        relImage.setVisibility(View.GONE);
        scrollViewParent.getViewTreeObserver().removeOnScrollChangedListener(listener);
        tvTitle2.setVisibility(View.VISIBLE);
        actionBar.setHomeButtonEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(false);
        linearAmount.setVisibility(View.VISIBLE);
        linearBlockChainProgress.setVisibility(View.VISIBLE);
        linearAssetName.setVisibility(View.VISIBLE);
        linearButton.setVisibility(View.GONE);
        if (itemHistory instanceof CashInOut) {
            labelAmount.setText(((CashInOut)itemHistory).getAmount().toPlainString());
        } else {
            labelAmount.setText(((Trading)itemHistory).getVolume().toPlainString());
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
        viewAmount.setVisibility(View.GONE);
        viewAssetName.setVisibility(View.GONE);
        viewBlockChainInProgress.setVisibility(View.GONE);
        viewHash.setVisibility(View.GONE);
        viewDate.setVisibility(View.GONE);
        viewConfirm.setVisibility(View.GONE);
        viewBlock.setVisibility(View.GONE);
        viewHeight.setVisibility(View.GONE);
        viewSender.setVisibility(View.GONE);
        viewAsset.setVisibility(View.GONE);
        viewQuatity.setVisibility(View.GONE);
        viewUrl.setVisibility(View.GONE);
    }

    @Override
    public void onFail(Object error) {
        dialog.hide();
        initNull();
    }

    @Override
    public void onConsume(Object o) {

    }
}
