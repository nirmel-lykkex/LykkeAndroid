package com.lykkex.LykkeWallet.gui.adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.lykkex.LykkeWallet.gui.fragments.mainfragments.setting.BaseAssetItem;
import com.lykkex.LykkeWallet.gui.fragments.mainfragments.setting.BaseAssetItem_;
import com.lykkex.LykkeWallet.rest.internal.response.model.BaseAsset;

import java.util.List;

/**
 * Created by LIZA on 15.03.2016.
 */
public class BaseAssetAdapter extends BaseAdapter {

    private List<BaseAsset> list;
    private Activity mContext;

    public BaseAssetAdapter(List<BaseAsset> list, Activity context){
        this.list = list;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        BaseAssetItem view = (BaseAssetItem) convertView;

        if (view == null) {
            view = BaseAssetItem_.build(mContext);
        }

        view.render(list.get(position));

        return view;
    }
}
