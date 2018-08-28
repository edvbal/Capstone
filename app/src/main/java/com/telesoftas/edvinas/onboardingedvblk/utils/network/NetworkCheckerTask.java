package com.telesoftas.edvinas.onboardingedvblk.utils.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

import com.telesoftas.edvinas.onboardingedvblk.R;
import com.telesoftas.edvinas.onboardingedvblk.news.articles.ArticlesFragment;
import com.telesoftas.edvinas.onboardingedvblk.utils.network.listeners.NetworkStatusListener;

import java.lang.ref.WeakReference;

public class NetworkCheckerTask extends AsyncTask<String, Void, Boolean> {
    private final WeakReference<Context> context;
    private final NetworkStatusListener networkStatusListener;

    public NetworkCheckerTask(Context context, NetworkStatusListener networkStatusListener) {
        this.context = new WeakReference<>(context);
        this.networkStatusListener = networkStatusListener;
    }

    @Override
    protected Boolean doInBackground(String... result) {
        Context context = this.context.get();
        Object connectivityService = context.getSystemService(Context.CONNECTIVITY_SERVICE);
        ConnectivityManager connectivityManager = (ConnectivityManager) connectivityService;
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    @Override
    protected void onPostExecute(Boolean isConnected) {
        networkStatusListener.isNetworkConnected(isConnected);
    }
}
