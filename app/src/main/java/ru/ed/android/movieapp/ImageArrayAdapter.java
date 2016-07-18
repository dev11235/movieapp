package ru.ed.android.movieapp;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;


import com.squareup.picasso.Picasso;

import static android.widget.ImageView.ScaleType.CENTER_CROP;

public class ImageArrayAdapter<T> extends ArrayAdapter<T> {

    private static final String LOG_TAG = ImageArrayAdapter.class.getSimpleName();

    private final Context context;
    private final int columnCount;

    public ImageArrayAdapter(Context context, int resource, int columnCount) {
        super(context, resource);
        this.context = context;
        this.columnCount = columnCount;
    }

    //@Override
    public View getView2(int position, View convertView, ViewGroup parent) {

        ImageView imageView;

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        int width = metrics.widthPixels;

        if (convertView == null) {
            imageView = new ImageView(context);
            imageView.setLayoutParams(new GridView.LayoutParams(width/columnCount, ViewGroup.LayoutParams.WRAP_CONTENT));
        } else {
            imageView = (ImageView) convertView;
        }

        T item = getItem(position);
        if (item instanceof Integer) {
            Picasso.with(context).load((Integer)item).resize(width/columnCount, 0).into(imageView);
        } else {
            Picasso.with(context).load(item.toString()).resize(width/columnCount, 0).into(imageView);
        }

        return imageView;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView view = (ImageView) convertView;
        if (view == null) {
            view = new FixedRatioImageView(context);
            view.setScaleType(CENTER_CROP);
        }

        // Get the image URL for the current position.
        String url = (String) getItem(position);

        // Trigger the download of the URL asynchronously into the image view.
        Picasso.with(context) //
                .load(url) //
                //.placeholder(R.drawable.placeholder) //
                //.error(R.drawable.error) //
                //.fit() //
                .tag(context) //
                .into(view);

        return view;
    }
}