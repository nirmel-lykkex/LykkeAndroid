package com.lykkex.LykkeWallet.gui.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.View;

import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.rest.trading.response.model.Rates;

/**
 * Created by e.kazimirova on 18.03.2016.
 */
public class DrawLine extends View {
    Paint paint = new Paint();
    private Rates rates;
    private int color;

    public DrawLine(Context context) {
        super(context);
        paint.setColor(Color.BLACK);
    }

    public DrawLine(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    public DrawLine(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public void onDraw(Canvas canvas) {
        if (rates != null) {
            canvas.drawColor(color);
            float startX = 0;
            float startY = 0;
            float widthStepX = getMeasuredWidth() / rates.getPchng().length;
            float nextX = widthStepX;
            float heighMeasured = getMeasuredHeight();
            if (rates.getPchng().length > 0) {
                if (rates.getPchng()[0] > rates.getPchng()[rates.getPchng().length - 1]) {
                    paint.setColor(Color.RED);
                } else {
                    paint.setColor(Color.GREEN);
                }
                for (float y : rates.getPchng()) {
                    canvas.drawLine(startX, startY, nextX, y *heighMeasured, paint);
                    startX = nextX;
                    startY = y *heighMeasured;
                    nextX += widthStepX;
                }
            }
            canvas.drawCircle(startX+1, startY+1, 3, paint);
        }
    }

    public void setUpRates(Rates rates, int color) {
        this.color = color;
        this.rates = rates;
        invalidate();
    }

}
