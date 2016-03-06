package com.lykkex.LykkeWallet.gui.fragments.addcard;

import android.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.gui.fragments.BaseFragment;
import com.lykkex.LykkeWallet.gui.utils.validation.MonthYearTextWatcher;
import com.lykkex.LykkeWallet.gui.utils.validation.SimpleTextWatcher;
import com.lykkex.LykkeWallet.gui.utils.Constants;
import com.lykkex.LykkeWallet.gui.utils.validation.CallBackListener;
import com.lykkex.LykkeWallet.rest.base.models.Error;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

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

    @AfterViews
    public void afterViews(){
        etNameCard.addTextChangedListener(new SimpleTextWatcher(imgWellCardName,
                btnClearCardName, etNameCard,Constants.MIN_COUNT_SYMBOL));

        etCVV.addTextChangedListener(new SimpleTextWatcher(imgWellCVV,
                btnClearCVV, etCVV, Constants.CVV_COUNT));

        etFinishCard.addTextChangedListener(new MonthYearTextWatcher(imgWellFinish,
                btnClearFinish, etFinishCard, Constants.COUNT_MONTH));
    }

    @Override
    public void onSuccess(Object result) {

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
