package com.lykkex.LykkeWallet.gui.widgets;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.gui.LykkeApplication_;
import com.lykkex.LykkeWallet.gui.fragments.storage.UserPref_;
import com.lykkex.LykkeWallet.gui.models.WalletSinglenton;
import com.lykkex.LykkeWallet.gui.utils.Constants;
import com.lykkex.LykkeWallet.gui.utils.validation.CallBackListener;
import com.lykkex.LykkeWallet.rest.history.reposnse.model.MarketData;
import com.lykkex.LykkeWallet.rest.pin.callback.CallBackPinSignIn;
import com.lykkex.LykkeWallet.rest.pin.response.model.SecurityData;
import com.lykkex.LykkeWallet.rest.wallet.callback.CashOutCallBack;
import com.lykkex.LykkeWallet.rest.wallet.request.models.CashOut;
import com.lykkex.LykkeWallet.rest.wallet.response.models.AssetsWallet;

import retrofit2.Call;

/**
 * Created by e.kazimirova on 24.03.2016.
 */

public class ConfirmCashOutDialog extends DialogFragment implements View.OnClickListener, CallBackListener {

    private String bitCoinAddress;
    private String amount;
    private AssetsWallet assetsWallet;

    private TextView valueBitcoinAddress;
    private TextView valueAmount;
    private ImageView imgDot1;
    private ImageView imgProgress;
    private ImageView imgDot2;
    private ImageView imgDot3;
    private ImageView imgDot4;
    private  TextView tvCancel;
    private  TextView tvCancelBottom;
    private RelativeLayout relProgress;
    private TextView tvProgress;
    private RelativeLayout relPin;
    private int count = 0;

    UserPref_ userPref = new UserPref_(LykkeApplication_.getInstance());



