package com.example.pw.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import com.example.pw.activity.FilmDescriptionActivity;
import com.example.pw.R;
import com.example.pw.activity.MainActivity;
import com.example.pw.database.FilmTableHelper;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    Context context;
    Cursor film;


    public RecyclerViewAdapter(Context context, Cursor film) {
        this.context = context;
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
        String url = MainActivity.imagePrefix+film.getString(film.getColumnIndex(FilmTableHelper.IMAGEPATH));
        final long id = film.getLong(film.getColumnIndex(FilmTableHelper._ID));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, FilmDescriptionActivity.class);
                intent.putExtra("position", id);

                context.startActivity(intent);
            }
        });

        Glide.with(context)
                .load(url)
                .into(holder.image);
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








