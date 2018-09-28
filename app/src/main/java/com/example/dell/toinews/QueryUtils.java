package com.example.dell.toinews;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

public class QueryUtils {
    private static final String TAG = QueryUtils.class.getName();
    //here in QueryUtils class we will perform all the queries related to network connections
    // and JSON Parsing
    //here we have created a method named fetchNews whose return type is List of Generic type
    //which can take an object as an input
    public static List<NewsFeatures> fetchNews(String requestUrl){

        //here we are passing the requesturl to another method which is createUrl
        URL url = createUrl(requestUrl);
        String jsonResponse = null;
        try{
            jsonResponse = makeHttpRequest(url);
        }
        catch (Exception e){
        }
        return extractNews(jsonResponse);
    }
    private static URL createUrl(String requestUrl) {
        //in this method
        URL url =null;
        try{
            url = new URL(requestUrl);
        }
        catch (MalformedURLException e){
            Timber.e("createUrl: ");
        }
        return url;
    }
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        if(url==null){
            return jsonResponse;
        }
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try{
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.connect();
            if(urlConnection.getResponseCode()==200){
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }
            else
            {
                Timber.e("makeHttpRequest: %s", urlConnection.getErrorStream());
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        finally {
            if(urlConnection != null)
            {
                urlConnection.disconnect();
            }
            if(inputStream!=null){
                inputStream.close();
            }
            return jsonResponse;
        }
    }
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        if (inputStream!=null){
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = bufferedReader.readLine();
            while(line!=null){
                stringBuilder.append(line);
                line = bufferedReader.readLine();
            }
        }
        return stringBuilder.toString();
    }
    private static List<NewsFeatures> extractNews(String newsjson) {
        //in this method we are going to do the real task of extracting the news
        //which is in json format
        if(TextUtils.isEmpty(newsjson)){
            return null;
        }
        //creating an array List to store all the news that we are going to fetch
        List<NewsFeatures> newsFeatures = new ArrayList<>();
        try{
            JSONObject baseJsonResponse = new JSONObject(newsjson);

            JSONArray jsonArray = baseJsonResponse.getJSONArray("articles");
            for(int i=0;i<jsonArray.length();i++){
                JSONObject currentNews = jsonArray.getJSONObject(i);
                String author = currentNews.getString("author");
                String title = currentNews.getString("title");
                String description = currentNews.getString("description");
                String dateTime = currentNews.getString("publishedAt");
                URL articleUrl = createUrl(currentNews.getString("url"));
                URL imageUrl = createUrl(currentNews.getString("urlToImage"));
                Bitmap image = BitmapFactory.decodeStream(imageUrl.openConnection().getInputStream());

                NewsFeatures newsObject = new NewsFeatures(author,title,description,articleUrl,image,dateTime);
                newsFeatures.add(newsObject);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return newsFeatures;
    }
}
