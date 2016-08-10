package com.example.zendynamix.movieworld;

import java.io.Serializable;

//MOVIE DATA
public class MovieData implements Serializable {

    String LOG_TAG = MovieData.class.getSimpleName();

    String mPoster;
    String mMovieOverView;
    String mMovieDate;
    String mMovieId;
    String mMovieTitle;
    String mMovieRating;

    public MovieData(String moviPoster, String mMovieOverView, String mMovieDate, String mMovieId, String mMovieTitle, String mMovieRating) {
        this.mPoster = moviPoster;
        this.mMovieOverView = mMovieOverView;
        this.mMovieDate = mMovieDate;
        this.mMovieId = mMovieId;
        this.mMovieTitle = mMovieTitle;
        this.mMovieRating = mMovieRating;
    }

    public String getmMovieDate() {
        return mMovieDate;
    }

    public void setmMovieDate(String mMovieDate) {
        this.mMovieDate = mMovieDate;
    }

    public String getmMovieOverView() {
        return mMovieOverView;
    }

    public void setmMovieOverView(String mMovieOverView) {
        this.mMovieOverView = mMovieOverView;
    }

    public String getmMovieTitle() {
        return mMovieTitle;
    }

    public void setmMovieId(String mMovieId) {
        this.mMovieTitle = mMovieId;
    }

    public String getmMovieId() {
        return mMovieId;
    }

    public void setmMovieTitle(String mMovieTitle) {
        this.mMovieTitle = mMovieTitle;
    }

    public String getMovieRating() {
        return mMovieRating;
    }

    public void setMovieRating(String movieRating) {
        this.mMovieRating = movieRating;
    }

    public String getmPoster() {
        return mPoster;
    }

    public void setmPoster(String mPoster) {
        this.mPoster = mPoster;
    }
}

