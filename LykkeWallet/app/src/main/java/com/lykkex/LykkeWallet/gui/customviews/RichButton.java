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
import android.widget.TextView;

import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.gui.utils.Constants;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * Created by Murtic on 31/05/16.
 */
@EViewGroup(R.layout.rich_button)
public class RichButton extends RelativeLayout {

    @ViewById
    TextView tvName;

    public RichButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setText(String text) {
        tvName.setText(text);
    }
}