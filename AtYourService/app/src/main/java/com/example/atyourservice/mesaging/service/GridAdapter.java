package com.example.atyourservice.mesaging.service;

import android.content.Context;
import android.media.MediaDrm;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.atyourservice.R;

public class GridAdapter extends BaseAdapter {
    //https://www.geeksforgeeks.org/gridview-using-baseadapter-in-android-with-example/

    Context context;
    int[] sticker;

    LayoutInflater inflater;

    public GridAdapter(Context context, int[] sticker) {
        this.context = context;
        this.sticker = sticker;
    }

    @Override
    public int getCount() {
        return sticker.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(inflater == null){
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (convertView == null){
            convertView = inflater.inflate(R.layout.sticker_grid,null);
        }

        ImageView imageView= convertView.findViewById(R.id.stickerGrid);
        imageView.setImageResource(sticker[position]);
        return convertView;
    }
}