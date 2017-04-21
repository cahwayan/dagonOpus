package com.tcc.dagon.opus.common;

import android.view.View;

/**
 * Created by Felipe on 10/03/2017.
 */

public abstract class OnOffClickListener implements View.OnClickListener {

    private boolean clickable = true;


    @Override
    public final void onClick(View v) {
        if(clickable) {
            clickable = false;
            onOneClick(v);
            reset();
        }
    }

    public abstract void onOneClick(View v);

    public void reset() {
        clickable = true;
    }
}
