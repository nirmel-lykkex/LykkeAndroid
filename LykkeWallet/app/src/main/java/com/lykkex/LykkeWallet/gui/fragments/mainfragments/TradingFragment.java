package com.lykkex.LykkeWallet.gui.fragments.mainfragments;

import android.content.Context;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.gui.LykkeApplication_;
import com.lykkex.LykkeWallet.gui.fragments.storage.UserPref_;
import com.lykkex.LykkeWallet.gui.models.AssetPairSinglenton;
import com.lykkex.LykkeWallet.gui.models.SettingSinglenton;
import com.lykkex.LykkeWallet.gui.utils.Constants;
import com.lykkex.LykkeWallet.gui.utils.validation.CallBackListener;
import com.lykkex.LykkeWallet.rest.trading.callback.AssetPairCallBack;
import com.lykkex.LykkeWallet.rest.trading.callback.AssetPairRatesCallBack;
import com.lykkex.LykkeWallet.rest.trading.response.model.AssetPair;
import com.lykkex.LykkeWallet.rest.trading.response.model.AssetPairData;
import com.lykkex.LykkeWallet.rest.trading.response.model.AssetPairsResult;
import com.lykkex.LykkeWallet.rest.trading.response.model.RateData;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.w3c.dom.Text;

import retrofit2.Call;

/**
 * Created by LIZA on 29.02.2016.
 */
@EFragment(R.layout.trading_fragment)
public class TradingFragment extends Fragment implements CallBackListener {

    @Pref  UserPref_ pref;
    @ViewById ProgressBar progressBar;
    @ViewById  LinearLayout linearEntity;
    private Handler handler = new Handler();
    private Runnable run = new Runnable() {
        @Override
        public void run() {
            getRates();
            startHandler();
        }
    };

    @AfterViews
    public void afterViews(){
        handler.post(run);
        tryToSetUpView();
        if (AssetPairSinglenton.getInstance().getResult() == null ||
                AssetPairSinglenton.getInstance().getResult().getAssetPairs() != null ||
                AssetPairSinglenton.getInstance().getResult().getAssetPairs().length == 0){
            getAssetPairs();
        }
    }

    @Click(R.id.lykke_section)
    public void clickLykke(){
        if (AssetPairSinglenton.getInstance().isCollapsed()) {
            linearEntity.removeAllViews();
            AssetPairSinglenton.getInstance().setIsCollapsed(false);
        } else {
            tryToSetUpView();
            AssetPairSinglenton.getInstance().setIsCollapsed(true);
        }
    }

    private void tryToSetUpView(){
        AssetPairsResult assetPairs = AssetPairSinglenton.getInstance().getResult();
        if (assetPairs != null &&
                assetPairs.getAssetPairs() != null && assetPairs.getAssetPairs().length > 0) {
            setUpView(assetPairs);
        } else {
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    private void getAssetPairs(){
        AssetPairCallBack callBack = new AssetPairCallBack(this, getActivity());
        Call<AssetPairData> call = LykkeApplication_.getInstance().getRestApi().getAssetPairs
                (Constants.PART_AUTHORIZATION + pref.authToken().get());
        call.enqueue(callBack);
    }

    private void setUpView(AssetPairsResult result){
        progressBar.setVisibility(View.GONE);
        if (AssetPairSinglenton.getInstance().isCollapsed()) {
            for (AssetPair pair : result.getAssetPairs()) {
                LayoutInflater lInflater = (LayoutInflater) getActivity()
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view = lInflater.inflate(R.layout.trading_item, null, false);
                TextView tvAssetName = (TextView)view.findViewById(R.id.tvPrice);
                TextView tvPrice = (TextView)view.findViewById(R.id.tvPrice);
                tvAssetName.setText(pair.getName());
                linearEntity.addView(view);
            }
        } else {
            linearEntity.removeAllViews();
        }
    }

    private void getRates(){
        AssetPairRatesCallBack callBack = new AssetPairRatesCallBack(this, getActivity());
        Call<RateData> call = LykkeApplication_.getInstance().getRestApi().getAssetPairsRates
                (Constants.PART_AUTHORIZATION + pref.authToken().get());
        call.enqueue(callBack);
    }

    private void startHandler(){
        handler.postDelayed(run, SettingSinglenton.getInstance().getRefreshTimer());
    }

    @Override
    public void onSuccess(Object result) {
        if (result instanceof AssetPairsResult) {
            AssetPairSinglenton.getInstance().setIsCollapsed(false);
            setUpView((AssetPairsResult) result);
        }
    }

    @Override
    public void onFail(Object error) {

    }
}
