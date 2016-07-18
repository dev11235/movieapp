package ru.ed.android.movieapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GridFragment extends Fragment {

    private final String LOG_TAG = GridFragment.class.getSimpleName();

    private ArrayAdapter<String> imageAdapter;

    GridView gridView;
    private Movie[] movies = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null && savedInstanceState.containsKey("movies")) {
            movies = (Movie[]) savedInstanceState.getParcelableArray("movies");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArray("movies", movies);
        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (movies == null) {
            FetchMoviesTask fetchMoviesTask = new FetchMoviesTask();
            fetchMoviesTask.execute();
            try {
                movies = fetchMoviesTask.get();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        View rootView = inflater.inflate(R.layout.fragment_grid, container, false);

        gridView = (GridView) rootView.findViewById(R.id.movies_gridview);
        imageAdapter = new ImageArrayAdapter<>(
                getActivity(),
                R.layout.poster,
                2);
        gridView.setAdapter(imageAdapter);

        for (Movie movie : movies) {
            imageAdapter.add(movie.getImage());
        }

        return rootView;
    }

    private void getPopularMovies() {

    }
}
