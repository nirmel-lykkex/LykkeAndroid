package com.lykkex.LykkeWallet.gui.fragments.mainfragments.wallet;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.encoder.ByteMatrix;
import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.gui.LykkeApplication_;
import com.lykkex.LykkeWallet.gui.activity.BaseActivity;
import com.lykkex.LykkeWallet.gui.fragments.BaseFragment;
import com.lykkex.LykkeWallet.gui.fragments.storage.UserPref_;
import com.lykkex.LykkeWallet.gui.models.WalletSinglenton;
import com.lykkex.LykkeWallet.gui.utils.Constants;
import com.lykkex.LykkeWallet.rest.trading.callback.EmailCallBack;
import com.lykkex.LykkeWallet.rest.trading.response.model.EmailData;
import com.lykkex.LykkeWallet.rest.trading.response.model.SendEmail;
import com.lykkex.LykkeWallet.rest.wallet.response.models.AssetsWallet;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.w3c.dom.Text;

import java.util.Hashtable;

import retrofit2.Call;

/**
 * Created by LIZA on 15.04.2016.
 */
@EFragment(R.layout.fragment_qr_code)
public class QrCodeFragment extends BaseFragment{

    private AssetsWallet assetsWallet;
    private String hashCode;
    @Pref UserPref_ userPref;
    @ViewById ImageView qrCodeImg;
    @ViewById TextView tvHashCode;
    @ViewById TextView tvWalletAddress;

    @AfterViews
    public void afterViews(){
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        assetsWallet = (AssetsWallet) getArguments().getSerializable(Constants.EXTRA_ASSET);
        if (isColoredMultising()){
            hashCode = WalletSinglenton.getInstance().getResult().getColoredMultiSig();
        } else {
            hashCode = WalletSinglenton.getInstance().getResult().getMultiSig();
        }
        actionBar.setTitle(getString(R.string.deposit_bitcoin) + " " + assetsWallet.getAssetPairId() );

        tvWalletAddress.setAlpha((float) 0.6);
        tvHashCode.setText(hashCode);
        try {
            Bitmap qrCode = generateQrCode(hashCode);
            qrCodeImg.setImageBitmap(qrCode);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    public static Bitmap generateQrCode(String myCodeText) throws WriterException {
        Hashtable<EncodeHintType, ErrorCorrectionLevel> hintMap = new Hashtable<EncodeHintType, ErrorCorrectionLevel>();
        hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H); // H = 30% damage

        QRCodeWriter qrCodeWriter = new QRCodeWriter();

        int size = 256;

        BitMatrix bitMatrix = qrCodeWriter.encode(myCodeText, BarcodeFormat.QR_CODE, size, size, hintMap);
        int width = bitMatrix.getWidth();
        Bitmap bmp = Bitmap.createBitmap(width, width, Bitmap.Config.RGB_565);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < width; y++) {
                bmp.setPixel(y, x, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
            }
        }
        return bmp;
    }

    private boolean isColoredMultising(){
        if (assetsWallet.getIssuerId().equals(Constants.BTC)){
            return  false;
        } else if (assetsWallet.getIssuerId().equals(Constants.LKE)) {
            return true;
        }
        return false;
    }

    @Click(R.id.btnEmailMe)
    public void clickEmailMe(){
        EmailCallBack callBack = new EmailCallBack(this, getActivity());
        Call<EmailData> call = LykkeApplication_.getInstance().getRestApi().sendBlockchainEmail(
                Constants.PART_AUTHORIZATION + userPref.authToken().get()
        );
        call.enqueue(callBack);
    }

    @Click(R.id.btnCopy)
    public void clickCopy(){
        ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(
                Activity.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText(hashCode, hashCode);
        clipboard.setPrimaryClip(clip);
    }

    @Override
    public void initOnBackPressed() {
        ((BaseActivity)getActivity()).initFragment(new TradingWalletFragment_(),getArguments());
    }

    @Override
    public void onSuccess(Object result) {
        if (result instanceof SendEmail) {
            Toast.makeText(getActivity(), String.format(getString(R.string.check_email),
                    ((SendEmail) result).getEmail()), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onFail(Object error) {

    }

    @Override
    public void onConsume(Object o) {

    }
}
