package com.example.zendynamix.movieworld;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


// PLACE HOLDER FRAGMENT
public class MainActivityFragment extends Fragment {
    private static final String LOG_TAG = MainActivityFragment.class.getSimpleName();
    private List<MovieData> data = new ArrayList<>();
    private MovieAdapter movieAdapter;
    RelativeLayout drawerPane;
    DrawerLayout drawerLayout;
    private ArrayList<DrawerItemData> drawerData = new ArrayList<>();
    DrawerListAdapter drawerListAdapter;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar dtoolbar;

    public MainActivityFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        drawerData.add(new DrawerItemData("Home", "Go to Main", R.drawable.play_48));
        drawerData.add(new DrawerItemData("Top Rated", "Get Top Rated movies", R.drawable.play_48));
        drawerData.add(new DrawerItemData("Popular", "Get popular movies", R.drawable.play_48));

       // dtoolbar = (Toolbar) rootView.findViewById(R.id.my_awesome_toolbar);

       // dtoolbar.setLogo(R.drawable.ic_sandwitch);

        drawerLayout = (DrawerLayout) rootView.findViewById(R.id.drawerLayout);
        drawerPane = (RelativeLayout) rootView.findViewById(R.id.drawerPane);
        ListView drlist = (ListView) rootView.findViewById(R.id.navList);
        drawerListAdapter = new DrawerListAdapter(getActivity(), drawerData);
        drlist.setAdapter(drawerListAdapter);

        drlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                }
                if (position == 1) {
                    FetchMoviedata fetchMoviedata = new FetchMoviedata("top_rated");
                    fetchMoviedata.execute();
                } else {
                    FetchMoviedata fetchMoviedata = new FetchMoviedata("popular");
                    fetchMoviedata.execute();
                }

            }
        });
        actionBarDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, R.string.drawer_open, R.string.drawer_close) {

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                Log.v(LOG_TAG, "closed");
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                Log.v(LOG_TAG, "open");
            }
        };
        drawerLayout.addDrawerListener(actionBarDrawerToggle);


        movieAdapter = new MovieAdapter(getActivity(), data);
        GridView gridView = (GridView) rootView.findViewById(R.id.gridview);
        gridView.setAdapter(movieAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                // Send intent to SingleViewActivity
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra("pos", position);
                intent.putExtra("movieDataList", (Serializable) data);
                Log.v(LOG_TAG, "****" + position);
                startActivity(intent);

            }
        });

        FetchMoviedata fetchMoviedata = new FetchMoviedata("popular");
        fetchMoviedata.execute();

        return rootView;

    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.refresh_fetch, menu);


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {

            return true;
        }

        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
    }

//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatemen
//       if (id == R.id.action_refresh) {
//
    // return true;
//        }
//        if (id == R.id.action_popular) {
//            FetchMoviedata fetchMoviedata = new FetchMoviedata("popular");
//            fetchMoviedata.execute();
//            return true;
//        }
//        if (id == R.id.action_toprated) {
//            FetchMoviedata fetchMoviedata = new FetchMoviedata("top_rated");
//            fetchMoviedata.execute();
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//
    // }

    @Override
    public void onResume() {
        super.onResume();
    }


    public class FetchMoviedata extends AsyncTask<String, Void, List<MovieData>> {

        final String LOG_TAG = MainActivityFragment.class.getSimpleName();
        private String choice = null;

        public FetchMoviedata(String choice) {
            this.choice = choice;
        }

        private List<MovieData> getMovieDataFromJson(String jmovieString)
                throws JSONException {

            Log.e(LOG_TAG, "****get movie** " + jmovieString);

            final String PAGE_NO = "page";
            // RESULT ARRAY
            final String NUMBER_MOVIES = "results";
            //CONTAINS
            final String MOVI_POSTER = "poster_path";
            final String MOVI_OVERVIEW = "overview";
            final String MOVI_RDATE = "release_date";
            //OBJECTS
            final String MOVIE_ID = "id";
            final String MOVI_TITLE = "original_title";
            final String MOVI_RATING = "vote_average";

            List<MovieData> result = new ArrayList();

            try {
                JSONObject movieObject = new JSONObject(jmovieString);
                Integer p = Integer.valueOf(movieObject.getString(PAGE_NO));
                JSONArray movieArray = movieObject.getJSONArray(NUMBER_MOVIES);


                Log.e(LOG_TAG, "****get movie** " + p);

                for (int j = 0; j <= p; j++) {

                    for (int i = 0; i < movieArray.length(); i++) {

                        JSONObject moviObj = movieArray.getJSONObject(i);
                        String http = "http://image.tmdb.org/t/p/w185/";
                        String moviPoster = http + moviObj.getString(MOVI_POSTER);
                        String movieOverView = moviObj.getString(MOVI_OVERVIEW);
                        String movieDate = moviObj.getString(MOVI_RDATE);
                        String movieId = moviObj.getString(MOVIE_ID);
                        String movieTitle = moviObj.getString(MOVI_TITLE);
                        String movieRating = moviObj.getString(MOVI_RATING);
                        MovieData data = new MovieData(moviPoster, movieOverView, movieDate, movieId, movieTitle, movieRating);

                        result.add(data);

                    }
                }
            } catch (JSONException e) {
                Log.e(LOG_TAG, "Error ", e);
            }


            return result;
        }

        @Override
        protected List<MovieData> doInBackground(String... param) {
            try {
                final String FORECAST_BASE_URL = "https://api.themoviedb.org/3/movie/" + choice + "?";
                //"https://api.themoviedb.org/3/movie/popular?";
                final String APPEND_TO_RESPONSE = "append_to_response";
                String append_to_response = "videos";
                final String PAGE = "page";
                int page = 2;

                Log.v(LOG_TAG, "*****" + page);
                Uri builtUri = Uri.parse(FORECAST_BASE_URL).buildUpon()
                        .appendQueryParameter(APPEND_TO_RESPONSE, append_to_response)
                        .appendQueryParameter(PAGE, Integer.toString(page))
                        .build();
                return getMovieDataFromJson(MovieHelper.fetch(builtUri.toString()));
            } catch (JSONException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
            }
            return null;
        }


        @Override
        protected void onPostExecute(List<MovieData> result) {
            if (result != null) {
                Log.e(LOG_TAG, "Result>>>>>>" + result);
                movieAdapter.setData(result);
            }
        }


    }
}




