package com.lykkex.LykkeWallet.gui.customviews;

import android.content.Context;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.gui.utils.Constants;
import com.lykkex.LykkeWallet.gui.utils.validation.EmailTextWatcher;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * Created by Murtic on 31/05/16.
 */
@EViewGroup(R.layout.rich_edit_text)
public class RichEditText extends RelativeLayout implements View.OnFocusChangeListener{

    @ViewById
    ImageView imageWell;

    @ViewById
    Button buttonClear;

    @ViewById
    EditText editTextField;

    private boolean isOnFocus;

    public RichEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @AfterViews
    void afterViews() {
        editTextField.setInputType(InputType.TYPE_CLASS_TEXT |
                InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);

        editTextField.setSelection(editTextField.getText().toString().length());

        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTextField.setText("");
            }
        });

        editTextField.setOnFocusChangeListener(this);
    }

    public void setHint(int hint) {
        editTextField.setHint(hint);
    }

    public void addTextWatcher(TextWatcher watcher) {
        editTextField.addTextChangedListener(watcher);
    }

    public void setText(String text) {
        editTextField.setText(text);
    }

    public Editable getText() {
        return editTextField.getText();
    }

    @Override
    public void setOnFocusChangeListener(OnFocusChangeListener l) {
        editTextField.setOnFocusChangeListener(l);
    }

    @Override
    public void onFocusChange(View view, boolean b) {
        isOnFocus = b;
        setUpClearButton(editTextField.getText().toString());
    }

    public void setUpClearButton(String charSequence){
        if (buttonClear != null) {
            if (charSequence.toString().length() >= Constants.MIN_COUNT_SYMBOL && isOnFocus) {
                buttonClear.setVisibility(View.VISIBLE);
            } else {
                buttonClear.setVisibility(View.GONE);
            }
        }
    }

    public void setReady(boolean ready) {
        imageWell.setVisibility(ready ? ImageView.VISIBLE : ImageView.GONE);
    }
}