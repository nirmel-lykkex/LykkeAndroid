package com.lykkex.LykkeWallet.gui.widgets;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.gui.LykkeApplication_;
import com.lykkex.LykkeWallet.gui.activity.BaseActivity;
import com.lykkex.LykkeWallet.gui.activity.authentication.FieldActivity_;
import com.lykkex.LykkeWallet.gui.fragments.mainfragments.tradings.DealResultFragment_;
import com.lykkex.LykkeWallet.gui.fragments.storage.UserPref_;
import com.lykkex.LykkeWallet.gui.models.SettingSinglenton;
import com.lykkex.LykkeWallet.gui.utils.Constants;
import com.lykkex.LykkeWallet.gui.utils.validation.CallBackListener;
import com.lykkex.LykkeWallet.rest.pin.callback.CallBackPinSignIn;
import com.lykkex.LykkeWallet.rest.pin.response.model.SecurityData;
import com.lykkex.LykkeWallet.rest.trading.callback.AssetPairRateCallBack;
import com.lykkex.LykkeWallet.rest.trading.callback.PurchaseAssetCallBack;
import com.lykkex.LykkeWallet.rest.trading.request.model.MakeTradeModel;
import com.lykkex.LykkeWallet.rest.trading.response.model.OrderData;
import com.lykkex.LykkeWallet.rest.trading.response.model.OrderResult;
import com.lykkex.LykkeWallet.rest.trading.response.model.RateData;
import com.lykkex.LykkeWallet.rest.trading.response.model.RateResult;
import com.lykkex.LykkeWallet.rest.trading.response.model.RatesData;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

import retrofit2.Call;

/**
 * Created by e.kazimirova on 24.03.2016.
 */

public class ErrorDialog extends DialogFragment implements View.OnClickListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        View v = inflater.inflate(R.layout.error_popup, null);

        TextView tvError = (TextView) v.findViewById(R.id.tvError);
        tvError.setText(getArguments().getString(Constants.EXTRA_ERROR));

        Button btnUnderstand = (Button) v.findViewById(R.id.btnUnderstand);
        btnUnderstand.setOnClickListener(this);
        return v;
    }



    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnUnderstand:
                dismiss();
                Intent intent = new Intent();
                intent.setClass(LykkeApplication_.getInstance(), FieldActivity_.class);
                startActivity(intent);
                getActivity().finish();

                break;
        }

    }

}