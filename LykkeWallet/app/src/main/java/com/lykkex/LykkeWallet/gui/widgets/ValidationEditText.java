package com.lykkex.LykkeWallet.gui.widgets;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.lykkex.LykkeWallet.R;

/**
 * Created by e.kazimirova on 10.02.2016.
 */
public class ValidationEditText implements View.OnClickListener, View.OnFocusChangeListener{

    private EditText editTextField;
    private ImageView imageViewWell;
    private Button buttonClear;
    private Button buttonAction;
    private boolean isClearVisible = false;

    public ValidationEditText(EditText editText, ImageView imageView, Button button,
                              Button buttonAction) {
        this.editTextField = editText;
        this.imageViewWell = imageView;
        this.buttonClear = button;
        this.buttonAction = buttonAction;
        button.setOnClickListener(this);
        editTextField.setOnFocusChangeListener(this);
    }
    public void setReady(boolean isReady){
        if (isReady) {
            buttonAction.setEnabled(true);
            imageViewWell.setVisibility(View.VISIBLE);
        } else {
            buttonAction.setEnabled(false);
            imageViewWell.setVisibility(View.GONE);
        }
    }

    public boolean isReady(){
        if (imageViewWell.getVisibility() == View.VISIBLE) {
            return true;
        }
        return false;
    }

    public void setButtonClearVisibilty(boolean isVisible){
        if (isVisible) {
            buttonClear.setVisibility(View.VISIBLE);
            isClearVisible = true;
        } else {
            buttonClear.setVisibility(View.GONE);
            isClearVisible = false;
        }
        buttonClear.invalidate();
    }

    public void setEditTextEnabled(boolean isEnabled){
        editTextField.setEnabled(isEnabled);
    }

    public  String getText(){
        return editTextField.getText().toString();
    }

    public void setText(String text){
        editTextField.setText(text);
    }

    @Override
    public void onClick(View view) {
        editTextField.setText("");
    }

    @Override
    public void onFocusChange(View view, boolean b) {
        if (b && isClearVisible) {
            buttonClear.setVisibility(View.VISIBLE);
        } else if (!b) {
            buttonClear.setVisibility(View.GONE);
        }
    }
}
