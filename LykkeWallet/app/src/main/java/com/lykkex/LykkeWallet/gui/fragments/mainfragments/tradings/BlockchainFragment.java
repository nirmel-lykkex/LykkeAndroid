package com.lykkex.LykkeWallet.gui.fragments.mainfragments.tradings;

import android.content.Context;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.gui.LykkeApplication_;
import com.lykkex.LykkeWallet.gui.fragments.BaseFragment;
import com.lykkex.LykkeWallet.gui.fragments.storage.UserPref_;
import com.lykkex.LykkeWallet.gui.utils.Constants;
import com.lykkex.LykkeWallet.gui.utils.validation.CallBackListener;
import com.lykkex.LykkeWallet.gui.widgets.LockableScrollView;
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
public class BlockchainFragment extends BaseFragment implements CallBackListener {

    @Pref  UserPref_ userPref;
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
    private int prevScroll = 0;

    @AfterViews
    public void afterViews(){
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setDefaultDisplayHomeAsUpEnabled(false);
        actionBar.hide();
        hideAllInfo();
        actionBar.setTitle(getString(R.string.blockchain_title));
        actionBar.setDisplayHomeAsUpEnabled(false);
        Order order = (Order) getArguments().getSerializable(Constants.EXTRA_ORDER);
        getTransaction(order.getId());

        scrollViewParent.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
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
        });
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

    @Deprecated
    public void initOnBackPressed() {
        getActivity().finish();
    }

    @Override
    public void onSuccess(Object result) {
        if (result instanceof TransactionResult) {
            if (((TransactionResult) result).getTransaction() != null) {
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
            }
        } else {
           hideAllInfo();
        }
    }

    private void hideAllInfo(){
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
}
