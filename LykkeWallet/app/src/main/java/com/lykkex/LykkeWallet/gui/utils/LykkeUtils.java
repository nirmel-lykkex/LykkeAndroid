package com.lykkex.LykkeWallet.gui.utils;

import android.app.FragmentManager;
import android.os.Bundle;

import com.lykkex.LykkeWallet.gui.widgets.ErrorDialog;

import java.util.Random;

/**
 * Created by Murtic on 02/06/16.
 */
public class LykkeUtils {
    public static void showError(FragmentManager fragmentManager, String error) {
        showError(fragmentManager, error, null);
    }

    public static void showError(FragmentManager fragmentManager, String error, Runnable callback) {
        ErrorDialog dialog = new ErrorDialog();

        if(callback != null) {
            dialog.setCallback(callback);
        }

        Bundle bundle = new Bundle();
        bundle.putString(Constants.EXTRA_ERROR, error);
        dialog.setArguments(bundle);
        dialog.show(fragmentManager, "dlg1" + new Random((int) Constants.DELAY_5000));
    }
}
