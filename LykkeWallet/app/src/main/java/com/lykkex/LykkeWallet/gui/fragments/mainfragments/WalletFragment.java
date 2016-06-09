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
import com.lykkex.LykkeWallet.gui.models.WalletSinglenton;
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

    @ViewById
    SwipeRefreshLayout swipeRefresh;

    @ViewById
    ListView listView;

    private WalletAdapter adapter;

    @Pref
    UserPref_ userPref;

    private boolean shouldShowError = false;
    private boolean isItGet = false;

    @AfterViews
    public void afterViews(){
        LykkeWalletResult res = new LykkeWalletResult();
        setUpAdapter(res, isItGet);
        swipeRefresh.setOnRefreshListener(this);
    }

    public void onStart(){
        super.onStart();
        shouldShowError = false;
        refreshContent();
    }

    private void refreshContent(){
        WalletCallback callback = new WalletCallback(this, getActivity());
        Call<LykkeWallerData> call = LykkeApplication_.getInstance().getRestApi().getLykkeWallet();
        call.enqueue(callback);
    }

    private void setUpAdapter(LykkeWalletResult result, boolean isItGet){
        if ((result == null || result.getBankCardses() == null
                || result.getLykke() == null || result.getLykke().getAssets() == null ) ||
                (result != null && result.getBankCardses() != null &&
                        result.getLykke() != null && result.getLykke().getAssets() != null &&
                        result.getBankCardses().length == 0 &&
                        result.getLykke().getAssets().length == 0) && isItGet){
            result = WalletSinglenton.getInstance().getResult();
        }

        WalletSinglenton.getInstance().setResult(result);

        if (result != null && result.getBankCardses() != null &&
                result.getLykke() != null && result.getLykke().getAssets() != null &&
                result.getBankCardses().length == 0 && result.getLykke().getAssets().length == 0){
            isItGet = true;
        }

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
        if (result instanceof Error && shouldShowError && ((Error)result).getCode() !=
                Constants.ERROR_401 && userPref.email().get() != null &&
                !userPref.email().get().isEmpty()) {
            Toast.makeText(getContext(), getString(R.string.server_error),
                    Toast.LENGTH_LONG).show();
        }
        shouldShowError = true;
        isItGet = true;
        setUpAdapter(((LykkeWallerData) result).getResult(), isItGet);
    }

    @Override
    public void onFail(Object error) {
        if ((error == null || ((Error)error).getCode() !=
                Constants.ERROR_401) && shouldShowError && getActivity() != null
                ){
            Toast.makeText(getActivity(), getString(R.string.server_error),
                    Toast.LENGTH_LONG).show();
        }
        LykkeWalletResult res = new LykkeWalletResult();
        setUpAdapter(res, isItGet);
        shouldShowError = true;
    }
}
