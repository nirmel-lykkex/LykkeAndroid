package com.lykkex.LykkeWallet.gui.fragments.camerascreen;

import android.os.Bundle;
import android.widget.TextView;

import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.gui.activity.BaseActivity;
import com.lykkex.LykkeWallet.gui.utils.Constants;
import com.lykkex.LykkeWallet.gui.utils.validation.CallBackListener;
import com.lykkex.LykkeWallet.rest.camera.callback.SendDocumentsDataCallback;
import com.lykkex.LykkeWallet.rest.camera.request.models.CameraModel;
import com.lykkex.LykkeWallet.rest.camera.request.models.CameraType;
import com.lykkex.LykkeWallet.rest.camera.response.models.PersonData;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import retrofit2.Call;

/**
 * Created by Murtic on 31/05/16.
 */
@EFragment(R.layout.camera_selfie_fragment)
public class CameraProofOfAddressFragment extends CameraBaseFragment {

    @ViewById
    TextView tvInfo;

    @AfterViews
    void afterViews() {
        super.afterViews();

        userManager.getCameraModel().setType(CameraType.ProofOfAddress.toString());

        tvInfo.setText(getString(R.string.proof_adress));
    }

    protected void sendImage(){
        Call<PersonData> call  = lykkeApplication.getRestApi().kysDocuments(userManager.getCameraModel());

        SendDocumentsDataCallback callback = new SendDocumentsDataCallback(new CallBackListener<PersonData>() {
            @Override
            public void onSuccess(PersonData result) {
                progressDialog.dismiss();
                userManager.getCameraResult().setProofOfAddress(false);

                Bundle bundle = new Bundle();
                bundle.putSerializable(Constants.EXTRA_CAMERA_MODEL_GUI, userManager.getCameraResult());

                ((BaseActivity)getActivity()).initFragment(new SubmitFragment_(), bundle, false);
            }

            @Override
            public void onFail(Object error) {
                dialog.dismiss();
                progressDialog.dismiss();
            }
        }, getActivity());

        call.enqueue(callback);

        showProgress(call, callback);
    }
}
