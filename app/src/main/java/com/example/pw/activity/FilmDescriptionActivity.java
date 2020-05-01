package com.example.pw.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.pw.Film;
import com.example.pw.R;
import com.example.pw.database.FilmProvider;
import com.example.pw.database.FilmTableHelper;

public class FilmDescriptionActivity extends AppCompatActivity {
    TextView titolo,descrizione;
    ImageView banner;

    long id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_description);
        id = getIntent().getLongExtra("position",0);
        titolo = findViewById(R.id.title);
        descrizione = findViewById(R.id.description);
        banner = findViewById(R.id.banner);
        riempiCampi();


    }

    private void riempiCampi() {
        Cursor cursor =getContentResolver().query(FilmProvider.FILMS,null, FilmTableHelper._ID +" = "+id,null,null);
        cursor.moveToNext();

        titolo.setText(cursor.getString(cursor.getColumnIndex(FilmTableHelper.TITLE)));
        descrizione.setText(cursor.getString(cursor.getColumnIndex(FilmTableHelper.DESCRIPTION)));
        String url = MainActivity.imagePrefix + cursor.getString(cursor.getColumnIndex(FilmTableHelper.BACKDROPPATH));
        Glide.with(this)
                .load(url)
                .into(banner);
    }
}
