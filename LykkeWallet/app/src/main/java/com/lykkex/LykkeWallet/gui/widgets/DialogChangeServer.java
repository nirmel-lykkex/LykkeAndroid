package com.lykkex.LykkeWallet.gui.widgets;

import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.gui.LykkeApplication_;
import com.lykkex.LykkeWallet.gui.fragments.storage.UserPref_;
import com.lykkex.LykkeWallet.rest.camera.callback.SendDocumentsDataCallback;

import retrofit2.Call;
import retrofit2.http.Path;

/**
 * Created by LIZA on 22.02.2016.
 */
public class DialogChangeServer extends DialogFragment implements View.OnClickListener{


    private UserPref_ pref = new UserPref_(LykkeApplication_.getInstance());
    private int idServer;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        idServer = pref.idServer().get();
        View v = inflater.inflate(R.layout.change_server, null);
        RadioButton radioTest = (RadioButton)v.findViewById(R.id.radioTest);
        RadioButton radioDev = (RadioButton)v.findViewById(R.id.radioDev);
        RadioButton radioDemo = (RadioButton)v.findViewById(R.id.radioDemo);

        switch (idServer) {
            case 0:
                radioDev.setChecked(true);
                break;
            case 1:
                radioTest.setChecked(true);
                break;
            case 2:
                radioDemo.setChecked(true);
                break;
        }
        Button btnSave = (Button) v.findViewById(R.id.saveBtn);

        btnSave.setOnClickListener(this);

        radioDemo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                idServer = 2;
            }
        });

        radioDev.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                idServer = 0;
            }
        });

        radioTest.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                idServer = 1;
            }
        });
        return v;
    }

    public void onClick(View v) {
        pref.idServer().put(idServer);
        LykkeApplication_.getInstance().setUpServer();
        dismiss();
    }

    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }

    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
    }


}
