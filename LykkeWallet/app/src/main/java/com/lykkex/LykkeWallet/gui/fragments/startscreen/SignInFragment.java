package com.lykkex.LykkeWallet.gui.fragments.startscreen;

import android.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.lykkex.LykkeWallet.BuildConfig;
import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.gui.customviews.RichEditText;
import com.lykkex.LykkeWallet.gui.fragments.models.RegistrationModelGUI;
import com.lykkex.LykkeWallet.gui.managers.UserManager;
import com.lykkex.LykkeWallet.gui.utils.Constants;
import com.lykkex.LykkeWallet.gui.utils.validation.CallBackListener;
import com.lykkex.LykkeWallet.gui.utils.validation.EmailTextWatcher;
import com.lykkex.LykkeWallet.gui.widgets.DialogChangeServer;
import com.lykkex.LykkeWallet.rest.registration.response.models.AcountExistResult;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.Random;

/**
 * Created by Murtic on 31/05/16.
 */
@EFragment(R.layout.sign_in_fragment)
public class SignInFragment extends Fragment {

    @ViewById
    RichEditText emailRichEditText;

    @ViewById
    Button buttonAction;

    @ViewById
    ProgressBar progressBar;

    @ViewById
    ImageView whatShouldKnowImageView;

    @ViewById
    Button btnChangeServer;

    @Click(R.id.btnChangeServer)
    public void clickBtnChangeServer(){
        DialogChangeServer dialogChangeServer = new DialogChangeServer();
        dialogChangeServer.show(getActivity().getFragmentManager(),
                "dlg1" +new Random((int) Constants.DELAY_5000));
    }

    @AfterViews
    void afterViews() {
        if (BuildConfig.CHANGE_SERVER){
            btnChangeServer.setVisibility(View.VISIBLE);
        } else {
            btnChangeServer.setVisibility(View.GONE);
        }

        final RegistrationModelGUI registrationModel = UserManager.getInstance().getRegistrationModel();

        buttonAction.setText(R.string.action_sign_up);

        if (getArguments() != null && getArguments().containsKey(Constants.EXTRA_EMAIL)) {
            registrationModel.setEmail(getArguments().getString(Constants.EXTRA_EMAIL));

            getArguments().clear();
        }

        emailRichEditText.setText(registrationModel.getEmail());
        emailRichEditText.setHint(R.string.email_hint);

        emailRichEditText.addTextWatcher(new EmailTextWatcher(new CallBackListener<AcountExistResult>() {
            @Override
            public void onSuccess(AcountExistResult result) {
                if (result != null) {
                    if ( emailRichEditText.getText().toString().equals(result.getEmail())) {
                        if (result.isEmailRegistered()) {
                            buttonAction.setText(R.string.action_sign_in);

                            registrationModel.setIsReady(true);
                        } else {
                            buttonAction.setText(R.string.action_sign_up);
                        }

                        registrationModel.setIsReady(true);

                        buttonAction.setEnabled(true);

                        emailRichEditText.setReady(registrationModel.isReady());
                    } else {
                        buttonAction.setEnabled(false);
                    }
                }
            }

            @Override
            public void onFail(Object error) {
                Log.e("ERROR", "Error while checking if user is registered: " + error.toString());
            }
        }, emailRichEditText, progressBar, buttonAction));
    }
}
