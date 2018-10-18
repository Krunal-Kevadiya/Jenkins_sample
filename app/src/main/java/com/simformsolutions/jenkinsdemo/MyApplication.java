package com.simformsolutions.jenkinsdemo;

import android.app.Application;
import android.content.Context;
import androidx.annotation.Nullable;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.core.CrashlyticsCore;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.simformsolutions.jenkinsdemo.api.OkHttpClientFactory;
import com.squareup.picasso.Picasso;

import io.fabric.sdk.android.Fabric;
import okhttp3.OkHttpClient;
import timber.log.Timber;

/**
 * Created by paresh.mayani on 26/04/17.
 */

public class MyApplication extends Application {

    private OkHttpClient okHttpClient;
    private Picasso mPicasso;

    public static MyApplication from(Context context) {
        return (MyApplication) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Crashlytics.Builder crashlytics = new Crashlytics.Builder().core(new CrashlyticsCore.Builder().disabled(BuildConfig.DEBUG).build());
        Fabric.with(this, crashlytics.build(), new Crashlytics());

        okHttpClient = OkHttpClientFactory.provideOkHttpClient(this);

        mPicasso = new Picasso.Builder(this)
                .downloader(new OkHttp3Downloader(okHttpClient))
                .build();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
        Timber.plant(new CrashlyticsTree());
    }

    public Picasso getPicasso() {
        return mPicasso;
    }

    public OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }

    private class CrashlyticsTree extends Timber.Tree {
        private static final String CRASHLYTICS_KEY_PRIORITY = "priority";
        private static final String CRASHLYTICS_KEY_TAG = "tag";
        private static final String CRASHLYTICS_KEY_MESSAGE = "message";

        @Override
        protected void log(int priority, @Nullable String tag, @Nullable String message, @Nullable Throwable t) {
            if (priority == Log.VERBOSE || priority == Log.DEBUG || priority == Log.INFO) {
                return;
            }

            Crashlytics.setInt(CRASHLYTICS_KEY_PRIORITY, priority);
            Crashlytics.setString(CRASHLYTICS_KEY_TAG, tag);
            Crashlytics.setString(CRASHLYTICS_KEY_MESSAGE, message);

            if (t == null) {
                Crashlytics.logException(new Exception(message));
            } else {
                Crashlytics.logException(t);
            }
        }
    }
}
