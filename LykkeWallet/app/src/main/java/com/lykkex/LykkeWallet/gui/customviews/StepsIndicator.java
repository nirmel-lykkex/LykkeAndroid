package com.lykkex.LykkeWallet.gui.customviews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.ViewsById;

import com.lykkex.LykkeWallet.R;

import java.util.List;

/**
 * Created by Murtic on 31/05/16.
 */
@EViewGroup(R.layout.steps_indicator)
public class StepsIndicator extends RelativeLayout {

    @ViewsById({R.id.imgFirst, R.id.imgSecond, R.id.imgThird, R.id.imgForth})
    List<ImageView> indicatorViews;

    public StepsIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setCurrentStep(int step) {
        for(int i = 0; i < indicatorViews.size(); i++) {
            indicatorViews.get(i).setImageResource(step <= i ? R.drawable.pin_un_setup : R.drawable.pin_setup);
        }
    }
}