package com.example.zendynamix.movieworld;

import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

// FETCH JSON DATA HELPER
public class MovieHelper {
    final static String LOG_TAG = MovieHelper.class.getSimpleName();
    private static final String API_KEY = "eff5cbdefa3fe4c3f1ab146cb57d94be";
    private static final String APPID_PARAM = "api_key";

    private MovieHelper() {
    }

    public static String fetch(String urlString) {
        HttpURLConnection urlConnection = null;
        try {
            Uri builtUri = Uri.parse(urlString).buildUpon()
                    .appendQueryParameter(APPID_PARAM, API_KEY)
                    .build();
            URL url = new URL(builtUri.toString());
            Log.v(LOG_TAG, "*****" + url);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line = null;
            while ((line = reader.readLine()) != null) {
                // buffer for debugging.
                buffer.append(line + "\n");
            }
            reader.close();
            String jmovieString = buffer.toString();
            Log.e(LOG_TAG, "jsondata" + jmovieString);
            return jmovieString;
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error ", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }

        return "";
    }
}
