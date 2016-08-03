package com.eragano.eraganoapps.view;

import android.content.Context;
import android.graphics.Typeface;
import android.text.style.TypefaceSpan;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.TextView;

/**
 * Created by Deztu on 8/3/2016.
 */
public class UIText extends TextView {

    Context mContext;

    public UIText(Context context) {
        super(context);
        mContext = context;
    }

    public UIText(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public UIText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
    }

    public void defaultFont()
    {
        Typeface tf = Typeface.createFromAsset(mContext.getAssets(), "fonts/weather.ttf");
        setTypeface(tf);
    }
}
