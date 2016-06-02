package com.lykkex.LykkeWallet.gui.fragments.startscreen;

import android.app.Fragment;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lykkex.LykkeWallet.BuildConfig;
import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.gui.LykkeApplication;
import com.lykkex.LykkeWallet.gui.activity.BaseActivity;
import com.lykkex.LykkeWallet.gui.customviews.RichEditText;
import com.lykkex.LykkeWallet.gui.fragments.models.RegistrationModelGUI;
import com.lykkex.LykkeWallet.gui.managers.UserManager;
import com.lykkex.LykkeWallet.gui.utils.Constants;
import com.lykkex.LykkeWallet.gui.utils.validation.CallBackListener;
import com.lykkex.LykkeWallet.gui.utils.validation.EmailTextWatcher;
import com.lykkex.LykkeWallet.gui.widgets.DialogChangeServer;
import com.lykkex.LykkeWallet.rest.emailverify.request.model.VerifyEmailRequest;
import com.lykkex.LykkeWallet.rest.emailverify.response.model.VerifyCodeData;
import com.lykkex.LykkeWallet.rest.emailverify.response.model.VerifyEmailData;
import com.lykkex.LykkeWallet.rest.registration.response.models.AcountExistResult;

import org.androidannotations.annotations.AfterTextChange;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.TextChange;
import org.androidannotations.annotations.ViewById;

import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Murtic on 31/05/16.
 */
@EFragment(R.layout.confirm_registration_fragment)
public class ConfirmRegistrationFragment extends Fragment {

    @ViewById
    EditText codeEditText;

    @ViewById
    Button buttonAction;

    @ViewById
    ProgressBar progressBar;

    @ViewById
    TextView confirmCodeMessage;

    @Bean
    UserManager userManager;

    @App
    LykkeApplication lykkeApplication;

    @Click(R.id.buttonAction)
    public void clickButtonAction(){
        Call<VerifyCodeData> call = lykkeApplication.getRestApi().verifyCode(userManager.getRegistrationModel().getEmail(), codeEditText.getText().toString());

        call.enqueue(new Callback<VerifyCodeData>() {
            @Override
            public void onResponse(Call<VerifyCodeData> call, Response<VerifyCodeData> response) {
                Log.d("DEBUG", "NEXT!");
            }

            @Override
            public void onFailure(Call<VerifyCodeData> call, Throwable t) {
                // TODO: Show popup
            }
        });
    }

    @AfterViews
    void afterViews() {
        confirmCodeMessage.setText(getResources().getString(R.string.confirm_code_message, userManager.getRegistrationModel().getEmail()));
    }

    @AfterTextChange(R.id.codeEditText)
    void onCodeTextChange(Editable text) {
        buttonAction.setEnabled(text.toString().length() > 0);
    }
}
