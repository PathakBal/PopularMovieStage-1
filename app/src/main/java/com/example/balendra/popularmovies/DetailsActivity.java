package com.example.balendra.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.balendra.popularmovies.model.PopularMovie;
import com.example.balendra.popularmovies.utils.Constant;
import com.example.balendra.popularmovies.utils.JsonUtils;
import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {

    TextView titleTextView;
    TextView movieRating;
    TextView releaseDateTextView;
    ImageView moviePoster;
    TextView descTextView;
    private static String POSTER_URL="http://image.tmdb.org/t/p/w185";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        titleTextView = findViewById(R.id.titleTextView);
        movieRating = findViewById(R.id.ratingTextView);
        releaseDateTextView = findViewById(R.id.releaseDateTextView);
        moviePoster = findViewById(R.id.posterImageView);
        descTextView = findViewById(R.id.overviewTextView);

        Intent intent = getIntent();
        PopularMovie movieClicked = intent.getExtras().getParcelable("PopularMovie");
        if(intent !=null && movieClicked instanceof PopularMovie){

            titleTextView.setText(movieClicked.getTitle());
            movieRating.setText(movieClicked.getVoteAverage());
            releaseDateTextView.setText(movieClicked.getReleaseDate());
            descTextView.setText(movieClicked.getOverView());

        }
        Log.d("Balendra","URL for image "+Constant.POSTER_URL +movieClicked.getPosterPath());
        Picasso.with(this)
                .load(Constant.POSTER_URL +movieClicked.getPosterPath())
                .error(R.drawable.sample_0)
                .into(moviePoster);


    }
}
