package com.simformsolutions.jenkinsdemo.api;

import android.app.Application;
import androidx.annotation.NonNull;
import android.util.Log;

import com.simformsolutions.jenkinsdemo.BuildConfig;

import java.io.File;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import okhttp3.logging.HttpLoggingInterceptor.Logger;

/**
 * Created by paresh.mayani on 27/04/17.
 */

public class OkHttpClientFactory {

    // Cache size for the OkHttpClient
    private static final int DISK_CACHE_SIZE = 50 * 1024 * 1024; // 50MB

    private OkHttpClientFactory() {
    }

    @NonNull
    public static OkHttpClient provideOkHttpClient(Application app) {
        // Install an HTTP cache in the application cache directory.
        File cacheDir = new File(app.getCacheDir(), "http");
        Cache cache = new Cache(cacheDir, DISK_CACHE_SIZE);

        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder()
                .cache(cache);

        if (BuildConfig.DEBUG) {
            okHttpClient.addInterceptor(provideHttpLoggingInterceptor());
        }
        return okHttpClient.build();
    }

    private static HttpLoggingInterceptor provideHttpLoggingInterceptor() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new Logger() {
            @Override
            public void log(String message) {
                Log.i("OkHttp", message);
            }
        });
        loggingInterceptor.setLevel(Level.BODY);
        return loggingInterceptor;
    }
}