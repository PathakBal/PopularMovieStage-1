package com.example.balendra.popularmovies.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.balendra.popularmovies.R;
import com.example.balendra.popularmovies.model.PopularMovie;
import com.example.balendra.popularmovies.utils.Constant;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieAdapter extends BaseAdapter {
    private Context mContext;
    private List<PopularMovie> movie;

    public MovieAdapter(Context mContext, List<PopularMovie> popularMovies) {
        this.mContext = mContext;
        this.movie = popularMovies;
    }

    @Override
    public int getCount() {
        return movie.size();
    }

    @Override
    public Object getItem(int position) {
        return movie.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PopularMovie movie = (PopularMovie) getItem(position);
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setAdjustViewBounds(true);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }

        Picasso.with(mContext)
                .load(Constant.POSTER_URL + movie.getPosterPath())
                .error(R.drawable.sample_0)
                .into(imageView);
        return imageView;

    }

    public void clearAll() {
        if (movie.size() > 0) {
            movie.clear();
        }
        notifyDataSetInvalidated();
    }

    public void setMovie(List<PopularMovie> data) {
        movie.addAll(data);
        notifyDataSetChanged();
    }

}
