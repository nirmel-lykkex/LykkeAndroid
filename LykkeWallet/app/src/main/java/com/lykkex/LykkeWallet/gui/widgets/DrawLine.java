package com.lykkex.LykkeWallet.gui.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.lykkex.LykkeWallet.rest.trading.response.model.Rate;

/**
 * Created by e.kazimirova on 18.03.2016.
 */
public class DrawLine extends View {
    Paint paint = new Paint();
    private Rate rate;
    private int color;

    public DrawLine(Context context) {
        super(context);
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(5);
    }

    public DrawLine(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(5);
    }


    public DrawLine(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(5);
    }


    @Override
    public void onDraw(Canvas canvas) {
        if (rate != null) {
            canvas.drawColor(color);
            float startX = 0;
            float startY = 0;
            float widthStepX = (getMeasuredWidth()-5) / rate.getChngGrph().length;
            float nextX = widthStepX;
            float heighMeasured = getMeasuredHeight() - 10;
            if (rate.getChngGrph().length > 0) {
                if (rate.getChngGrph()[0] > rate.getChngGrph()[rate.getChngGrph().length - 1]) {
                    if(!rate.getInverted()) {
                        paint.setColor(Color.RED);
                    } else {
                        paint.setColor(Color.GREEN);
                    }
                } else {
                    if(!rate.getInverted()) {
                        paint.setColor(Color.GREEN);
                    } else {
                        paint.setColor(Color.RED);
                    }
                }

                Float[] array = rate.getChngGrph();

                for (int i = 0; i < array.length; i++) {
                    Float y = array[i];

                    if(!rate.getInverted()) {
                        y = 1 - y;
                    }

                    if(i == 0) {
                        startY = y * heighMeasured;

                        continue;
                    }

                    canvas.drawLine(startX, startY + 5, nextX, (y * heighMeasured) + 5, paint);
                    startX = nextX;
                    startY = y *heighMeasured;
                    nextX += widthStepX;
                }
            }
            canvas.drawCircle(startX+1, startY+1, 4, paint);
        }
    }

    public void setUpRates(Rate rate, int color) {
        this.color = color;
        this.rate = rate;
        invalidate();
    }

}
