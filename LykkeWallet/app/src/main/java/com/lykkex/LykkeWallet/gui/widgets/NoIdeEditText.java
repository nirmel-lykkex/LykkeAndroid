package com.lykkex.LykkeWallet.gui.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by e.kazimirova on 24.03.2016.
 */
public class NoIdeEditText extends EditText {

    public NoIdeEditText(Context context) {
        super(context);
    }

    public NoIdeEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    public NoIdeEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onCheckIsTextEditor() {
        return false;
    }
}
