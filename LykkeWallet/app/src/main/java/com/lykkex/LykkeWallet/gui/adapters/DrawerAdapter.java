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

    public ArrayList<DrawerModel> getListModel() {
        return listModel;
    }

    private ArrayList<DrawerModel> listModel= new ArrayList<>();

    public DrawerAdapter(Context context){
        this.mContext = context;
        DrawerModel drawerModel = new DrawerModel();
        drawerModel.setName(LykkeApplication_.getInstance().
                getResources().getString(R.string.wallet_item));
        drawerModel.setRes(R.drawable.ic_drawer);
        listModel.add(drawerModel);

        drawerModel = new DrawerModel();
        drawerModel.setName(LykkeApplication_.getInstance().
                getResources().getString(R.string.trading_item));
        drawerModel.setRes(R.drawable.ic_drawer);
        listModel.add(drawerModel);

        drawerModel = new DrawerModel();
        drawerModel.setName(LykkeApplication_.getInstance().
                getResources().getString(R.string.history_item));
        drawerModel.setRes(R.drawable.ic_drawer);
        listModel.add(drawerModel);

        drawerModel = new DrawerModel();
        drawerModel.setName(LykkeApplication_.getInstance().
                getResources().getString(R.string.setting_item));
        drawerModel.setRes(R.drawable.ic_drawer);
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
        imgIcon.setBackgroundResource(listModel.get(position).getRes());
        tvTitle.setText(listModel.get(position).getName());
        return view;
    }
}
