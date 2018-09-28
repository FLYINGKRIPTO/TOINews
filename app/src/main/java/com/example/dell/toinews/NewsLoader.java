package com.example.dell.toinews;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

import timber.log.Timber;

public class NewsLoader extends AsyncTaskLoader<List<NewsFeatures>> {
    private static final String TAG = NewsLoader.class.getName();
    private String mUrl;
    public NewsLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
        Timber.d("onStartLoading: onStartMethod ");
    }

    @Override
    public List<NewsFeatures> loadInBackground() {
        if(mUrl==null){
            Timber.d("loadInBackground: ");
            return null;
        }
        List<NewsFeatures> newsFeatures = QueryUtils.fetchNews(mUrl);
        return newsFeatures;
    }
}
