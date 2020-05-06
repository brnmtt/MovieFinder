package com.example.pw.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import com.example.pw.Utilities;
import com.example.pw.activity.FilmDescriptionActivity;
import com.example.pw.R;
import com.example.pw.activity.MainActivity;
import com.example.pw.database.FilmTableHelper;
import com.example.pw.ui.dialogFragment.ConfirmDialogFragmentListener;
import com.example.pw.ui.dialogFragment.CustomDialogFragment;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>  {
    Context context;
    Cursor film;
    ConfirmDialogFragmentListener listener;
    String dialogMessage;




    public RecyclerViewAdapter(Context context, Cursor film, ConfirmDialogFragmentListener listener, String dialogMessage) {
        this.context = context;
        this.film = film;
        this.listener = listener;
        this.dialogMessage = dialogMessage;
    }

    public void setCursor(Cursor film) {
        this.film = film;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.film, parent, false);
        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        if(!film.moveToPosition(position)){
            return;
        }

        if(!film.getString(film.getColumnIndex(FilmTableHelper.IMAGEPATH)).equals("null")){
            String url = Utilities.imagePrefix+film.getString(film.getColumnIndex(FilmTableHelper.IMAGEPATH));
            Glide.with(context)
                    .load(url)
                    .into(holder.image);
        }else{
            Glide.with(context)
                    .load(R.drawable.image_missing)
                    .into(holder.image);
        }

        final long id = film.getLong(film.getColumnIndex(FilmTableHelper._ID));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, FilmDescriptionActivity.class);
                intent.putExtra("position", id);

                context.startActivity(intent);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AppCompatActivity activity = (AppCompatActivity) context;
                FragmentManager fragmentManager = activity.getSupportFragmentManager();
                CustomDialogFragment dialogFragment = new CustomDialogFragment(id, dialogMessage, listener);

                dialogFragment.show(fragmentManager, null);
                return true;
            }
        });


    }


    @Override
    public int getItemCount() {
        if (film == null) {
            return 0;
        }
        return film.getCount();
    }



    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.icon);
        }
    }
}




////////////////////////////////////////////////








