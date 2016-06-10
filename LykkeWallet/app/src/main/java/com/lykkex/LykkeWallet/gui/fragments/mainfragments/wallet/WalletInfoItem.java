package com.lykkex.LykkeWallet.gui.fragments.mainfragments.wallet;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.gui.activity.BaseActivity;
import com.lykkex.LykkeWallet.gui.activity.paymentflow.QrCodeActivity_;
import com.lykkex.LykkeWallet.gui.managers.SettingManager;
import com.lykkex.LykkeWallet.gui.utils.Constants;
import com.lykkex.LykkeWallet.rest.internal.response.model.BaseAsset;
import com.lykkex.LykkeWallet.rest.wallet.response.models.AssetsWallet;
import com.lykkex.LykkeWallet.rest.wallet.response.models.LykkeWalletResult;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * Created by Murtic on 08/06/16.
 */
@EViewGroup(R.layout.info_item)
public class WalletInfoItem extends RelativeLayout {

    @ViewById
    TextView tvValue;

    @ViewById
    ImageView imgPlus;

    @ViewById
    TextView tvTitleProp;

    private AssetsWallet assetsWallet;

    @AfterViews
    public void afterViews() {
    }

    @Click(R.id.imgPlus)
    public void onPlusClick() {
        Intent intent = new Intent();
        intent.setClass(getContext(), QrCodeActivity_.class);
        intent.putExtra(Constants.EXTRA_ASSET, assetsWallet);
        intent.putExtra(Constants.EXTRA_DEPOSIT, true);

        getContext().startActivity(intent);
    }

    public WalletInfoItem(Context context) {
        super(context);
    }

    public WalletInfoItem(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void render(final LykkeWalletResult lykkeWallet, final AssetsWallet assetsWallet) {
        this.assetsWallet = assetsWallet;

        BaseAsset baseAsset = SettingManager.getInstance().getBaseAsset(assetsWallet.getId());

        if (baseAsset == null || baseAsset.getHideDeposit()) {
            imgPlus.setVisibility(View.INVISIBLE);
        } else {
            imgPlus.setVisibility(View.VISIBLE);
        }

        tvTitleProp.setText(assetsWallet.getName());

        tvValue.setText(Html.fromHtml(assetsWallet.getBalanceWithSymbol()));

        setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPaymentActivity(lykkeWallet, assetsWallet);
            }
        });

        tvTitleProp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPaymentActivity(lykkeWallet, assetsWallet);
            }
        });

        tvValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPaymentActivity(lykkeWallet, assetsWallet);
            }
        });
    }

    private void startPaymentActivity(LykkeWalletResult lykkeWallet, AssetsWallet assetsWallet){
        if ((lykkeWallet.getColoredMultiSig() != null &&
                assetsWallet.getIssuerId().equals(Constants.LKE)) ||
                (lykkeWallet.getMultiSig() != null &&
                        assetsWallet.getIssuerId().equals(Constants.BTC))) {
            Intent intent = new Intent();
            intent.setClass(getContext(), QrCodeActivity_.class);
            intent.putExtra(Constants.EXTRA_ASSET, assetsWallet);

            getContext().startActivity(intent);
        }
    }
}
