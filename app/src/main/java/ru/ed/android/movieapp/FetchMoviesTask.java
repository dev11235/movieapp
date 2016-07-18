package ru.ed.android.movieapp;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kma on 7/10/16.
 */
public class FetchMoviesTask extends AsyncTask<String, Void, Movie[]> {

    private final String LOG_TAG = FetchMoviesTask.class.getSimpleName();



    @Override
    protected Movie[] doInBackground(String... params) {

        // These two need to be declared outside the try/catch
        // so that they can be closed in the finally block.
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String moviesJson = null;



        try {
            final String MOVIEDB_BASE_URL = "http://api.themoviedb.org/3/";
            final String API_KEY = "api_key";

            Uri builtUri = Uri.parse(MOVIEDB_BASE_URL).buildUpon()
                    .appendEncodedPath("movie/popular")
                    .appendQueryParameter(API_KEY, BuildConfig.MOVIEDB_API_KEY)
                    .build();

            URL url = new URL(builtUri.toString());

            // Create the request to OpenWeatherMap, and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }
            moviesJson = buffer.toString();
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error ", e);
            // If the code didn't successfully get the weather data, there's no point in attemping
            // to parse it.
            return null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }

        try {
            return getMoviesIdsFromJson(moviesJson);
        } catch (Exception e) {
            Log.e(LOG_TAG, e.getMessage(), e);
        }

        // This will only happen if there was an error getting or parsing the forecast.
        return null;
    }

    private Movie[] getMoviesIdsFromJson(String json) {
        final String OMDB_RESULTS = "results";
        final String OMDB_POSTER = "poster_path";
        final String OMDB_TITLE = "original_title";
        List<Movie> posterUrls = new ArrayList<>();
        try {
            JSONObject moviesJson = new JSONObject(json);
            JSONArray moviesArray = moviesJson.getJSONArray(OMDB_RESULTS);

            final String IMG_MOVIEDB_BASE_URL = "http://image.tmdb.org/t/p/w300";
            Uri imgBasePath = Uri.parse(IMG_MOVIEDB_BASE_URL);

            for (int i = 0; i < moviesArray.length(); i++) {
                String poster = moviesArray.getJSONObject(i).getString(OMDB_POSTER);
                String title = moviesArray.getJSONObject(i).getString(OMDB_TITLE);
                posterUrls.add(new Movie(imgBasePath.buildUpon().appendEncodedPath(poster).toString(), title));
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Error parsing movies json", e);
        }
        return posterUrls.toArray(new Movie[posterUrls.size()]);
    }

    @Override
    protected void onPostExecute(Movie[] result) {
        if (result != null) {
            /*mForecastAdapter.clear();
            for(String dayForecastStr : result) {
                mForecastAdapter.add(dayForecastStr);
            }*/
            // New data is back from the server.  Hooray!
        }
    }
}
