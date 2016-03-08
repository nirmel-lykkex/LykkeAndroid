package com.lykkex.LykkeWallet.gui.fragments.addcard;

import android.app.Fragment;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.gui.LykkeApplication_;
import com.lykkex.LykkeWallet.gui.activity.BaseActivity;
import com.lykkex.LykkeWallet.gui.adapters.ViewPageAdapter;
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
import com.lykkex.LykkeWallet.rest.wallet.response.models.BankCards;
import com.lykkex.LykkeWallet.rest.wallet.response.models.BankCardsData;
import com.viewpagerindicator.CirclePageIndicator;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;

/**
 * Created by LIZA on 06.03.2016.
 */
@EFragment(R.layout.addcard_fragment)
public class AddCard extends BaseFragment implements CallBackListener {

    @ViewById ViewPager viewPager;
    @ViewById CirclePageIndicator circlePageViewer;
    @ViewById LinearLayout relViewPager;

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

    private HashMap<FieldType, Boolean> map = new HashMap<>();

    @AfterViews
    public void afterViews(){
        actionBar.setTitle(R.string.create_card);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        setUpViewPage();

        etNameCard.addTextChangedListener(new SimpleTextWatcher(imgWellCardName,
                btnClearCardName, etNameCard,Constants.MIN_COUNT_SYMBOL, this, FieldType.cardName));

        etCVV.addTextChangedListener(new SimpleTextWatcher(imgWellCVV,
                btnClearCVV, etCVV, Constants.CVV_COUNT, this, FieldType.cvv));

        etFinishCard.addTextChangedListener(new MonthYearTextWatcher(imgWellFinish,
                btnClearFinish, etFinishCard, Constants.COUNT_MONTH, this, FieldType.monthYear));

        etNumberCard.addTextChangedListener(new CardNumberTextWatcher(imgWellCardNumber,
                btnClearCardNumber, etNumberCard, Constants.COUNT_CARD_NUMBER, this, FieldType.cardNumber));

        etFinishCard.setRawInputType(Configuration.KEYBOARD_QWERTY);
        etNumberCard.setRawInputType(Configuration.KEYBOARD_QWERTY);

    }

    private void setUpViewPage(){
        List<Fragment> list = new ArrayList<>();
        Parcelable[] cardses = getArguments().getParcelableArray(Constants.EXTRA_BANK_CARDS);
        for (Parcelable pars  : cardses) {
            FragmentCards_ fragmentCards = new FragmentCards_();
            Bundle bundle = new Bundle();
            bundle.putParcelable(Constants.EXTRA_BANK, pars);
            fragmentCards.setArguments(bundle);
            list.add(fragmentCards);
        }

        if (list.size() > 0) {
            relViewPager.setVisibility(View.VISIBLE);
        } else {
            relViewPager.setVisibility(View.GONE);
        }
        ViewPageAdapter viewPageAdapter = new ViewPageAdapter(getActivity().getFragmentManager(),
                list);
        viewPager.setAdapter(viewPageAdapter);
        circlePageViewer.setViewPager(viewPager);

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
        if (result instanceof FieldType) {
            if (map.containsKey(result)) {
                map.remove(result);
            }
            map.put((FieldType)result, true);
            if (map.size() == 4) {
                btnSubmit.setEnabled(true);
            }
        } else {
            ((BaseActivity) getActivity()).initFragment(new CardAddSuccess_(), null);
        }
    }

    @Override
    public void onFail(Object result) {
        if (result instanceof FieldType) {
            if (map.containsKey(result)) {
                map.remove(result);
            }
        }
    }

    @Override
    public void initOnBackPressed() {

    }

    @Override
    public void onConsume(Object o) {

    }
}
