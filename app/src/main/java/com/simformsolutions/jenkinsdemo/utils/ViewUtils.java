package com.simformsolutions.jenkinsdemo.utils;

import android.app.Activity;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import android.view.View;

public final class ViewUtils {

    private ViewUtils() {
        // util class
    }

    @SuppressWarnings({"unchecked", "UnusedDeclaration"})
    public static <T extends View> T findById(@NonNull Activity activity, @IdRes int id) {
        return (T) activity.findViewById(id);
    }
}
