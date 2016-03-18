package com.lykkex.LykkeWallet.gui.fragments.mainfragments.tradings;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.gui.LykkeApplication_;
import com.lykkex.LykkeWallet.gui.fragments.BaseFragment;
import com.lykkex.LykkeWallet.gui.fragments.storage.UserPref_;
import com.lykkex.LykkeWallet.gui.utils.Constants;
import com.lykkex.LykkeWallet.rest.trading.callback.DescriptionCallBack;
import com.lykkex.LykkeWallet.rest.trading.response.model.DescriptionData;
import com.lykkex.LykkeWallet.rest.trading.response.model.DescriptionResult;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import retrofit2.Call;

/**
 * Created by e.kazimirova on 18.03.2016.
 */
@EFragment(R.layout.trading_description_fragment)
public class TradingDescription  extends BaseFragment {

    @ViewById RelativeLayout relProgress;
    @ViewById LinearLayout linearInfo;
    @ViewById ProgressBar progressBar;
    @ViewById TextView tvAssetClass;
    @ViewById TextView tvDescription;
    @ViewById TextView tvIssuerName;
    @ViewById TextView tvNumberOfCoins;
    @ViewById LinearLayout linearPop;
    @ViewById Button btnBuy;
    @Pref UserPref_ userPref;

    @AfterViews
    public void afterViews(){
        actionBar.setTitle(getArguments().getString(Constants.EXTRA_ASSETPAIR_NAME));
        setUpVisibility(View.VISIBLE, View.GONE);
        getDescription();
    }

    private void getDescription(){
        DescriptionCallBack callBack = new DescriptionCallBack(this, getActivity());
        Call<DescriptionData> call = LykkeApplication_.getInstance().getRestApi().
                getDescription(Constants.PART_AUTHORIZATION + userPref.authToken().get(),
                        getArguments().getString(Constants.EXTRA_ASSETPAIR_ID));
        call.enqueue(callBack);
    }

    @Override
    public void initOnBackPressed() {
        getActivity().finish();
    }

    @Override
    public void onSuccess(Object result) {
        if (result instanceof DescriptionResult) {
            setUpVisibility(View.GONE, View.VISIBLE);
            tvAssetClass.setText(((DescriptionResult) result).getAssetClass());
            tvDescription.setText(((DescriptionResult) result).getDescription());
            tvIssuerName.setText(((DescriptionResult) result).getIssuerName());
            tvNumberOfCoins.setText(((DescriptionResult) result).getNumberOfCoins());
            btnBuy.setTag(getString(R.string.buy_rate) + ((DescriptionResult) result).getMarketCapitalization());
            for (int i =0; i<Integer.parseInt(((DescriptionResult) result).getPopIndex()); i++){
                ImageView imageView = new ImageView(getActivity());
                imageView.setImageResource(R.drawable.star);
                imageView.setLayoutParams(new LinearLayout.LayoutParams(
                        20,
                        20));
                linearInfo.addView(imageView);
            }
        }
    }

    private void setUpVisibility(int gone, int goneLinear) {
        relProgress.setVisibility(gone);
        progressBar.setVisibility(gone);
        linearInfo.setVisibility(goneLinear);
    }

    @Override
    public void onFail(Object error) {

    }

    @Override
    public void onConsume(Object o) {

    }
}
