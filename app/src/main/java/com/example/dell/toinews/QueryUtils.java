package com.example.dell.toinews;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;


public class QueryUtils {
    private static final String TAG = QueryUtils.class.getName();
    //here in QueryUtils class we will perform all the queries related to network connections
    // and JSON Parsing
    //here we have created a method named fetchNews whose return type is List of Generic type
    //which can take an object as an input
    private List<NewsFeatures> fetchNews(String requestUrl){

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

    private URL createUrl(String requestUrl) {
        //in this method
        URL url =null;
        try{
            url = new URL(requestUrl);
        }
        catch (MalformedURLException e){
            Log.e(TAG, "createUrl: " );
        }
        return url;
    }

    private String makeHttpRequest(URL url) {
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
                Log.e(TAG, "makeHttpRequest: ");
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
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return jsonResponse;
        }

    }

    private String readFromStream(InputStream inputStream) throws IOException {
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

}
