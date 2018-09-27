package com.example.dell.toinews;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class NewsAdapter extends ArrayAdapter<NewsFeatures> {
    ArrayList<NewsFeatures> list;
    public NewsAdapter(@NonNull Context context, ArrayList<NewsFeatures> list) {

        super(context, R.layout.activity_main,R.id.list,list);
        this.list= list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
         if(view==null)
         {
          view = LayoutInflater.from(getContext()).inflate(R.layout.list,parent,false);
         }
         return view;
    }
}
