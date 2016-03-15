package com.lykkex.LykkeWallet.gui.fragments.mainfragments;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.gui.LykkeApplication_;
import com.lykkex.LykkeWallet.gui.activity.BaseActivity;
import com.lykkex.LykkeWallet.gui.activity.authentication.FieldActivity_;
import com.lykkex.LykkeWallet.gui.activity.pin.EnterPinActivity_;
import com.lykkex.LykkeWallet.gui.fragments.mainfragments.setting.PersonalDataFragment;
import com.lykkex.LykkeWallet.gui.fragments.mainfragments.setting.PersonalDataFragment_;
import com.lykkex.LykkeWallet.gui.fragments.mainfragments.setting.SettingActivity;
import com.lykkex.LykkeWallet.gui.fragments.mainfragments.setting.SettingActivity_;
import com.lykkex.LykkeWallet.gui.fragments.mainfragments.setting.SettingEnum;
import com.lykkex.LykkeWallet.gui.fragments.storage.UserPref_;
import com.lykkex.LykkeWallet.gui.models.SettingSinglenton;
import com.lykkex.LykkeWallet.gui.utils.Constants;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

/**
 * Created by LIZA on 29.02.2016.
 */
@EFragment(R.layout.setting_fragment)
public class SettingFragment extends Fragment {

    @ViewById Switch switchCheck;
    @Pref  UserPref_ userPref;
    @ViewById TextView tvExit;
    @ViewById TextView tvBaseInfo;

    @AfterViews
    public void afterViews(){
        tvExit.setText(getString(R.string.exit) + " " + userPref.fullName().get());
        tvBaseInfo.setText(SettingSinglenton.getInstance().getBaseAssetSymbol());
        switchCheck.setChecked(SettingSinglenton.getInstance().isShouldSignOrder());
        switchCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), EnterPinActivity_.class);
                intent.putExtra(Constants.EXTRA_FRAGMENT_SETTING, SettingEnum.signorder);
                startActivity(intent);
            }
        });
    }

    @Click(R.id.relPersonalData)
    public void clickRelPresonal(){
        clickPersonal();
    }

    @Click(R.id.tvPersonal)
    public void clickTvPresonal(){
        clickPersonal();
    }

    @Click(R.id.imgPersonalData)
    public void clickImgPersonal(){
        clickPersonal();
    }

    @Click(R.id.relPush)
    public void clickRelPush(){
        clickPush();
    }

    @Click(R.id.imgPush)
    public void clickImgPush(){
        clickPush();
    }

    @Click(R.id.tvPush)
    public void clickTvPush(){
        clickPush();
    }

    @Click(R.id.tvBaseCurrency)
    public void clickTvBaseCurrency(){
        clickBaseCurrency();
    }

    @Click(R.id.relBaseCurrency)
    public void clickRelBaseCurrency(){
        clickBaseCurrency();
    }

    @Click(R.id.imgBaseCurrency)
    public void clickImgBaseCurrency(){
        clickBaseCurrency();
    }

    @Click(R.id.relExit)
    public void clickRelExit(){
        clickExit();
    }

    @Click(R.id.tvExit)
    public void clickTvExit(){
        clickExit();
    }

    public void clickExit(){
        userPref.clear();
        Intent intent = new Intent();
        intent.setClass(LykkeApplication_.getInstance(), FieldActivity_.class);
        getActivity().startActivity(intent);
        getActivity().finish();
    }

    public void clickBaseCurrency(){
        Intent intent = new Intent();
        intent.putExtra(Constants.EXTRA_FRAGMENT_SETTING, SettingEnum.baseasset);
        intent.setClass(getActivity(), SettingActivity_.class);
        startActivity(intent);
    }

    public void clickPush(){
        Intent intent = new Intent();
        intent.putExtra(Constants.EXTRA_FRAGMENT_SETTING, SettingEnum.pushnotifications);
        intent.setClass(getActivity(), SettingActivity_.class);
        startActivity(intent);
    }

    public void clickPersonal(){
        Intent intent = new Intent();
        intent.putExtra(Constants.EXTRA_FRAGMENT_SETTING, SettingEnum.personalData);
        intent.setClass(getActivity(), SettingActivity_.class);
        startActivity(intent);
    }


}
