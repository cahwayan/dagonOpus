package com.tcc.dagon.opus.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import android.support.v7.view.ContextThemeWrapper;

/**
 * Created by cahwayan on 30/03/2017.
 */

public class ProgressDialogHelper {

    private ProgressDialogHelper() { }

    /**
     * Creates a generic progress dialog with the specified message
     *
     * @param activity the activity which hosts the dialog. This must be an activity, not a context.
     * @param msgResId the resId for the message to display
     * @return a progress dialog
     */

    public static ProgressDialog buildDialog(Activity activity, int msgResId)  {

        return buildDialog(activity, activity.getApplicationContext().getString(msgResId));
    }


    /**
     * Creates a generic progress dialog with the specified message
     *
     * @param activity the activity which hosts the dialog. This must be an activity, not a context.
     * @param message the message to display
     * @return a progress dialog
     */

    public static ProgressDialog buildDialog(Activity activity, String message) {

        ProgressDialog dialog = new ProgressDialog(activity);

        /*
        if(Build.VERSION.SDK_INT  >= Build.VERSION_CODES.HONEYCOMB)
            dialog = new ProgressDialog(new ContextThemeWrapper(activity, android.R.style.Theme_Holo_Dialog));
         else
             dialog = new ProgressDialog(activity);
        */

        dialog.setMessage(message);
        dialog.setCancelable(false);

        return dialog;

    }
}
