package com.lykkex.LykkeWallet.gui.fragments.mainfragments.setting;

import android.annotation.TargetApi;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.widget.Switch;

import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.gui.fragments.BaseFragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

/**
 * Created by LIZA on 15.03.2016.
 */
@EFragment(R.layout.push_fragment)
public class PushFragment extends BaseFragment {

    @ViewById Switch switchNewAsset;
    @ViewById Switch switchOrder;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @AfterViews
    public void afterViews(){
        actionBar.setTitle(R.string.push_title);
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= android.os.Build.VERSION_CODES.LOLLIPOP){
            switchNewAsset.setBackground(getResources().getDrawable(R.drawable.switch_background));
            switchOrder.setBackground(getResources().getDrawable(R.drawable.switch_background));
            switchNewAsset.setTextAppearance(getActivity(), R.style.switchStyle);
            switchOrder.setTextAppearance(getActivity(),R.style.switchStyle);
        }
        int colorOn = getActivity().getResources().getColor(R.color.blue_color);
        int colorOff =getActivity().getResources().getColor(R.color.grey_text);
        int colorDisabled = getActivity().getResources().getColor(R.color.grey_text);
        StateListDrawable thumbStates = new StateListDrawable();
        thumbStates.addState(new int[]{android.R.attr.state_checked}, new ColorDrawable(colorOn));
        thumbStates.addState(new int[]{-android.R.attr.state_enabled}, new ColorDrawable(colorDisabled));
        thumbStates.addState(new int[]{}, new ColorDrawable(colorOff));
        switchNewAsset.setThumbDrawable(thumbStates);
        switchOrder.setThumbDrawable(thumbStates);
    }
}
