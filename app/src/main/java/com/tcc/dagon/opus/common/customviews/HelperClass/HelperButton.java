package com.tcc.dagon.opus.common.customviews.HelperClass;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

import com.tcc.dagon.opus.R;

import static android.content.ContentValues.TAG;

/**
 * Created by cahwayan on 13/12/2016.
 */

public class HelperButton {

    /**
     * Sets a font on a textview based on the custom com.my.package:font attribute
     * If the custom font attribute isn't found in the attributes nothing happens
     * @param textView
     * @param context
     * @param attrs
     */
    public static void setCustomFont(TextView textView, Context context, AttributeSet attrs) {
        TypedArray attributeArray = context.obtainStyledAttributes(attrs, R.styleable.CustomFontButton);
        String font = attributeArray.getString(R.styleable.CustomFontButton_CustomFontButton);
        setCustomFont(textView, font, context);
        attributeArray.recycle();
    }

    /**
     * Sets a font on a textview
     * @param textView
     * @param font
     * @param context
     */
    public static void setCustomFont(TextView textView, String font, Context context) {
        if(font == null) {
            return;
        }

        Log.d(TAG, font);

        Typeface tf = FontCache.get(font, context);

        if(tf != null) {
            textView.setTypeface(tf);
        }
    }

}
