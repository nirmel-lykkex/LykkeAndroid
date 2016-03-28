package com.lykkex.LykkeWallet.gui.fragments.mainfragments;

import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.gui.LykkeApplication_;
import com.lykkex.LykkeWallet.gui.fragments.storage.UserPref_;
import com.lykkex.LykkeWallet.gui.utils.Constants;
import com.lykkex.LykkeWallet.gui.utils.validation.CallBackListener;
import com.lykkex.LykkeWallet.rest.history.callback.HistoryCallBack;
import com.lykkex.LykkeWallet.rest.history.reposnse.model.History;
import com.lykkex.LykkeWallet.rest.history.reposnse.model.HistoryData;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.sharedpreferences.Pref;

import retrofit2.Call;

/**
 * Created by LIZA on 29.02.2016.
 */
@EFragment(R.layout.history_fragment)
public class HistoryFragment extends Fragment implements CallBackListener{

    @Pref UserPref_ userPref;

    @AfterViews
    public void afterViews(){
        getHistory();
    }

    private void getHistory(){
        HistoryCallBack callBack = new HistoryCallBack(this, getActivity());
        Call<HistoryData> call = LykkeApplication_.getInstance().getRestApi().getHistory
                (Constants.PART_AUTHORIZATION + userPref.authToken().get());
        call.enqueue(callBack);
    }

    @Override
    public void onSuccess(Object result) {
        if (result instanceof History) {
            Toast.makeText(getActivity(), "Success", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onFail(Object error) {

    }
}
