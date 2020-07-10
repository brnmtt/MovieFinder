package com.example.pw.ui.home;


import android.content.ContentValues;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;

import android.util.Log;
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


import com.example.pw.EndlessScrollListener;
import com.example.pw.R;

import com.example.pw.Utilities;
import com.example.pw.activity.MainActivity;
import com.example.pw.database.FilmProvider;
import com.example.pw.database.FilmTableHelper;
import com.example.pw.ui.adapter.RecyclerViewAdapter;
import com.example.pw.ui.dialogFragment.ConfirmDialogFragmentListener;

import static com.example.pw.Utilities.see;


public class HomeFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>, ConfirmDialogFragmentListener{
    private RecyclerView list;
    private RecyclerViewAdapter adapter;
    private final String dialogMessage = "Vuoi aggiungere questo film alla lista di quelli da vedere?";



    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        MainActivity.seen.setVisibility(View.VISIBLE);
        MainActivity.unseen.setVisibility(View.VISIBLE);

        MainActivity.seen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(see == 1){
                    see = 0;
                }else{
                    see = 1;
                }
                getActivity().getSupportLoaderManager().restartLoader(1, null, HomeFragment.this);

            }
        });

        MainActivity.unseen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(see == 2){
                    see = 0;
                }else{
                    see = 2;
                }
                getActivity().getSupportLoaderManager().restartLoader(1, null, HomeFragment.this);
            }
        });



        list = root.findViewById(R.id.list);
        GridLayoutManager manager;
        if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            manager = new GridLayoutManager(getActivity(),3);
        }else{
            manager = new GridLayoutManager(getActivity(),2);
        }

        list.setLayoutManager(manager);

        getActivity().getSupportLoaderManager().initLoader(1, null, this);
        EndlessScrollListener listener = new EndlessScrollListener(manager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                Utilities.getData(getContext());
            }
        };
        list.addOnScrollListener(listener);

        return root;
    }


    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        Log.d("see", ""+see);
        switch (see){
            case 0:
                return new CursorLoader(getContext(), FilmProvider.FILMS, null, null, null, null);

            case 1:
                return new CursorLoader(getContext(), FilmProvider.FILMS, null, FilmTableHelper.SEEN + " LIKE 'true' ", null, null);

            case 2:
                return new CursorLoader(getContext(), FilmProvider.FILMS, null, FilmTableHelper.SEEN + " LIKE 'false' ", null, null);

        }
        return null;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {

        if(list.getAdapter()==null){
            adapter = new RecyclerViewAdapter(getContext(),data, this, dialogMessage);
            list.setAdapter(adapter);
        }else{
            adapter.setCursor(data);
        }

        adapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }


    @Override
    public void onPositivePressed(long id) {
        ContentValues cv = new ContentValues();
        cv.put(FilmTableHelper.TO_SEE, "true");
        getContext().getContentResolver().update(FilmProvider.FILMS,cv,FilmTableHelper._ID + " = "+ id,null);
    }

    @Override
    public void onNegativePressed() {

    }
}
