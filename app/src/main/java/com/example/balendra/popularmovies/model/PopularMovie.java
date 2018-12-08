package com.example.balendra.popularmovies.model;

import android.os.Parcel;
import android.os.Parcelable;

public class PopularMovie implements Parcelable {

    String posterPath;
    String overView;
    String title;
    String releaseDate;

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setVoteAverage(String voteAverage) {
        this.voteAverage = voteAverage;
    }

    String voteAverage;

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public PopularMovie(String extractedPosterPath){

        this.posterPath = posterPath;

    }
    public PopularMovie(String extractedPosterPath, String extractedTitle, String extractedOverview, String extractedReleaseDate, String extractedVote) {
        this.posterPath = extractedPosterPath;
        this.overView=extractedOverview;
        this.title=extractedTitle;
        this.releaseDate=extractedReleaseDate;
        this.voteAverage=extractedVote;
    }

    private PopularMovie(Parcel parcel) {

        title = parcel.readString();
        posterPath = parcel.readString();
        overView = parcel.readString();
        voteAverage = parcel.readString();
        releaseDate = parcel.readString();
    }

    public PopularMovie(String posterPath, String overView, String title) {
        this.posterPath = posterPath;
        this.overView = overView;
        this.title = title;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public void setOverView(String overView) {
        this.overView = overView;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverView() {
        return overView;
    }

    public String getTitle() {
        return title;
    }

    public String getPosterPath() {
        return posterPath;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(posterPath);
        dest.writeString(overView);
        dest.writeString(voteAverage);
        dest.writeString(releaseDate);

    }
    public static final Parcelable.Creator<PopularMovie> CREATOR = new Parcelable.Creator<PopularMovie>() {

        @Override
        public PopularMovie createFromParcel(Parcel parcel) {
            return new PopularMovie(parcel);
        }

        @Override
        public PopularMovie[] newArray(int i) {
            return new PopularMovie[i];
        }
    };
}
