package com.lykkex.LykkeWallet.gui.fragments.addcard;

import android.app.Fragment;
import android.widget.TextView;

import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.gui.utils.Constants;
import com.lykkex.LykkeWallet.rest.wallet.response.models.BankCards;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

/**
 * Created by LIZA on 08.03.2016.
 */
@EFragment(R.layout.fragment_cards)
public class FragmentCards extends Fragment {

    @ViewById TextView tvCardNumber;
    @ViewById TextView tvMonth;
    @ViewById TextView tvName;

    @AfterViews
    public void afterViews(){
        BankCards card = getArguments().getParcelable(Constants.EXTRA_BANK);
        tvCardNumber.setText(".... "+card.getLastDigits());
        tvMonth.setText(card.getMonthTo()+"/"+card.getYearTo());
        tvName.setText(card.getName());
    }
}
