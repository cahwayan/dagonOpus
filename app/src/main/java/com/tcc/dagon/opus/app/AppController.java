package com.tcc.dagon.opus.app;

import android.app.Application;
import android.text.TextUtils;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.tcc.dagon.opus.utils.LruBitmapCache;

/**
 * Created by cahwayan on 30/03/2017.
 */

public class AppController extends Application {

    public final String TAG = AppController.class.getSimpleName();

    private RequestQueue requestQueue;
    private ImageLoader imageLoader;

    private static AppController mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    // Método que retorna a única instância da classe
    public static synchronized AppController getInstance() {
        return mInstance;
    }

    // Método que vai retornar a RequestQueue
    public RequestQueue getRequestQueue() {
        if(this.requestQueue == null) {
            this.requestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return this.requestQueue;
    }

    // Método que vai retornar o ImageLoader
    public ImageLoader getImageLoader() {
        getRequestQueue();
        if(this.imageLoader == null) {
            // Image loader precisa de um requestQueue e um gerenciador de cache de bitmaps
            this.imageLoader = new ImageLoader(this.requestQueue, new LruBitmapCache());
        }
        return this.imageLoader;
    }

    // Método que vai adicionar um request na fila de requests
    public <T> void addToRequestQueue(Request<T> request, String tag) {
        // A tag está vazia? Se sim, usar a tag padrão desta classe
        request.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(request);
    }

    public void cancelPendingRequests(Object tag) {
        if(this.requestQueue != null) {
            requestQueue.cancelAll(tag);
        }
    }




}
