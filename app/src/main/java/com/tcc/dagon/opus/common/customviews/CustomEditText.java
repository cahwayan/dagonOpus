package com.tcc.dagon.opus.common.customviews;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;

import com.tcc.dagon.opus.common.customviews.HelperClass.HelperEditText;

/**
 * Created by cahwayan on 14/12/2016.
 */

public class CustomEditText extends AppCompatEditText {
    public CustomEditText(Context context) {
        super(context);
    }

    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        HelperEditText.setCustomFont(this, context, attrs);
    }

    public CustomEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        HelperEditText.setCustomFont(this, context, attrs);
    }
}
