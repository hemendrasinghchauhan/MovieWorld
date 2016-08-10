package com.example.zendynamix.movieworld;
// MOVIE DATA ADAPTER

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class  MovieAdapter extends ArrayAdapter<MovieData> {
    private static final String LOG_TAG = MovieAdapter.class.getSimpleName();


    private List<MovieData> movieDataList;

    public void setData(List<MovieData> movieDataList) {
        this.movieDataList.clear();
        this.movieDataList.addAll(movieDataList);
        Log.v(LOG_TAG, "in agdapter************** " + movieDataList);
        notifyDataSetChanged();
    }

    /**
     * This is our own custom constructor (it doesn't mirror a superclass constructor).
     * The context is used to inflate the layout file, and the List is the data we want
     * to populate into the lists
     *
     * @param context   The current context. Used to inflate the layout file.
     * @param movieData A List of image objects to display in a grid
     */
    public MovieAdapter(Activity context, List<MovieData> movieData) {

        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for two TextViews and an ImageView, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context, 0, movieData);

        movieDataList = movieData;

    }

    /**
     * Provides a view for an AdapterView (ListView, GridView, etc.)
     *
     * @param position    The AdapterView position that is requesting a view
     * @param convertView The recycled view to populate.
     *                    (search online for "android view recycling" to learn more)
     * @param parent      The parent ViewGroup that is used for inflation.
     * @return The View for the position in the AdapterView.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Gets the Movi object from the ArrayAdapter at the appropriate position

        MovieData movieData = getItem(position);

        // Adapters recycle views to AdapterViews.
        // If this is a new View object we're getting, then inflate the layout.
        // If not, this view already has the layout inflated from a previous call to getView,
        // and we modify the View widgets as usual.
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.grid_detail, parent, false);
        }

        ImageView moviePoster = (ImageView) convertView.findViewById(R.id.movi_poster);
        Picasso.with(getContext()).load(movieData.getmPoster()).into(moviePoster);

//        TextView movieReview = (TextView) convertView.findViewById(R.id.move_review);
//        movieReview.setText(movieData.mMovieDate);
//
//        TextView movieDate = (TextView) convertView.findViewById(R.id.movie_Date);
//        movieDate.setText(movieData.mMovieDate);
//
//        TextView movieId=convertView.findViewById(R.id.mMovieId);
//        movieId.setText(movieData.getmMovieId());
//
//        TextView movieTitle = (TextView) convertView.findViewById(R.id.movie_Title);
//        movieTitle.setText(movieData.mMovieTitle);
//
//        TextView movieRating = (TextView) convertView.findViewById(R.id.movie_rating);
//        movieRating.setText(movieData.mMovieRating);


        return convertView;
    }
}