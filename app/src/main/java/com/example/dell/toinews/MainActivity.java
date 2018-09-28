package com.example.dell.toinews;

import android.app.LoaderManager;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<NewsFeatures>> {
    private static final String REQUESTED_URL ="https://newsapi.org/v2/top-headlines?sources=the-times-of-india&apiKey=3920a251e2234f4b8fa5c8ea1459942e";
    private static final int NEWS_LOADER_ID =1;
    private NewsAdapter newsAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo==null||!networkInfo.isConnected()){

        }
        else
        {
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(NEWS_LOADER_ID,null,this);
        }
        

    }

    @Override
    public Loader<List<NewsFeatures>> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<List<NewsFeatures>> loader, List<NewsFeatures> data) {

    }

    @Override
    public void onLoaderReset(Loader<List<NewsFeatures>> loader) {

    }
}
