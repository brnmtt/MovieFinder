package com.example.pw.ui.home;


import android.database.Cursor;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.pw.R;

import com.example.pw.database.FilmProvider;
import com.example.pw.ui.adapter.RecyclerViewAdapter;




public class HomeFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private RecyclerView list;
    private RecyclerViewAdapter adapter;




    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);



        list = root.findViewById(R.id.list);
        list.setLayoutManager(new GridLayoutManager(getActivity(),2));
        getActivity().getSupportLoaderManager().initLoader(1, null, this);


        return root;
    }


    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return new CursorLoader(getContext(), FilmProvider.FILMS, null, null, null, null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        adapter = new RecyclerViewAdapter(getContext(),data);
        list.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }
}
