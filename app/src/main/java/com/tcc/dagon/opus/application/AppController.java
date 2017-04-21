package com.tcc.dagon.opus.application;

import android.app.Application;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.tcc.dagon.opus.common.LruBitmapCache;

import java.util.concurrent.CountDownLatch;

/**
 * Created by cahwayan on 30/03/2017.
 */

public class AppController extends Application {

    /* Countdown for multiple requests */
    private static CountDownLatch countdown;
    private static int requestCount;

    public static final String TAG = AppController.class.getSimpleName();
    private RequestQueue requestQueue;
    private ImageLoader imageLoader;


    private static AppController mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static CountDownLatch getCountdownLatch() {
        return countdown;
    }

    public static int getRequestCount() {
        return requestCount;
    }

    public static void setRequestCountdown(int count) {
        Log.d(TAG, "COUNTDOWN INICIADA COM " + String.valueOf(count) + " requests");
        countdown = new CountDownLatch(count);
    }

    public static void increaseRequestCount() {
        requestCount++;
        Log.d(TAG, "REQUEST COUNTDOWN AUMENTANDO . . . " + String.valueOf(requestCount));
    }

    public static void decreaseRequestCount() {
        countdown.countDown();
        requestCount--;
        Log.d(TAG, "REQUEST COUNTDOWN ABAIXANDO . . . " + String.valueOf(requestCount));
    }

    public static synchronized AppController getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if(this.requestQueue == null) {
            this.requestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return this.requestQueue;
    }

    public ImageLoader getImageLoader() {
        getRequestQueue();
        if(this.imageLoader == null) {
            // Image loader precisa de um requestQueue e um gerenciador de cache de bitmaps
            this.imageLoader = new ImageLoader(this.requestQueue, new LruBitmapCache());
        }
        return this.imageLoader;
    }

    public <T> void addToRequestQueue(Request<T> request, String tag) {
        // A tag está vazia? Se sim, usar a tag padrão desta classe
        request.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(request);
    }

    public void cancelPendingRequests(Object tag) {
        if(this.requestQueue != null) {
            requestQueue.cancelAll(tag);
            Log.d(TAG, tag + " teve um erro e cancelou todos os requests." );
            countdown = null;
            requestCount = 0;
        }
    }




}
