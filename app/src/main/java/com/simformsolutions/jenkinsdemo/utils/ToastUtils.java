package com.simformsolutions.jenkinsdemo.utils;

import android.content.Context;
import android.widget.Toast;

import static com.simformsolutions.jenkinsdemo.utils.Logger.makeLogTag;

public class ToastUtils {

    private static final String TAG = makeLogTag(ToastUtils.class);

    /**
     * Shows a short time duration toast message.
     *
     * @param msg Message to be shown in Toast
     * @return Toast object just shown
     * **
     */
    public static Toast showToast(Context ctx, CharSequence msg) {
        return showToast(ctx, msg, Toast.LENGTH_SHORT);
    }

    /**
     * Shows the message with using parameters passed
     *
     * @param msg Message to be shown in the toast.
     * @param duration Duration in milliseconds for which the toast should be shown
     * @return Toast object just shown
     * **
     */
    public static Toast showToast(Context ctx, CharSequence msg, int duration) {
        Toast toast = Toast.makeText(ctx, msg, duration);
        toast.setDuration(duration);
        toast.show();
        return toast;
    }
}
