package com.example.pw.ui.to_see;

import android.content.ContentValues;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

public class ToSeeFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>, ConfirmDialogFragmentListener {
    private RecyclerView list;
    private RecyclerViewAdapter adapter;
    private final String dialogMessage = "Vuoi rimuovere questo film dalla lista di quelli da vedere?";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        MainActivity.seen.setVisibility(View.GONE);
        MainActivity.unseen.setVisibility(View.GONE);
        View root = inflater.inflate(R.layout.fragment_to_see, container, false);

        list = root.findViewById(R.id.list);
        GridLayoutManager manager;
        if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            manager = new GridLayoutManager(getActivity(),3);
        }else{
            manager = new GridLayoutManager(getActivity(),2);
        }
        list.setLayoutManager(manager);
        getActivity().getSupportLoaderManager().initLoader(2, null, this);


        return root;
    }
    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return new CursorLoader(getContext(), FilmProvider.FILMS, null, FilmTableHelper.TO_SEE + " LIKE 'true' ", null, null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {

        if(list.getAdapter()==null){
            adapter = new RecyclerViewAdapter(getContext(),data,this, dialogMessage);
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
        cv.put(FilmTableHelper.TO_SEE, "false");
        getContext().getContentResolver().update(FilmProvider.FILMS,cv,FilmTableHelper._ID + " = "+ id,null);
    }

    @Override
    public void onNegativePressed() {

    }
}
