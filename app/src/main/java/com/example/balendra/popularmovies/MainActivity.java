package com.example.balendra.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.balendra.popularmovies.adapter.MovieAdapter;
import com.example.balendra.popularmovies.model.PopularMovie;
import com.example.balendra.popularmovies.utils.Constant;
import com.example.balendra.popularmovies.utils.JsonUtils;
import com.example.balendra.popularmovies.utils.NetworkUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    MovieAdapter imageAdapter;
    List<PopularMovie> movieInfo;
    GridView gridview;
    private static final String KEY_MOVIE_LIST = "MOVIE_LIST";
    boolean isByVotedClicked = false;
    TextView errorTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            movieInfo = new ArrayList<>();
            if (isOnline()) {
                new DownLoadMovieInfo().execute(Constant.SORT_BY_POPULARITY);
            }
        } else {
            movieInfo = savedInstanceState.getParcelableArrayList(KEY_MOVIE_LIST);
        }
        imageAdapter = new MovieAdapter(this, movieInfo);
        gridview = (GridView) findViewById(R.id.gridView);
        gridview.setAdapter(imageAdapter);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                intent.putExtra("PopularMovie", (Parcelable) adapterView.getItemAtPosition(i));
                startActivity(intent);
                Toast.makeText(MainActivity.this, "was clicked on " + i, Toast.LENGTH_SHORT).show();
            }
        });

        errorTextView = findViewById(R.id.errormsg);

        if (!isOnline()) {
            noNetworkInfoMsg();
            gridview.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuItemSelected = item.getItemId();
        if (menuItemSelected == R.id.byVoted) {
            isByVotedClicked = true;
            Toast.makeText(MainActivity.this, "Clicked on By Voted" + Constant.SORT_BY_VOTE, Toast.LENGTH_LONG).show();
            imageAdapter.clearAll();
            imageAdapter.notifyDataSetInvalidated();
            gridview.invalidateViews();
            new DownLoadMovieInfo().execute(Constant.SORT_BY_VOTE);
        } else {
            if (isByVotedClicked) {
                Toast.makeText(MainActivity.this, "Clicked on By Voted" + Constant.SORT_BY_POPULARITY, Toast.LENGTH_LONG).show();
                imageAdapter.clearAll();
                imageAdapter.notifyDataSetInvalidated();
                gridview.invalidateViews();
                new DownLoadMovieInfo().execute(Constant.SORT_BY_POPULARITY);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(KEY_MOVIE_LIST, (ArrayList<? extends Parcelable>) movieInfo);
        super.onSaveInstanceState(outState);
    }

    public class DownLoadMovieInfo extends AsyncTask<String, Void, List<PopularMovie>> {

        @Override
        protected List<PopularMovie> doInBackground(String... string) {
            String results = null;
            List<PopularMovie> result = null;
            URL url = NetworkUtils.buildURL(string[0]);
            try {
                results = NetworkUtils.getResponseFromHttpUrl(url);
                result = JsonUtils.parsePopularMovie(results);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(List<PopularMovie> popularMovies) {
            super.onPostExecute(popularMovies);
            imageAdapter.setMovie(popularMovies);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    public void noNetworkInfoMsg() {
        errorTextView.setVisibility(View.VISIBLE);
    }


    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
