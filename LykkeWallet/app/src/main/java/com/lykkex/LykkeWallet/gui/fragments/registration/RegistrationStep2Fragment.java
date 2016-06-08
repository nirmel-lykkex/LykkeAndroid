package com.lykkex.LykkeWallet.gui.fragments.registration;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.graphics.Point;
import android.os.Build;
import android.text.Editable;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.gui.LykkeApplication;
import com.lykkex.LykkeWallet.gui.activity.BaseActivity;
import com.lykkex.LykkeWallet.gui.customviews.StepsIndicator;
import com.lykkex.LykkeWallet.gui.fragments.BaseFragment;
import com.lykkex.LykkeWallet.gui.fragments.models.RegistrationModelGUI;
import com.lykkex.LykkeWallet.gui.fragments.storage.UserPref_;
import com.lykkex.LykkeWallet.gui.managers.UserManager;
import com.lykkex.LykkeWallet.gui.utils.LykkeUtils;
import com.lykkex.LykkeWallet.rest.registration.response.models.RegistrationData;

import org.androidannotations.annotations.AfterTextChange;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Murtic on 31/05/16.
 */
@EFragment(R.layout.registration_step_2_fragment)
public class RegistrationStep2Fragment extends BaseFragment {

    @ViewById
    StepsIndicator stepsIndicator;

    @ViewById
    EditText hintEditText;

    @ViewById
    Button buttonAction;

    @Bean
    UserManager userManager;

    @App
    LykkeApplication lykkeApplication;

    @Click(R.id.buttonAction)
    public void clickButtonAction(){
        sendRegistrationRequest();
    }

    @AfterViews
    void afterViews() {
        stepsIndicator.setCurrentStep(1);

        hintEditText.setText(userManager.getRegistrationModel().getHint());
    }

    @AfterTextChange(R.id.hintEditText)
    void onHintChange(Editable text) {
        userManager.getRegistrationModel().setHint(text.toString());

        buttonAction.setEnabled(text.toString().length() > 0);
    }

    private void sendRegistrationRequest(){
        RegistrationModelGUI model = userManager.getRegistrationModel();

        final ProgressDialog dialog = new ProgressDialog(getActivity());

        dialog.setMessage(getActivity().getString(R.string.waiting));
        dialog.setCancelable(false);

        if (model.isReady()) {
            dialog.show();

            buttonAction.setEnabled(false);

            Point point = new Point();

            getActivity().getWindowManager().getDefaultDisplay().getSize(point);

            model.setClientInfo("<android>; Model:<" + Build.MODEL +">; Os:<android>; Screen:<"+
                    point.x+"x" + point.y+">;");

            Call<RegistrationData> call = lykkeApplication.getRestApi().registration(model);

            call.enqueue(new Callback<RegistrationData>() {
                @Override
                public void onResponse(Call<RegistrationData> call, Response<RegistrationData> response) {
                    buttonAction.setEnabled(true);
                    dialog.dismiss();

                    if(!response.isSuccess() || response.body().getResult() == null) {
                        onFailure(call, new RuntimeException("Unexpected error occured."));

                        return;
                    }

                    RegistrationData data = response.body();

                    new UserPref_(getActivity()).authToken().put(data.getResult().getToken());
                    new UserPref_(getActivity()).fullName().put(data.getResult().getPersonalData().getFullName());

                    getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

                    ((BaseActivity) getActivity()).initFragment(new RegistrationStep3Fragment_(), null);

                    userManager.setRegistrationResult(data.getResult());
                }

                @Override
                public void onFailure(Call<RegistrationData> call, Throwable t) {
                    buttonAction.setEnabled(true);
                    dialog.dismiss();

                    Log.e(RegistrationStep2Fragment.class.getSimpleName(), "Error while registering user.", t);

                    LykkeUtils.showError(getFragmentManager(), "Unexpected error while registering user.");
                }
            });
        }
    }
}
