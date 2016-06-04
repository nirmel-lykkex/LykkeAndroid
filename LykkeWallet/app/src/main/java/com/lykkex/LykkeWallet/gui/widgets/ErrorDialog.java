package com.lykkex.LykkeWallet.gui.widgets;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.gui.utils.Constants;

/**
 * Created by e.kazimirova on 24.03.2016.
 */

public class ErrorDialog extends DialogFragment implements View.OnClickListener {

    private Runnable callback;

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

        TextView btnUnderstand = (TextView) v.findViewById(R.id.btnUnderstand);
        btnUnderstand.setOnClickListener(this);

        return v;
    }

    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnUnderstand:
                dismiss();

                if(callback != null) {
                    callback.run();
                }

                break;
        }
    }

    public Runnable getCallback() {
        return callback;
    }

    public void setCallback(Runnable callback) {
        this.callback = callback;
    }
}