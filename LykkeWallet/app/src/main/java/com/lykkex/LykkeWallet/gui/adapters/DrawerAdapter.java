package com.lykkex.LykkeWallet.gui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.gui.LykkeApplication_;

import java.util.ArrayList;

/**
 * Created by LIZA on 29.02.2016.
 */
public class DrawerAdapter extends BaseAdapter {

    private Context mContext;

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    private int active = 0;

    public ArrayList<DrawerModel> getListModel() {
        return listModel;
    }

    private ArrayList<DrawerModel> listModel= new ArrayList<>();

    public DrawerAdapter(Context context){
        this.mContext = context;
        DrawerModel drawerModel = new DrawerModel(LykkeApplication_.getInstance().
                getResources().getString(R.string.wallet_item),
                R.drawable.wallet_inactive, R.drawable.wallet_active,
                LykkeApplication_.getInstance().
                        getResources().getString(R.string.wallet_title));

        listModel.add(drawerModel);

        drawerModel = new DrawerModel(LykkeApplication_.getInstance().
                getResources().getString(R.string.exchange_item),
                R.drawable.trading_inactive, R.drawable.trading_active,
                LykkeApplication_.getInstance().
                        getResources().getString(R.string.trading_title));

        listModel.add(drawerModel);

        drawerModel = new DrawerModel(LykkeApplication_.getInstance().
                getResources().getString(R.string.history_item),
                R.drawable.history_inactive, R.drawable.history_active,
                LykkeApplication_.getInstance().
                        getResources().getString(R.string.history_title));

        listModel.add(drawerModel);

        drawerModel = new DrawerModel(LykkeApplication_.getInstance().
                getResources().getString(R.string.setting_item),
                R.drawable.setting_inactive, R.drawable.setting_active,
                LykkeApplication_.getInstance().
                        getResources().getString(R.string.setting_title));

        listModel.add(drawerModel);
    }

    @Override
    public int getCount() {
        return listModel.size();
    }

    @Override
    public DrawerModel getItem(int i) {
        return listModel.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // используем созданные, но не используемые view
        View view = convertView;
        if (view == null) {
            LayoutInflater lInflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = lInflater.inflate(R.layout.drawer_list_item, parent, false);
        }
        ImageView imgIcon = (ImageView) view.findViewById(R.id.imgIcon);
        TextView tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        if (active == position) {
            imgIcon.setBackgroundResource(listModel.get(position).getResActive());
        } else {
            imgIcon.setBackgroundResource(listModel.get(position).getRes());
        }
        tvTitle.setText(listModel.get(position).getName());
        return view;
    }
}