    private String pin = "";

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();

        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);

            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        View v = inflater.inflate(R.layout.confirm_cash_out_dialog, null);
        valueBitcoinAddress = (TextView) v.findViewById(R.id.valueBitcoinAddress);

        valueBitcoinAddress.setMovementMethod(new ScrollingMovementMethod());

        valueAmount = (TextView) v.findViewById(R.id.valueAmount);
        imgDot1 = (ImageView) v.findViewById(R.id.imgDot1);
        imgDot2 = (ImageView) v.findViewById(R.id.imgDot2);
        imgDot3 = (ImageView) v.findViewById(R.id.imgDot3);
        imgDot4 = (ImageView) v.findViewById(R.id.imgDot4);
        relPin = (RelativeLayout) v.findViewById(R.id.relPin);
        imgProgress = (ImageView) v.findViewById(R.id.imgProgress);
        relProgress = (RelativeLayout) v.findViewById(R.id.relProgress);
        tvProgress = (TextView) v.findViewById(R.id.tvProgress);

        bitCoinAddress = getArguments().getString(Constants.EXTRA_QR_CODE_READ);
        amount = getArguments().getString(Constants.EXTRA_AMOUNT);
        assetsWallet = (AssetsWallet) getArguments().getSerializable(Constants.EXTRA_ASSET);

        setUpValues();

        tvCancel = (TextView)v.findViewById(R.id.tvCancel);
        tvCancelBottom = (TextView) v.findViewById(R.id.tvCancelBottom);
        RelativeLayout rel1 = (RelativeLayout) v.findViewById(R.id.rel1);
        ImageView img1 = (ImageView) v.findViewById(R.id.img1);

        RelativeLayout rel2 = (RelativeLayout) v.findViewById(R.id.rel2);
        ImageView img2 = (ImageView) v.findViewById(R.id.img2);

        RelativeLayout rel3 = (RelativeLayout) v.findViewById(R.id.rel3);
        ImageView img3 = (ImageView) v.findViewById(R.id.img3);

        RelativeLayout rel4 = (RelativeLayout) v.findViewById(R.id.rel4);
        ImageView img4 = (ImageView) v.findViewById(R.id.img4);

        RelativeLayout rel5 = (RelativeLayout) v.findViewById(R.id.rel5);
        ImageView img5 = (ImageView) v.findViewById(R.id.img5);

        RelativeLayout rel6 = (RelativeLayout) v.findViewById(R.id.rel6);
        ImageView img6 = (ImageView) v.findViewById(R.id.img6);

        RelativeLayout rel7 = (RelativeLayout) v.findViewById(R.id.rel7);
        ImageView img7 = (ImageView) v.findViewById(R.id.img7);

        RelativeLayout rel8 = (RelativeLayout) v.findViewById(R.id.rel8);
        ImageView img8 = (ImageView) v.findViewById(R.id.img8);

        RelativeLayout rel9 = (RelativeLayout) v.findViewById(R.id.rel9);
        ImageView img9 = (ImageView) v.findViewById(R.id.img9);

        RelativeLayout rel0 = (RelativeLayout) v.findViewById(R.id.rel0);
        ImageView img0 = (ImageView) v.findViewById(R.id.img0);

        RelativeLayout relRemove = (RelativeLayout) v.findViewById(R.id.relRemove);
        ImageView imgRemove = (ImageView) v.findViewById(R.id.imgRemove);

        tvCancel.setOnClickListener(this);
        tvCancelBottom.setOnClickListener(this);
        img1.setOnClickListener(this);
        rel1.setOnClickListener(this);
        img2.setOnClickListener(this);
        rel2.setOnClickListener(this);
        img3.setOnClickListener(this);
        rel3.setOnClickListener(this);
        img4.setOnClickListener(this);
        rel4.setOnClickListener(this);
        img5.setOnClickListener(this);
        rel5.setOnClickListener(this);
        img6.setOnClickListener(this);
        rel6.setOnClickListener(this);
        img7.setOnClickListener(this);
        rel7.setOnClickListener(this);
        img8.setOnClickListener(this);
        rel8.setOnClickListener(this);
        img9.setOnClickListener(this);
        rel9.setOnClickListener(this);
        img0.setOnClickListener(this);
        rel0.setOnClickListener(this);
        imgRemove.setOnClickListener(this);
        relRemove.setOnClickListener(this);

        getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, android.view.KeyEvent event) {

                if ((keyCode == android.view.KeyEvent.KEYCODE_BACK)) {

                    return true;
                } else
                    return false;
            }
        });
        return v;
    }





    public void onStop(){
        super.onStop();
    }


    private void setUpValues(){
        valueAmount.setText(amount);
        valueBitcoinAddress.setText(bitCoinAddress);
        setUpCircles();
    }

    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tvCancel:
                dismiss();
                setUpCircles();
                break;
            case R.id.tvCancelBottom:
                dismiss();
                setUpCircles();
                break;
            case R.id.rel1:
            case R.id.img1:
                pin +="1";
                setUpCircles();
                break;
            case R.id.rel2:
            case R.id.img2:
                pin +="2";
                setUpCircles();
                break;
            case R.id.rel3:
            case R.id.img3:
                pin +="3";
                setUpCircles();
                break;
            case R.id.rel4:
            case R.id.img4:
                pin +="4";
                setUpCircles();
                break;
            case R.id.rel5:
            case R.id.img5:
                pin +="5";
                setUpCircles();
                break;
            case R.id.rel6:
            case R.id.img6:
                pin +="6";
                setUpCircles();
                break;
            case R.id.rel7:
            case R.id.img7:
                pin +="7";
                setUpCircles();
                break;
            case R.id.rel8:
            case R.id.img8:
                pin +="8";
                setUpCircles();
                break;
            case R.id.rel9:
            case R.id.img9:
                pin +="9";
                setUpCircles();
                break;
            case R.id.rel0:
            case R.id.img0:
                pin +="0";
                setUpCircles();
                break;
            case R.id.relRemove:
            case R.id.imgRemove:
                if (pin.length() > 0) {
                    pin = pin.substring(0, pin.length() - 1);
                }
                setUpCircles();
                break;
        }

    }

    private void setUpCircles(){
        switch (pin.length()){
            case 0:
                clearSetupCircule();
                break;
            case 1:
                imgDot1.setImageResource(R.drawable.pin_setup);
                imgDot2.setImageResource(R.drawable.pin_un_setup);
                imgDot3.setImageResource(R.drawable.pin_un_setup);
                imgDot4.setImageResource(R.drawable.pin_un_setup);
                break;
            case 2:
                imgDot1.setImageResource(R.drawable.pin_setup);
                imgDot2.setImageResource(R.drawable.pin_setup);
                imgDot3.setImageResource(R.drawable.pin_un_setup);
                imgDot4.setImageResource(R.drawable.pin_un_setup);
                break;
            case 3:
                imgDot1.setImageResource(R.drawable.pin_setup);
                imgDot2.setImageResource(R.drawable.pin_setup);
                imgDot3.setImageResource(R.drawable.pin_setup);
                imgDot4.setImageResource(R.drawable.pin_un_setup);
                break;
            case 4:
                setUpVisibility(View.GONE, View.VISIBLE);
                count +=1;
                tvProgress.setText(R.string.pin_entered);
                imgDot1.setImageResource(R.drawable.pin_setup);
                imgDot2.setImageResource(R.drawable.pin_setup);
                imgDot3.setImageResource(R.drawable.pin_setup);
                imgDot4.setImageResource(R.drawable.pin_setup);
                CallBackPinSignIn callback = new CallBackPinSignIn(this, getActivity());
                Call<SecurityData> call  = LykkeApplication_.getInstance().getRestApi().
                        signInPinSecurite(pin);
                call.enqueue(callback);
                clearSetupCircule();
                break;
        }
    }

    private void setUpVisibility(int visibility, int visibilityRel) {
        relPin.setVisibility(visibility);
        tvCancel.setVisibility(visibility);
        tvCancelBottom.setVisibility(visibility);
        if (visibilityRel == View.VISIBLE) {
            RotateAnimation anim = new RotateAnimation(0.0f, 360.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);

            anim.setInterpolator(new LinearInterpolator());
            anim.setRepeatCount(Animation.INFINITE);
            anim.setDuration(700);

            imgProgress.startAnimation(anim);
        }
        relProgress.setVisibility(visibilityRel);
    }

    private void clearSetupCircule(){
        pin = "";
        imgDot1.setImageResource(R.drawable.pin_un_setup);
        imgDot2.setImageResource(R.drawable.pin_un_setup);
        imgDot3.setImageResource(R.drawable.pin_un_setup);
        imgDot4.setImageResource(R.drawable.pin_un_setup);
    }

    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }

    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
    }

    private void generateAsset(){
        setUpVisibility(View.GONE, View.VISIBLE);
        tvProgress.setText(R.string.sending_order);
        CashOutCallBack cashOutCallBack = new CashOutCallBack(this, getActivity());
        CashOut cashOut = new CashOut(getHash(), amount, assetsWallet.getAssetPairId());
        Call<MarketData> call = LykkeApplication_.getInstance().getRestApi().postCachOut(
                Constants.PART_AUTHORIZATION + userPref.authToken().get(),cashOut);
        call.enqueue(cashOutCallBack);

    }

    private String getHash(){
        if (isColoredMultising()){
            return WalletSinglenton.getInstance().getResult().getColoredMultiSig();
        } else {
            return WalletSinglenton.getInstance().getResult().getMultiSig();
        }
    }
    private boolean isColoredMultising(){
        if (assetsWallet.getIssuerId().equals(Constants.BTC)){
            return  false;
        } else if (assetsWallet.getIssuerId().equals(Constants.LKE)) {
            return true;
        }
        return false;
    }

    @Override
    public void onSuccess(Object result) {
        if (result instanceof SecurityData) {
            setUpVisibility(View.VISIBLE, View.GONE);
            if (((SecurityData) result).getResult().isPassed()) {
                generateAsset();
            } else if (!((SecurityData) result).getResult().isPassed()){
                Toast.makeText(getActivity(), getString(R.string.wrong_pin), Toast.LENGTH_LONG).show();
                if (count >= 3){
                    dismiss();
                }
            }
        } else {
            dismiss();
            getActivity().finish();
        }
    }

    @Override
    public void onFail(Object error) {

    }
}