package com.tcc.dagon.opus.common.customviews;

import android.content.Context;
import android.util.AttributeSet;
import android.support.v7.widget.AppCompatEditText;

import com.tcc.dagon.opus.common.customviews.HelperClass.HelperEditText;

/**
 * Created by cahwayan on 13/12/2016.
 */

public class CustomTextView extends AppCompatEditText {

    public CustomTextView(Context context) {
        super(context);
        this.setBackground(null);
        this.setClickable(false);
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        HelperEditText.setCustomFont(this, context, attrs);
        this.setBackground(null);
        this.setClickable(false);
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        HelperEditText.setCustomFont(this, context, attrs);
        this.setBackground(null);
        this.setClickable(false);
    }
}
