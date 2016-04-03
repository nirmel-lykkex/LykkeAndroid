package com.lykkex.LykkeWallet.gui.fragments.mainfragments;

import android.app.ProgressDialog;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.gui.LykkeApplication_;
import com.lykkex.LykkeWallet.gui.adapters.HistoryAdapter;
import com.lykkex.LykkeWallet.gui.fragments.storage.UserPref_;
import com.lykkex.LykkeWallet.gui.utils.Constants;
import com.lykkex.LykkeWallet.gui.utils.validation.CallBackListener;
import com.lykkex.LykkeWallet.gui.widgets.DialogProgress;
import com.lykkex.LykkeWallet.rest.history.callback.HistoryCallBack;
import com.lykkex.LykkeWallet.rest.history.reposnse.model.History;
import com.lykkex.LykkeWallet.rest.history.reposnse.model.HistoryData;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import retrofit2.Call;

/**
 * Created by LIZA on 29.02.2016.
 */
@EFragment(R.layout.history_fragment)
public class HistoryFragment extends Fragment implements CallBackListener, SwipeRefreshLayout.OnRefreshListener{

    @Pref UserPref_ userPref;
    private ProgressDialog dialog;
    @ViewById
    SwipeRefreshLayout swipeRefresh;
    private HistoryAdapter adapter;
    @ViewById
    ListView listView;


    @AfterViews
    public void afterViews(){
        dialog = new ProgressDialog(getActivity());
        dialog.setMessage(getString(R.string.waiting));
        dialog.show();
        getHistory();
        swipeRefresh.setOnRefreshListener(this);
    }

    private void getHistory(){
        HistoryCallBack callBack = new HistoryCallBack(this, getActivity());
        Call<HistoryData> call = LykkeApplication_.getInstance().getRestApi().getHistory
                (Constants.PART_AUTHORIZATION + userPref.authToken().get(),
                        "?assetId=null");
        call.enqueue(callBack);
    }

    @Override
    public void onSuccess(Object result) {
        if (result instanceof History) {
            dialog.hide();
            adapter = new HistoryAdapter(((History) result).getList(), getActivity());
            listView.setAdapter(adapter);
            swipeRefresh.setRefreshing(false);
        }
    }

    @Override
    public void onFail(Object error) {

    }

    @Override
    public void onRefresh() {
        getHistory();
    }
}
