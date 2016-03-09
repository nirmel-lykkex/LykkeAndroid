package com.lykkex.LykkeWallet.gui.fragments.mainfragments;

import android.app.ProgressDialog;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.gui.LykkeApplication_;
import com.lykkex.LykkeWallet.gui.adapters.WalletAdapter;
import com.lykkex.LykkeWallet.gui.fragments.storage.UserPref_;
import com.lykkex.LykkeWallet.gui.utils.Constants;
import com.lykkex.LykkeWallet.gui.utils.validation.CallBackListener;
import com.lykkex.LykkeWallet.rest.base.models.Error;
import com.lykkex.LykkeWallet.rest.wallet.callback.WalletCallback;
import com.lykkex.LykkeWallet.rest.wallet.response.models.LykkeWallerData;
import com.lykkex.LykkeWallet.rest.wallet.response.models.LykkeWalletResult;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import retrofit2.Call;

/**
 * Created by LIZA on 29.02.2016.
 */
@EFragment(R.layout.wallet_fragment)
public class WalletFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener,
        CallBackListener{

    @ViewById SwipeRefreshLayout swipeRefresh;
    @ViewById ListView listView;
    private WalletAdapter adapter;
    @Pref  UserPref_ userPref;
    private boolean shouldShowError = false;

    @AfterViews
    public void afterViews(){
        shouldShowError = false;
        LykkeWalletResult res = new LykkeWalletResult();
        setUpAdapter(res, false);
        swipeRefresh.setOnRefreshListener(this);
    }

    public void onStart(){
        super.onStart();
        refreshContent();
    }

    private void refreshContent(){
        WalletCallback callback = new WalletCallback(this, getActivity());
        Call<LykkeWallerData> call = LykkeApplication_.getInstance().
                getRestApi().getLykkeWallet(Constants.PART_AUTHORIZATION + userPref.authToken().get());
        call.enqueue(callback);

    }


    private void setUpAdapter(LykkeWalletResult result, boolean isItGet){
        adapter = new WalletAdapter(result, getActivity(), isItGet);
        listView.setAdapter(adapter);
        swipeRefresh.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        refreshContent();
    }

    @Override
    public void onSuccess(Object result) {
        if (result instanceof Error && shouldShowError) {
            Toast.makeText(getContext(), getString(R.string.server_error),
                    Toast.LENGTH_LONG).show();
        }
        shouldShowError = true;
        setUpAdapter(((LykkeWallerData) result).getResult(), true);
    }

    @Override
    public void onFail(Object error) {
        if (shouldShowError && getActivity() != null){
            Toast.makeText(getActivity(), getString(R.string.server_error),
                    Toast.LENGTH_LONG).show();
        }
        swipeRefresh.setRefreshing(false);
        shouldShowError = true;
    }
}
