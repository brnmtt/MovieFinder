package com.example.pw.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.pw.Film;
import com.example.pw.activity.FilmDescriptionActivity;
import com.example.pw.R;
import com.example.pw.activity.MainActivity;

import java.util.ArrayList;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private Context context;

    public RecyclerViewAdapter(ArrayList<Film> data, Context context) {

        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.film, parent, false);
        return new ViewHolder(view,context);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        String url = MainActivity.imagePrefix+MainActivity.films.get(position).getImagePath();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, FilmDescriptionActivity.class);
                intent.putExtra("position", position);

                context.startActivity(intent);
            }
        });

        Glide.with(context)
                .load(url)
                .into(holder.icon);
    }


    @Override
    public int getItemCount() {
        Log.d("Film_size",""+MainActivity.films.size());
        return MainActivity.films.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView icon;
        Context context;
        public ViewHolder(@NonNull View itemView, final Context context) {
            super(itemView);
            icon = itemView.findViewById(R.id.icon);

            this.context = context;
        }

    }
}
