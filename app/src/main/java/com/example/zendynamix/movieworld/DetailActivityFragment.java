package com.example.zendynamix.movieworld;
//DETAIL VIEW FRAGMENT

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailActivityFragment extends Fragment {
    private static final String URL_PREFIX = "https://api.themoviedb.org";
    private static final String YOUR_DEVELOPER_KEY = "AIzaSyCrFZhspLD9dP9LKDi7my0C-nwA__fiqNI";
    private static final String MOVIE_SHARE = "#MOVIE APP";
    private static final String LOG_TAG = DetailActivity.class.getSimpleName();
    private List<MovieData> movieDataList;
    private int position;
    private List<TrailerMovie> tDataList = new ArrayList<>();
    private MovieData mDetail;
    private TrailerMovie mTdata;
    private ArrayAdapter trailerAdapter;
    String trailer;


    public DetailActivityFragment() {
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        Bundle bundle = getActivity().getIntent().getExtras();
        if (bundle != null) {
            position = bundle.getInt("pos");
            movieDataList = (List<MovieData>) bundle.getSerializable("movieDataList");
            mDetail = movieDataList.get(position);
            tDataList.clear();
            FetchMoviTrailer fetchMoviedata = new FetchMoviTrailer();
            fetchMoviedata.execute();

            ImageView moviePoster = (ImageView) rootView.findViewById(R.id.dMovie_poster);
            Picasso.with(getContext()).load(movieDataList.get(position).mPoster).into(moviePoster);

            TextView movieReview = (TextView) rootView.findViewById(R.id.dMovie_review);
            movieReview.setText(mDetail.getmMovieOverView());

            TextView movieDate = (TextView) rootView.findViewById(R.id.dMovie_Date);
            movieDate.setText(mDetail.getmMovieDate());

            TextView movieTitle = (TextView) rootView.findViewById(R.id.dMovie_Title);
            movieTitle.setText(mDetail.getmMovieTitle());

            TextView movieRatingt = (TextView) rootView.findViewById(R.id.dMovie_ratingt);
            movieRatingt.setText(mDetail.getMovieRating());

            RatingBar movieRating = (RatingBar) rootView.findViewById(R.id.dMovie_rating);

            movieRating.setNumStars((int) Double.parseDouble(String.valueOf(mDetail.getMovieRating())));

            String movieId = mDetail.getmMovieId();
            Log.v(LOG_TAG, ">>>....>>>ID" + movieId);
        }

        trailerAdapter = new ArrayAdapter(getContext(), R.layout.trailer_list_view, R.id.trailer_view, tDataList);
        View roootView = inflater.inflate(R.layout.trailer_list, container, false);
        ListView listView = (ListView) roootView.findViewById(R.id.list_view_trailer);
        listView.setAdapter(trailerAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                mTdata = tDataList.get(position - 1);
                trailer = mTdata.getmMovieTrailer();
//                Intent intent = new Intent(Intent.ACTION_VIEW,
//                        Uri.parse("http://www.youtube.com/watch?v=" + trailer));
//                startActivity(intent);

                Intent intentone = new Intent(getActivity(), MediaPlayer.class);
                intentone.putExtra("trailer", trailer);
                startActivity(intentone);


            }
        });

        listView.addHeaderView(rootView);
        return roootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.sharedetails, menu);
        MenuItem menuItem = menu.findItem(R.id.action_share);
        ShareActionProvider mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);
        if (mShareActionProvider != null) {
            mShareActionProvider.setShareIntent(createShareMovieIntent());
        } else {
            Log.d(LOG_TAG, "Share Action Provider is null?");
        }
    }

    private Intent createShareMovieIntent() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, "hello" + mDetail.mMovieOverView + MOVIE_SHARE);
        return shareIntent;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatemen
        if (id == R.id.action_share) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public class FetchMoviTrailer extends AsyncTask<String, Void, List<TrailerMovie>> {
        final String LOG_TAG = FetchMoviTrailer.class.getSimpleName();

        private List<TrailerMovie> getTrailerFromJson(String jtrailerString) throws JSONException {
            final String TRAILER_LIST = "results";
            final String MOVIE_TRAILER = "key";
            List<TrailerMovie> tresult = new ArrayList<>();
            try {

                JSONObject trailerjsonObject = new JSONObject(jtrailerString);
                JSONArray trailerArray = trailerjsonObject.getJSONArray(TRAILER_LIST);
                for (int i = 0; i < trailerArray.length(); i++) {
                    JSONObject jsonTrailerObject = trailerArray.getJSONObject(i);
                    String trailer = jsonTrailerObject.getString(MOVIE_TRAILER);
                    TrailerMovie tData = new TrailerMovie(trailer);
                    tresult.add(tData);
                }
            } catch (JSONException e) {
                Log.e(LOG_TAG, "Error ", e);
            }
            return tresult;
        }

        @Override
        protected List<TrailerMovie> doInBackground(String... params) {
            try {
                Log.v(LOG_TAG, ">>>...////.>>>ID" + mDetail.getmMovieId());
                String uri = URL_PREFIX + "/3/movie/" + mDetail.getmMovieId() + "/videos?";
                return getTrailerFromJson(MovieHelper.fetch(uri));
            } catch (JSONException e) {
                Log.e(LOG_TAG, "json ", e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<TrailerMovie> result) {
            if (result != null) {
                Log.e(LOG_TAG, "Result>>>***>>>" + result);
                trailerAdapter.addAll(result);
            }
        }
    }
}
