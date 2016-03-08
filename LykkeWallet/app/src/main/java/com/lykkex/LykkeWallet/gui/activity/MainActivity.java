package com.lykkex.LykkeWallet.gui.activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.gui.LykkeApplication_;
import com.lykkex.LykkeWallet.gui.adapters.DrawerAdapter;
import com.lykkex.LykkeWallet.gui.fragments.mainfragments.HistoryFragment;
import com.lykkex.LykkeWallet.gui.fragments.mainfragments.HistoryFragment_;
import com.lykkex.LykkeWallet.gui.fragments.mainfragments.SettingFragment;
import com.lykkex.LykkeWallet.gui.fragments.mainfragments.SettingFragment_;
import com.lykkex.LykkeWallet.gui.fragments.mainfragments.TradingFragment;
import com.lykkex.LykkeWallet.gui.fragments.mainfragments.TradingFragment_;
import com.lykkex.LykkeWallet.gui.fragments.mainfragments.WalletFragment;
import com.lykkex.LykkeWallet.gui.fragments.mainfragments.WalletFragment_;
import com.lykkex.LykkeWallet.gui.fragments.storage.UserPref_;
import com.lykkex.LykkeWallet.gui.models.SettingSinglenton;
import com.lykkex.LykkeWallet.gui.utils.Constants;
import com.lykkex.LykkeWallet.gui.utils.validation.CallBackListener;
import com.lykkex.LykkeWallet.rest.base.models.Error;
import com.lykkex.LykkeWallet.rest.internal.callback.BaseAssetCallback;
import com.lykkex.LykkeWallet.rest.internal.callback.SettingCallBack;
import com.lykkex.LykkeWallet.rest.internal.response.model.BaseAssetData;
import com.lykkex.LykkeWallet.rest.internal.response.model.BaseAssetResult;
import com.lykkex.LykkeWallet.rest.internal.response.model.SettingData;
import com.lykkex.LykkeWallet.rest.internal.response.model.SettingResult;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import retrofit2.Call;

/**
 * Created by e.kazimirova on 12.02.2016.
 */
@EActivity(R.layout.main_activity)
public class MainActivity  extends ActionBarActivity implements CallBackListener{
    private DrawerAdapter adapter;
    @ViewById DrawerLayout drawerLayout;
    @ViewById ListView drawerListView;
    @Pref
    UserPref_ pref;
    private ActionBarDrawerToggle mDrawerToggle;
    private String mTitle;
    private SettingSinglenton singlenton;

    public void onCreate(Bundle bundle){
        super.onCreate(bundle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_drawer);
    }
    @AfterViews
    public void afterViews() {
        getSetting();
        getBaseAssets();
        adapter = new DrawerAdapter(this);
        singlenton = SettingSinglenton.getInstance();

        drawerListView.setAdapter(adapter);
        drawerListView.setOnItemClickListener(new DrawerItemClickListener());

        drawerLayout.setBackgroundResource(R.color.white);
        mDrawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                R.drawable.ic_drawer,
                R.string.drawer_open,
                R.string.drawer_close
        ) {

            public void onDrawerClosed(View view) {
                getSupportActionBar().setTitle(mTitle);
                supportInvalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(mTitle);
                supportInvalidateOptionsMenu();
            }
        };

        drawerLayout.setDrawerListener(mDrawerToggle);

        selectItem(0);
    }

    private void getSetting(){
        SettingCallBack settingCallback = new SettingCallBack(this, this);
        Call<SettingData> call = LykkeApplication_.getInstance().
                getRestApi().getAppSettings(Constants.PART_AUTHORIZATION + pref.authToken().get());
        call.enqueue(settingCallback);
    }

    private void getBaseAssets(){
        BaseAssetCallback baseAssetCallback = new BaseAssetCallback(this, this);
        Call<BaseAssetData> call = LykkeApplication_.getInstance().
                getRestApi().getBaseAssets(Constants.PART_AUTHORIZATION + pref.authToken().get());
        call.enqueue(baseAssetCallback);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (mDrawerToggle.isDrawerIndicatorEnabled()) {
                    return mDrawerToggle.onOptionsItemSelected(item);
                } else {
                    onBackPressed();
                    return true;
                }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onSuccess(Object result) {
        if (result instanceof BaseAssetResult) {
            Log.e("Liza ", "get asset");
             singlenton.setBaseAssets(
                     ((BaseAssetResult) result).getAsset());
        } else if (result instanceof SettingResult) {
            singlenton.setShouldSignOrder(
                    ((SettingResult) result).getSignOrder());
            singlenton.setDepositUrl(
                    ((SettingResult) result).getDepositUrl());
            singlenton.setRefreshTimer(
                    ((SettingResult) result).getRateRefreshPeriod());
            singlenton.setBaseAssetId(
                    ((SettingResult) result).getBaseAsset().getId());
        }
    }

    @Override
    public void onFail(Object error) {

    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    private void selectItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new WalletFragment_();
                adapter.setActive(0);
                mTitle = adapter.getListModel().get(0).getName();
                break;
            case 1:
                fragment = new TradingFragment_();
                adapter.setActive(1);
                mTitle = adapter.getListModel().get(1).getName();
                break;
            case 2:
                fragment = new HistoryFragment_();
                adapter.setActive(2);
                mTitle = adapter.getListModel().get(2).getName();
                break;
            case 3:
                fragment = new SettingFragment_();
                adapter.setActive(2);
                mTitle = adapter.getListModel().get(3).getName();
                break;
            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, fragment).commit();

            drawerListView.setItemChecked(position, true);
            setTitle(mTitle);
            drawerLayout.closeDrawer(drawerListView);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void setTitle(CharSequence title) {
        getSupportActionBar().setTitle(title);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

}