package com.lykkex.LykkeWallet.gui.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.gui.fragments.storage.UserPref_;
import com.lykkex.LykkeWallet.gui.models.SettingSinglenton;
import com.lykkex.LykkeWallet.gui.utils.Constants;
import com.lykkex.LykkeWallet.rest.internal.response.model.BaseAsset;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by LIZA on 03.03.2016.
 */
@EActivity(R.layout.payment_activity)
public class PaymentActivity extends ActionBarActivity {

    @ViewById  WebView webViewPayment;
    @ViewById ProgressBar progressBar;
    @Pref   UserPref_ userPref;

    @AfterViews
    public void afterViews(){
        String assetId = getIntent().getExtras().getString(Constants.EXTRA_ASSET_ID);

        getSupportActionBar().setTitle(String.format(getString(R.string.deposit_wallet),
                getAssetName(assetId)));
        progressBar.setVisibility(View.VISIBLE);
        WebSettings settings = webViewPayment.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setLoadsImagesAutomatically(true);
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        webViewPayment.setVisibility(View.VISIBLE);
        webViewPayment.setBackgroundColor(Color.TRANSPARENT);
        webViewPayment.getSettings().setUseWideViewPort(true);
        webViewPayment.getSettings().setLoadWithOverviewMode(true);
        webViewPayment.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);

        webViewPayment.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                progressBar.setVisibility(View.GONE);
            }

        });

        Map<String, String> extraHeaders = new HashMap<>();
        extraHeaders.put("Authorization", Constants.PART_AUTHORIZATION + userPref.authToken().get());
        webViewPayment.loadUrl(SettingSinglenton.getInstance().getDepositUrl()+
                String.format(Constants.SETUP_URL_PAYMENT,
                        userPref.email().get(),
                        assetId),
                        extraHeaders);
    }

    private String getAssetName(String assetId){
        SettingSinglenton setting = SettingSinglenton.getInstance();
        for (BaseAsset asset : setting.getBaseAssets()) {
            if (asset.getId().equals(assetId)) {
                return asset.getName();
            }
        }
        return "";
    }
}
