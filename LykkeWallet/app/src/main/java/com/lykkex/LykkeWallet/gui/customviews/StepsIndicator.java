package com.lykkex.LykkeWallet.gui.customviews;

import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.ViewsById;

import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.gui.LykkeApplication;

import java.util.List;

/**
 * Created by Murtic on 31/05/16.
 */
@EViewGroup(R.layout.steps_indicator)
public class StepsIndicator extends RelativeLayout {

    @ViewsById({R.id.imgFirst, R.id.imgSecond, R.id.imgThird, R.id.imgForth})
    List<ImageView> indicatorViews;

    @ViewsById({R.id.imageView2, R.id.imageView3, R.id.imageView4})
    List<View> linesViews;

    @App
    LykkeApplication lykkeApplication;

    @AfterViews
    public void afterViews() {
        final View rootView = getRootView();

        rootView.post(new Runnable() {
            @Override
            public void run() {
                int width = rootView.getMeasuredWidth();

                for(View view : linesViews) {
                    view.getLayoutParams().width = width / 5;

                    view.requestLayout();
                }
            }
        });
    }

    public StepsIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setCurrentStep(int step) {
        for(int i = 0; i < indicatorViews.size(); i++) {
            indicatorViews.get(i).setImageResource(step <= i ? R.drawable.pin_un_setup : R.drawable.ready);
        }

        if(indicatorViews.size() >= step) {
            indicatorViews.get(step - 1).setImageResource(R.drawable.submit_form_circle);
        }
    }
}