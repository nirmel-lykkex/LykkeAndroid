package com.lykkex.LykkeWallet.gui.fragments.mainfragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.StateListDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.gui.LykkeApplication;
import com.lykkex.LykkeWallet.gui.activity.authentication.SignInActivity_;
import com.lykkex.LykkeWallet.gui.activity.paymentflow.SettingActivity_;
import com.lykkex.LykkeWallet.gui.activity.pin.EnterPinActivity_;
import com.lykkex.LykkeWallet.gui.fragments.BaseFragment;
import com.lykkex.LykkeWallet.gui.fragments.mainfragments.enums.SettingEnum;
import com.lykkex.LykkeWallet.gui.fragments.storage.UserPref_;
import com.lykkex.LykkeWallet.gui.managers.SettingManager;
import com.lykkex.LykkeWallet.gui.utils.Constants;
import com.lykkex.LykkeWallet.rest.appinfo.response.model.AppInfoData;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by LIZA on 29.02.2016.
 */
@EFragment(R.layout.setting_fragment)
public class SettingFragment extends BaseFragment {

    protected static final int REQUEST_CALL_PHONE_PERMISSION = 201;

    @ViewById
    Switch switchCheck;

    @Pref
    UserPref_ userPref;

    @ViewById
    TextView tvExit;

    @ViewById
    TextView tvBaseInfo;

    @ViewById
    TextView tvAppVersion;

    @App
    LykkeApplication lykkeApplication;

    @AfterViews
    public void afterViews(){
        int currentapiVersion = Build.VERSION.SDK_INT;
        if (currentapiVersion >= Build.VERSION_CODES.LOLLIPOP){
            switchCheck.setBackgroundResource(R.drawable.switch_background);
            switchCheck.setTextAppearance(getActivity(),R.style.switchStyle);
        }
        int colorOn = getActivity().getResources().getColor(R.color.blue_color);
        int colorOff =getActivity().getResources().getColor(R.color.grey_text);
        int colorDisabled = getActivity().getResources().getColor(R.color.grey_text);
        StateListDrawable thumbStates = new StateListDrawable();
        thumbStates.addState(new int[]{android.R.attr.state_checked}, new ColorDrawable(colorOn));
        thumbStates.addState(new int[]{-android.R.attr.state_enabled}, new ColorDrawable(colorDisabled));
        thumbStates.addState(new int[]{}, new ColorDrawable(colorOff));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            switchCheck.setThumbDrawable(thumbStates);
        }
    }
    public void onStart(){
        super.onStart();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                tvExit.setText(getString(R.string.exit) + " " + userPref.email().get());
                tvBaseInfo.setText(SettingManager.getInstance().getBaseAssetId());
                switchCheck.setChecked(SettingManager.getInstance().isShouldSignOrder());
                switchCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        if (b != SettingManager.getInstance().isShouldSignOrder()) {
                            switchCheck.setChecked(!b);
                            Intent intent = new Intent();
                            intent.setClass(getActivity(), EnterPinActivity_.class);
                            intent.putExtra(Constants.EXTRA_FRAGMENT, SettingEnum.signorder);
                            startActivity(intent);
                        }
                    }
                });

                try {
                    final PackageInfo pInfo = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0);

                    tvAppVersion.setText(getString(R.string.app_version, pInfo.versionName, pInfo.versionCode));

                    Call<AppInfoData> call = lykkeApplication.getRestApi().getAppInfo();
                    call.enqueue(new Callback<AppInfoData>() {
                        @Override
                        public void onResponse(Call<AppInfoData> call, Response<AppInfoData> response) {
                            if(response.body() instanceof AppInfoData && isAdded()) {
                                tvAppVersion.setText(getString(R.string.api_app_version, pInfo.versionName, pInfo.versionCode, response.body().getResult().getAppVersion()));
                            }
                        }

                        @Override
                        public void onFailure(Call<AppInfoData> call, Throwable t) {
                        }
                    });

                } catch (PackageManager.NameNotFoundException e) {
                    Log.e("ERROR", "Error while loading version", e);
                }
            }
        }, Constants.DELAY_500);
    }

    @Click({R.id.relPersonalData, R.id.tvPersonal, R.id.imgPersonalData})
    public void clickRelPresonal(){
        clickPersonal();
    }

    @Click({R.id.relPush, R.id.imgPush, R.id.tvPush})
    public void clickRelPush(){
        clickPush();
    }

    @Click({R.id.tvBaseCurrency, R.id.relBaseCurrency, R.id.imgBaseCurrency})
    public void clickTvBaseCurrency(){
        clickBaseCurrency();
    }

    @Click({R.id.relTermsOfUse, R.id.termsOfUse})
    public void clickOnTermsOfUse(){
        clickTermsOfUse();
    }

    @Click({R.id.relExit, R.id.tvExit})
    public void clickRelExit(){
        clickExit();
    }

    @Click(R.id.btnCallSupport)
    public void onCallSupport(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (getActivity().checkSelfPermission(Manifest.permission.CALL_PHONE)
                    != PackageManager.PERMISSION_GRANTED) {

                requestPermissions(new String[]{Manifest.permission.CALL_PHONE},
                        REQUEST_CALL_PHONE_PERMISSION);

                return;
            }
        }

        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:+41 61 588 04 02"));
        startActivity(intent);
    }

    public void clickExit(){
        userPref.clear();
        userPref.isDepositVisible().put(false);

        Intent intent = new Intent();
        intent.setClass(lykkeApplication, SignInActivity_.class);
        getActivity().startActivity(intent);
        getActivity().finish();
    }

    public void clickTermsOfUse(){
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse("https://lykkewallet.lykkex.com/terms_of_use.html"));
        startActivity(i);
    }

    public void clickBaseCurrency(){
        Intent intent = new Intent();
        intent.putExtra(Constants.EXTRA_FRAGMENT, SettingEnum.baseasset);
        intent.setClass(getActivity(), SettingActivity_.class);
        startActivity(intent);
    }

    public void clickPush(){
        Intent intent = new Intent();
        intent.putExtra(Constants.EXTRA_FRAGMENT, SettingEnum.pushnotifications);
        intent.setClass(getActivity(), SettingActivity_.class);
        startActivity(intent);
    }

    public void clickPersonal(){
        Intent intent = new Intent();
        intent.putExtra(Constants.EXTRA_FRAGMENT, SettingEnum.personalData);
        intent.setClass(getActivity(), SettingActivity_.class);
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CALL_PHONE_PERMISSION:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    onCallSupport();
                }
        }
    }
}
