package com.lykkex.LykkeWallet.gui.fragments.mainfragments.setting;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.gui.LykkeApplication_;
import com.lykkex.LykkeWallet.gui.activity.paymentflow.QrCodeActivity_;
import com.lykkex.LykkeWallet.gui.managers.SettingManager;
import com.lykkex.LykkeWallet.gui.utils.Constants;
import com.lykkex.LykkeWallet.gui.utils.validation.CallBackListener;
import com.lykkex.LykkeWallet.rest.internal.callback.BaseAssetCallback;
import com.lykkex.LykkeWallet.rest.internal.request.model.IdBaseAsset;
import com.lykkex.LykkeWallet.rest.internal.response.model.BaseAsset;
import com.lykkex.LykkeWallet.rest.internal.response.model.BaseAssetData;
import com.lykkex.LykkeWallet.rest.internal.response.model.BaseAssetResult;
import com.lykkex.LykkeWallet.rest.wallet.response.models.AssetsWallet;
import com.lykkex.LykkeWallet.rest.wallet.response.models.LykkeWalletResult;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import retrofit2.Call;

/**
 * Created by Murtic on 08/06/16.
 */
@EViewGroup(R.layout.base_asset_item)
public class BaseAssetItem extends RelativeLayout {

    @ViewById
    RelativeLayout relData;

    @ViewById
    ImageView imgAsset;

    @ViewById
    TextView tvAsset;

    private ProgressDialog dialog;

    private BaseAsset baseAsset;

    @AfterViews
    public void afterViews() {
        dialog = new ProgressDialog(getContext());
        dialog.setMessage(getContext().getString(R.string.waiting));
    }

    public BaseAssetItem(Activity context) {
        super(context);
    }

    public BaseAssetItem(Activity context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Click({R.id.tvAsset, R.id.relData, R.id.imgAsset})
    public void onClick() {
        sendRequest(baseAsset);
    }

    public void render(final BaseAsset baseAsset) {
        this.baseAsset = baseAsset;

        tvAsset.setText(baseAsset.getName());
        tvAsset.setTag(baseAsset.getId());
        relData.setTag(baseAsset.getId());
        imgAsset.setTag(baseAsset.getId());

        if (SettingManager.getInstance().getBaseAssetId().equals(
                baseAsset.getId())){
            imgAsset.setVisibility(View.VISIBLE);
        } else {
            imgAsset.setVisibility(View.GONE);
        }
    }

    private void sendRequest(final BaseAsset baseAsset){
        dialog.show();
        IdBaseAsset asset = new IdBaseAsset(baseAsset.getId());
        Call<BaseAssetData> call = LykkeApplication_.getInstance().getRestApi().
                postBaseAsset(asset);
        BaseAssetCallback baseAssetCallback = new BaseAssetCallback(new CallBackListener<BaseAssetResult>() {
            @Override
            public void onSuccess(BaseAssetResult result) {
                dialog.dismiss();

                SettingManager.getInstance().setBaseAssetId(baseAsset.getId());

                ((Activity)getContext()).finish();
            }

            @Override
            public void onFail(Object error) {

            }
        }, (Activity) getContext());

        call.enqueue(baseAssetCallback);
    }
}
