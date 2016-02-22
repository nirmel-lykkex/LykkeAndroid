package com.lykkex.LykkeWallet.gui.widgets;

import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.rest.camera.callback.SendDocumentsDataCallback;

import retrofit2.Call;

/**
 * Created by LIZA on 22.02.2016.
 */
public class DialogProgress extends DialogFragment implements View.OnClickListener {

    private Call currentCall;
    private SendDocumentsDataCallback callBackSendDocuments;

    public void setUpCurrentCall(Call call, SendDocumentsDataCallback callBackSendDocuments){
        this.currentCall = call;
        this.callBackSendDocuments = callBackSendDocuments;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        View v = inflater.inflate(R.layout.dialog, null);
        v.findViewById(R.id.tvInfo).setOnClickListener(this);

        return v;
    }

    public void onClick(View v) {
        if (currentCall != null) {
            Log.e("qa ", "current call should be cancel");
            callBackSendDocuments.cancel();
            currentCall.cancel();
        }
        dismiss();
    }

    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }

    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
    }
}
