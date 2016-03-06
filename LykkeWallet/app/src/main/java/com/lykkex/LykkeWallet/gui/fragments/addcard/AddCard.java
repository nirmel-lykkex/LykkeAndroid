package com.lykkex.LykkeWallet.gui.fragments.addcard;

import android.app.Fragment;
import android.content.res.Configuration;
import android.support.v4.view.ViewPager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.gui.LykkeApplication_;
import com.lykkex.LykkeWallet.gui.fragments.BaseFragment;
import com.lykkex.LykkeWallet.gui.fragments.storage.UserPref_;
import com.lykkex.LykkeWallet.gui.utils.validation.CardNumberTextWatcher;
import com.lykkex.LykkeWallet.gui.utils.validation.MonthYearTextWatcher;
import com.lykkex.LykkeWallet.gui.utils.validation.SimpleTextWatcher;
import com.lykkex.LykkeWallet.gui.utils.Constants;
import com.lykkex.LykkeWallet.gui.utils.validation.CallBackListener;
import com.lykkex.LykkeWallet.rest.base.models.Error;
import com.lykkex.LykkeWallet.rest.wallet.callback.CreateBankCardCallBack;
import com.lykkex.LykkeWallet.rest.wallet.request.models.CardModel;
import com.lykkex.LykkeWallet.rest.wallet.response.models.BankCardsData;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import retrofit2.Call;

/**
 * Created by LIZA on 06.03.2016.
 */
@EFragment(R.layout.addcard_fragment)
public class AddCard extends BaseFragment implements CallBackListener {

    @ViewById ViewPager viewPager;
    @ViewById EditText etNumberCard;
    @ViewById EditText etFinishCard;
    @ViewById EditText etNameCard;
    @ViewById EditText etCVV;
    @ViewById Button btnSubmit;

    @ViewById ImageView imgWellCardName;
    @ViewById Button btnClearCardName;

    @ViewById ImageView imgWellCardNumber;
    @ViewById Button btnClearCardNumber;

    @ViewById ImageView imgWellFinish;
    @ViewById Button btnClearFinish;

    @ViewById ImageView imgWellCVV;
    @ViewById Button btnClearCVV;

    @Pref UserPref_ userPref;

    @AfterViews
    public void afterViews(){
        actionBar.setTitle(R.string.create_card);
        etNameCard.addTextChangedListener(new SimpleTextWatcher(imgWellCardName,
                btnClearCardName, etNameCard,Constants.MIN_COUNT_SYMBOL));

        etCVV.addTextChangedListener(new SimpleTextWatcher(imgWellCVV,
                btnClearCVV, etCVV, Constants.CVV_COUNT));

        etFinishCard.addTextChangedListener(new MonthYearTextWatcher(imgWellFinish,
                btnClearFinish, etFinishCard, Constants.COUNT_MONTH));

        etNumberCard.addTextChangedListener(new CardNumberTextWatcher(imgWellCardNumber,
                btnClearCardNumber, etNumberCard, Constants.COUNT_CARD_NUMBER));

        etFinishCard.setRawInputType(Configuration.KEYBOARD_QWERTY);
        etNumberCard.setRawInputType(Configuration.KEYBOARD_QWERTY);

        btnSubmit.setEnabled(true);
    }

    @Click(R.id.btnSubmit)
    public void createCard(){
        CardModel model = new CardModel();
        model.setBankNumber(etNumberCard.getText().toString());
        model.setCvc(etCVV.getText().toString());
        String finishTo = etFinishCard.getText().toString();
        model.setMonthTo(Integer.valueOf(finishTo.substring(0, 2)));
        model.setYearTo(Integer.valueOf(finishTo.substring(3)));
        model.setName(etNameCard.getText().toString());
        CreateBankCardCallBack cardCallBack = new CreateBankCardCallBack(this, getActivity());
        Call<BankCardsData> call =
                LykkeApplication_.getInstance().getRestApi().
                        postBankCards(Constants.PART_AUTHORIZATION + userPref.authToken().get(),
                                model);
        call.enqueue(cardCallBack);
    }

    @Override
    public void onSuccess(Object result) {
        Toast.makeText(getActivity(), "CREATED!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onFail(Error error) {

    }

    @Override
    public void initOnBackPressed() {

    }

    @Override
    public void onConsume(Object o) {

    }
}
