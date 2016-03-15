package com.lykkex.LykkeWallet.gui.adapters;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.gui.LykkeApplication_;
import com.lykkex.LykkeWallet.gui.fragments.storage.UserPref_;
import com.lykkex.LykkeWallet.gui.models.SettingSinglenton;
import com.lykkex.LykkeWallet.gui.utils.Constants;
import com.lykkex.LykkeWallet.gui.utils.validation.CallBackListener;
import com.lykkex.LykkeWallet.rest.internal.callback.BaseAssetCallback;
import com.lykkex.LykkeWallet.rest.internal.request.model.IdBaseAsset;
import com.lykkex.LykkeWallet.rest.internal.response.model.BaseAsset;
import com.lykkex.LykkeWallet.rest.internal.response.model.BaseAssetData;
import com.lykkex.LykkeWallet.rest.internal.response.model.BaseAssetResult;

import retrofit2.Call;

/**
 * Created by LIZA on 15.03.2016.
 */
public class BaseAssetAdapter extends BaseAdapter implements CallBackListener{

    private BaseAsset[] list;
    private Activity mContext;
    private UserPref_ pref;
    private ProgressDialog dialog;
    private String id;

    public BaseAssetAdapter(BaseAsset[] list, Activity context,
                            UserPref_ pref){
        this.list = list;
        this.mContext = context;
        this.pref = pref;
        dialog = new ProgressDialog(context);
        dialog.setMessage(context.getString(R.string.waiting));
    }

    @Override
    public int getCount() {
        return list.length;
    }

    @Override
    public Object getItem(int i) {
        return list[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        View view = convertView;
        if (view == null) {
            LayoutInflater lInflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = lInflater.inflate(R.layout.base_asset_item, viewGroup, false);
        }

        TextView tvAsset = (TextView) view.findViewById(R.id.tvAsset);
        ImageView imgAsset = (ImageView) view.findViewById(R.id.imgAsset);
        RelativeLayout relData = (RelativeLayout)view.findViewById(R.id.relData);

        tvAsset.setText(list[position].getName());
        tvAsset.setTag(list[position].getId());
        relData.setTag(list[position].getId());
        imgAsset.setTag(list[position].getId());

        tvAsset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                id = (String) view.getTag();
                sendRequest();
            }
        });

        imgAsset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                id = (String) view.getTag();
                sendRequest();
            }
        });

        relData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                id = (String) view.getTag();
                sendRequest();
            }
        });

        if (SettingSinglenton.getInstance().getBaseAssetId().equals(
                list[position].getId())){
            imgAsset.setVisibility(View.VISIBLE);
        } else {
            imgAsset.setVisibility(View.GONE);
        }
        return view;
    }

    private void sendRequest(){
        dialog.show();
        IdBaseAsset asset = new IdBaseAsset(id);
        Call<BaseAssetData> call = LykkeApplication_.getInstance().getRestApi().
                postBaseAsset(Constants.PART_AUTHORIZATION + pref.authToken().get(), asset);
        BaseAssetCallback baseAssetCallback = new BaseAssetCallback(this, mContext);
        call.enqueue(baseAssetCallback);
    }

    @Override
    public void onSuccess(Object result) {
        if (result instanceof BaseAssetResult){
            dialog.hide();
            SettingSinglenton.getInstance().setBaseAssetId(id);
            mContext.finish();
        }
    }

    @Override
    public void onFail(Object error) {

    }
}
