package com.lykkex.LykkeWallet.gui.fragments.mainfragments.wallet;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.gui.fragments.BaseFragment;
import com.lykkex.LykkeWallet.gui.utils.Calculate;
import com.lykkex.LykkeWallet.gui.utils.Constants;
import com.lykkex.LykkeWallet.gui.widgets.ConfirmCashOutDialog;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.Random;

/**
 * Created by e.kazimirova on 19.04.2016.
 */
@EFragment(R.layout.withdraw_funds_fragment)
public class WithdrawFundsFragment extends BaseFragment implements TextWatcher, View.OnFocusChangeListener {

    @ViewById
    EditText etAmount;

    @ViewById
    Button button;

    @ViewById
    View calcKeyboard;

    @AfterViews
    public void afterViews(){
        button.setText(R.string.proceed);
        etAmount.setOnFocusChangeListener(this);
        etAmount.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.onTouchEvent(event);
                InputMethodManager imm = (InputMethodManager)
                        v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
                return true;
            }
        });
        etAmount.requestFocus();
        button.setEnabled(false);
        button.setTextColor(getResources().getColor(R.color.grey_text));
        etAmount.addTextChangedListener(this);
    }

    @Click(R.id.button)
    public void clickTryToConfirm(){
        ConfirmCashOutDialog dialog = new ConfirmCashOutDialog();
        Bundle args = getArguments();
        args.putString(Constants.EXTRA_AMOUNT, etAmount.getText().toString());

        dialog.setArguments(args);
        dialog.show(getActivity().getFragmentManager(),
                "dlg1" + new Random((int) Constants.DELAY_5000));
    }

    @Click(R.id.rel100)
    public void click100(){
        etAmount.setText("100");
        etAmount.setSelection(etAmount.getText().toString().length());
    }

    @Click(R.id.rel1000)
    public void click1000(){
        etAmount.setText("1000");
        etAmount.setSelection(etAmount.getText().toString().length());
    }

    @Click(R.id.rel10000)
    public void click10000(){
        etAmount.setText("10000");
        etAmount.setSelection(etAmount.getText().toString().length());
    }

    @Click(R.id.rel1)
    public void click1(){
        setUpText(etAmount.getText().toString() + "1");
    }

    @Click(R.id.rel2)
    public void click2(){
        setUpText(etAmount.getText().toString() + "2");
    }

    @Click(R.id.rel3)
    public void click3(){
        setUpText(etAmount.getText().toString() + "3");
    }

    @Click(R.id.rel4)
    public void click4(){
        setUpText(etAmount.getText().toString() + "4");
    }

    @Click(R.id.rel5)
    public void click5(){
        setUpText(etAmount.getText().toString() + "5");
    }

    @Click(R.id.rel6)
    public void click6(){
        setUpText(etAmount.getText().toString() + "6");
    }

    @Click(R.id.rel7)
    public void click7(){
        setUpText(etAmount.getText().toString() + "7");
    }

    @Click(R.id.rel8)
    public void click8(){
        setUpText(etAmount.getText().toString() + "8");
    }

    @Click(R.id.rel9)
    public void click9(){
        setUpText(etAmount.getText().toString() + "9");
    }

    @Click(R.id.relDot)
    public void clickDot(){
        setUpText(etAmount.getText().toString() + ".");
    }

    @Click(R.id.rel0)
    public void click0(){
        setUpText(etAmount.getText().toString() + "0");
    }

    @Click(R.id.relRemove)
    public void clickRemove(){
        if (etAmount.getText().toString().length() > 0) {
            setUpText(etAmount.getText().toString().substring(0, etAmount.getText().toString().length() - 1));
        }
    }

    @Click(R.id.relDivider)
    public void clickDivider(){
        setUpText(etAmount.getText().toString() + "/");
    }

    private void setUpText(String text) {
        etAmount.setText(text);
        etAmount.setSelection(etAmount.getText().toString().length());
    }

    @Click(R.id.relMultiply)
    public void clickMultiply(){
        setUpText(etAmount.getText().toString() + "*");
    }

    @Click(R.id.relMinus)
    public void clickMinus(){
        setUpText(etAmount.getText().toString() + "-");
    }

    @Click(R.id.relPlus)
    public void clickPlus(){
        setUpText(etAmount.getText().toString() + "+");
    }

    @Click(R.id.relEqual)
    public void clickEqual(){
        setUpText(String.valueOf((Calculate.eval(
                etAmount.getText().toString(), 6))));
    }

    @Override
    public void onFocusChange(View view, boolean b) {
        if(isAdded() && calcKeyboard != null) {
            if (b) {
                calcKeyboard.setVisibility(View.VISIBLE);
            } else {
                calcKeyboard.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    private BigDecimal etAmountRes = BigDecimal.ZERO;

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        try {

            DecimalFormat format= (DecimalFormat) DecimalFormat.getInstance();
            format.setParseBigDecimal(true);
            DecimalFormatSymbols symbols=format.getDecimalFormatSymbols();
            symbols.setDecimalSeparator('.');
            format.setDecimalFormatSymbols(symbols);

            etAmountRes = (BigDecimal) format.parse(etAmount.getText().toString());

            int charCount = etAmount.getText().toString().replaceAll("[^.]", "").length();
            if (charCount <=1
                    &&!etAmount.getText().toString().startsWith(".")
                    &&!etAmount.getText().toString().contains("/") &&
                    !etAmount.getText().toString().contains("*") &&
                    !etAmount.getText().toString().contains("-") &&
                    !etAmount.getText().toString().contains("+")
                    && !etAmount.getText().toString().isEmpty()
                    && etAmountRes.compareTo(BigDecimal.ZERO) > 0) {
                button.setEnabled(true);
                button.setTextColor(Color.WHITE);
            } else {
                button.setEnabled(false);
                button.setTextColor(getResources().getColor(R.color.grey_text));
            }
        } catch (NumberFormatException ex){
            button.setEnabled(false);
            button.setTextColor(getResources().getColor(R.color.grey_text));
        }
        catch (ParseException e) {
            button.setEnabled(false);
            button.setTextColor(getResources().getColor(R.color.grey_text));
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {
    }

}
