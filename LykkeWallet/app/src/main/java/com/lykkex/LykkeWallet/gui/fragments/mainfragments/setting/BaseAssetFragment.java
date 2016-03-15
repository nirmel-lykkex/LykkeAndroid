package com.lykkex.LykkeWallet.gui.fragments.mainfragments.setting;

import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.gui.LykkeApplication_;
import com.lykkex.LykkeWallet.gui.adapters.BaseAssetAdapter;
import com.lykkex.LykkeWallet.gui.fragments.BaseFragment;
import com.lykkex.LykkeWallet.gui.fragments.storage.UserPref_;
import com.lykkex.LykkeWallet.gui.models.SettingSinglenton;
import com.lykkex.LykkeWallet.gui.utils.Constants;
import com.lykkex.LykkeWallet.rest.internal.callback.BaseAssetCallback;
import com.lykkex.LykkeWallet.rest.internal.response.model.BaseAsset;
import com.lykkex.LykkeWallet.rest.internal.response.model.BaseAssetData;
import com.lykkex.LykkeWallet.rest.internal.response.model.BaseAssetResult;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import retrofit2.Call;

/**
 * Created by LIZA on 15.03.2016.
 */
@EFragment(R.layout.base_asset_fragment)
public class BaseAssetFragment extends BaseFragment {

    @ViewById  ListView listView;
    @ViewById ProgressBar progressBar;
    @Pref  UserPref_ pref;

    @AfterViews
    public void afterViews(){
        actionBar.setTitle(R.string.base_asset_title);
        BaseAssetCallback baseAssetCallback = new BaseAssetCallback(this, getActivity());
        Call<BaseAssetData> call = LykkeApplication_.getInstance().
                getRestApi().getBaseAssets(Constants.PART_AUTHORIZATION + pref.authToken().get());
        call.enqueue(baseAssetCallback);
        if (SettingSinglenton.getInstance().getBaseAssets() == null
                || SettingSinglenton.getInstance().getBaseAssets().length == 0){
            progressBar.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
        } else {
            progressBar.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
            setUpAdapter();
        }

    }

    private void setUpAdapter(){
        BaseAssetAdapter assetAdapter = new BaseAssetAdapter
                (SettingSinglenton.getInstance().getBaseAssets(), getActivity(),pref);
        listView.setAdapter(assetAdapter);

    }
    @Override
    public void initOnBackPressed() {
        getActivity().finish();
    }

    @Override
    public void onSuccess(Object result) {
        if (result instanceof BaseAssetResult){
            SettingSinglenton.getInstance().setBaseAssets(((BaseAssetResult) result).getAsset());
            setUpAdapter();
        }
    }

    @Override
    public void onFail(Object error) {

    }

    @Override
    public void onConsume(Object o) {

    }
}
