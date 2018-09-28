package com.example.dell.toinews;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<NewsFeatures>> {
    private static final String TAG = MainActivity.class.getName();
    private static final String REQUESTED_URL ="https://newsapi.org/v2/top-headlines?sources=the-times-of-india&apiKey=3920a251e2234f4b8fa5c8ea1459942e";
    private static final int NEWS_LOADER_ID =1;
    private ProgressBar progressBar;
    private NewsAdapter newsAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ListView newsListView = findViewById(R.id.list);
        progressBar= findViewById(R.id.progressBar);

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo==null||!networkInfo.isConnected()){
            Timber.d("onCreate: %s", networkInfo != null ? networkInfo.getState() : null);
            progressBar.setVisibility(View.GONE);
        }
        else
        {
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(NEWS_LOADER_ID,null,this);
        }
      newsAdapter = new NewsAdapter(this,new ArrayList<NewsFeatures>());
      newsListView.setAdapter(newsAdapter);
      newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
          @Override
          public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
              NewsFeatures newsFeatures = newsAdapter.getItem(position);
              Uri uri = Uri.parse(newsFeatures.getArticleUrl().toString());
              Intent intent = new Intent(Intent.ACTION_VIEW,uri);
              startActivity(intent);
          }
      });
    }

    @Override
    public Loader<List<NewsFeatures>> onCreateLoader(int id, Bundle args) {
        return new NewsLoader(this,REQUESTED_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<NewsFeatures>> loader, List<NewsFeatures> data) {
         if(data!=null&&!data.isEmpty()){
             newsAdapter.addAll(data);
             progressBar.setVisibility(View.GONE);
         }
         else{
             Timber.d("Reached here");
             newsAdapter.clear();

             }
    }
    @Override
    public void onLoaderReset(Loader<List<NewsFeatures>> loader) {
        newsAdapter.clear();
    }
}
