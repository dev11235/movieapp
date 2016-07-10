package ru.ed.android.movieapp;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;


import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageArrayAdapter<T> extends ArrayAdapter<T> {


    private final LayoutInflater inflater;
    private final  int resource;
    private final int imageViewResourceId;
    private final Context context;

    public ImageArrayAdapter(Context context, int resource, int imageViewResourceId, List<T> objects) {
        super(context, resource, imageViewResourceId, objects);
        this.resource = resource;
        this.imageViewResourceId = imageViewResourceId;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // TODO make image view programmatically
        View view;
        ImageView imageView;

        if (convertView == null) {
            view = inflater.inflate(resource, parent, false);
        } else {
            view = convertView;
        }

        try {
            imageView = (ImageView) view.findViewById(imageViewResourceId);
        } catch (ClassCastException e) {
            Log.e("ImageArrayAdapter", "You must supply a resource ID for a ImageView");
            throw new IllegalStateException(
                    "ImageArrayAdapter requires the resource ID to be a ImageView", e);
        }

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();

        int width = metrics.widthPixels;

        T item = getItem(position);
        if (item instanceof Integer) {
            Picasso.with(context).load((Integer)item).resize(width/2, 0).into(imageView);
        } else {
            Picasso.with(context).load(item.toString()).resize(width/2, 0).into(imageView);
        }

        return view;
    }
}