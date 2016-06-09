package com.lykkex.LykkeWallet.gui.customviews;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.gui.activity.paymentflow.QrCodeActivity_;
import com.lykkex.LykkeWallet.gui.utils.Constants;
import com.lykkex.LykkeWallet.rest.wallet.response.models.AssetsWallet;
import com.lykkex.LykkeWallet.rest.wallet.response.models.LykkeWalletResult;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import java.math.RoundingMode;

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

    @AfterViews
    public void afterViews() {
    }

    public WalletInfoItem(Context context) {
        super(context);
    }

    public WalletInfoItem(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void render(final LykkeWalletResult lykkeWallet, final AssetsWallet assetsWallet) {
        if ((lykkeWallet.getColoredMultiSig() != null &&
                assetsWallet.getIssuerId().equals(Constants.LKE)) ||
                (lykkeWallet.getMultiSig() != null &&
                        assetsWallet.getIssuerId().equals(Constants.BTC))) {
            imgPlus.setVisibility(View.VISIBLE);
        } else {
            imgPlus.setVisibility(View.GONE);
        }
        tvTitleProp.setText(assetsWallet.getName());
        tvValue.setText(assetsWallet.getBalance().setScale(assetsWallet.getAccuracy(),
                        RoundingMode.HALF_EVEN).stripTrailingZeros().toPlainString());

        setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPaymentActivity(lykkeWallet, assetsWallet);
            }
        });

        tvTitleProp.setTag(assetsWallet);
        tvTitleProp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPaymentActivity(lykkeWallet, assetsWallet);
            }
        });

        tvValue.setTag(assetsWallet);
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
