package com.lykkex.LykkeWallet.gui.customviews;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by Murtic on 04/06/16.
 */
public class ExtendedEditText extends EditText {

    TextPaint mTextPaint = new TextPaint();
    float mFontHeight;
    TagDrawable left;

    String mSuffix = "";

    Rect line0bounds = new Rect();

    int mLine0Baseline;

    public ExtendedEditText(Context context, AttributeSet attrs) {
        super(context, attrs);

        left = new TagDrawable();

        mFontHeight = getTextSize();

        mTextPaint.setColor( getCurrentHintTextColor() );
        mTextPaint.setTextSize( mFontHeight );
        mTextPaint.setTextAlign(Paint.Align.LEFT);

        setCompoundDrawables(left, null, null, null);
    }

    @Override
    public void setTypeface(Typeface typeface){
        super.setTypeface(typeface);

        if(mTextPaint != null){
            mTextPaint.setTypeface(typeface);
        }

        postInvalidate();
    }

    public void setPrefix(String s) {
        left.setText(s);

        setCompoundDrawables(left, null, null, null);
    }
    public void setSuffix(String s) {
        mSuffix = s;

        setCompoundDrawables(left, null, null, null);
    }

    public String getPrefix() {
        return left.text;
    }

    public String getSufix() {
        return mSuffix;
    }

    @Override
    public void onDraw(Canvas c){
        mLine0Baseline = getLineBounds(0, line0bounds);

        super.onDraw(c);

        int xSuffix = (int)mTextPaint.measureText( left.text + getText().toString() ) + getPaddingLeft();

        c.drawText( mSuffix, xSuffix, line0bounds.bottom, mTextPaint );

    }

    public class TagDrawable extends Drawable {

        public String text = "";

        public void setText(String s){
            text = s;

            setBounds(0,0,getIntrinsicWidth(),getIntrinsicHeight());

            invalidateSelf();
        }

        @Override
        public void draw(Canvas canvas) {
            canvas.drawText( text, 0, mLine0Baseline + canvas.getClipBounds().top, mTextPaint );
        }

        @Override public void setAlpha(int i) {}
        @Override public void setColorFilter(ColorFilter colorFilter) {}
        @Override public int getOpacity() {return 1;}

        @Override public int getIntrinsicHeight (){
            return (int)mFontHeight;
        }
        @Override public int getIntrinsicWidth(){
            return (int)mTextPaint.measureText( text );
        }
    }

}