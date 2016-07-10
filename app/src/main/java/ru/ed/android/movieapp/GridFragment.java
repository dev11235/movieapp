package ru.ed.android.movieapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.ArrayList;

public class GridFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ArrayList<Integer> tst = new ArrayList<>();
        tst.add(R.drawable.atesparkasi);
        tst.add(R.drawable.experiment);
        tst.add(R.drawable.instructor);
        tst.add(R.drawable.interstellar);
        tst.add(R.drawable.windiswatching);


        ImageArrayAdapter<Integer> imageArrayAdapter = new ImageArrayAdapter(
                getActivity(),
                R.layout.poster,
                R.id.poster_imageview,
                tst);

        View rootView = inflater.inflate(R.layout.fragment_grid, container, false);

        GridView gridView = (GridView) rootView.findViewById(R.id.movies_gridview);
        gridView.setAdapter(imageArrayAdapter);
        //gridView.setNumColumns(3);
        //gridView.setHorizontalSpacing(0);

        return rootView;
    }
}
