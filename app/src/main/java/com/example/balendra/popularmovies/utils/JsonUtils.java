package com.example.balendra.popularmovies.utils;

import android.util.Log;

import com.example.balendra.popularmovies.model.PopularMovie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private static String JSON_KEY_POSTER_PATH="poster_path";
    private static String JSON_KEY_TITLE="title";
    private static String JSON_KEY_OVERVIEW="overview";
    private static String JSON_KEY_RELEASE_DATE="release_date";
    private static String JSON_KEY_VOTE_AVERAGE="vote_average";
    private static String JSON_KEY_RESULT="results";

    static JSONObject jsonObject;
    static JSONArray jsonArrayResult;

    public static List<PopularMovie> getMovieList() {
        return movieList;
    }

    static List<PopularMovie> movieList;

    public static List<PopularMovie> parsePopularMovie(String popularMovieJson){

        movieList = new ArrayList<>();

        try {
            jsonObject = new JSONObject(popularMovieJson);
            jsonArrayResult = jsonObject.getJSONArray(JSON_KEY_RESULT);
            Log.d("Balendra"," Size is: "+jsonArrayResult.length());
            for (int i=0;i<jsonArrayResult.length();i++) {
                JSONObject objectfromArray = jsonArrayResult.getJSONObject(i);
                String extractedPosterPath = objectfromArray.getString(JSON_KEY_POSTER_PATH);
                String extractedTitle = objectfromArray.getString(JSON_KEY_TITLE);
                String extractedReleaseDate = objectfromArray.getString(JSON_KEY_RELEASE_DATE);
                String extractedVote = objectfromArray.getString(JSON_KEY_VOTE_AVERAGE);
                String extractedOverview= objectfromArray.getString(JSON_KEY_OVERVIEW);
                movieList.add(new PopularMovie(extractedPosterPath,extractedTitle,extractedOverview,extractedReleaseDate,extractedVote));
            }
        } catch (JSONException e) {
            Log.d("Balendra","From JSON parsing"+e.toString());
            e.printStackTrace();

        }
        Log.d("Balendra"," Size is: "+movieList.size());
        return movieList;
    }
}
