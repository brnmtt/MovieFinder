package com.example.pw.ui.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import com.bumptech.glide.request.RequestOptions;
import com.example.pw.Utilities;
import com.example.pw.activity.FilmDescriptionActivity;
import com.example.pw.R;
import com.example.pw.activity.MainActivity;
import com.example.pw.database.FilmProvider;
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
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        if(!film.moveToPosition(position)){
            return;
        }
        final String seen = film.getString(film.getColumnIndex(FilmTableHelper.SEEN));
        final long id = film.getLong(film.getColumnIndex(FilmTableHelper._ID));
        if(seen.equals("false")){
            Glide.with(context)
                    .load(R.drawable.not_seen)
                    .into(holder.seen);
        }else{
            Glide.with(context)
                    .load(R.drawable.seen)
                    .into(holder.seen);
        }

        holder.seen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues cv = new ContentValues();
                if(seen.equals("false")){
                    Glide.with(context)
                            .load(R.drawable.seen)
                            .into(holder.seen);

                    cv.put(FilmTableHelper.SEEN,"true");

                }else{
                    Glide.with(context)
                            .load(R.drawable.not_seen)
                            .into(holder.seen);
                    cv.put(FilmTableHelper.SEEN,"false");
                }
                context.getContentResolver().update(FilmProvider.FILMS,cv,FilmTableHelper._ID + " = "+ id,null);
            }
        });

        String url = Utilities.imagePrefix+film.getString(film.getColumnIndex(FilmTableHelper.IMAGEPATH));

        Glide.with(context).applyDefaultRequestOptions(new RequestOptions().placeholder(R.drawable.image_missing).error(R.drawable.image_missing)).
                load(url).into(holder.image);


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
        ImageButton seen;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.icon);
            seen = itemView.findViewById(R.id.seenImageButton);
        }
    }
}




////////////////////////////////////////////////








