package com.tcc.dagon.opus.common.customviews;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

import com.tcc.dagon.opus.common.customviews.HelperClass.HelperButton;

/**
 * Created by cahwayan on 13/12/2016.
 */

public class CustomButton extends Button {

    public CustomButton(Context context) {
        super(context);
    }

    public CustomButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        HelperButton.setCustomFont(this, context, attrs);
    }

    public CustomButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        HelperButton.setCustomFont(this, context, attrs);
    }
}
