package com.example.pw.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.pw.Film;
import com.example.pw.R;

public class FilmDescriptionActivity extends AppCompatActivity {
    TextView titolo,descrizione;
    ImageView banner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_description);
        int pos = getIntent().getIntExtra("position",0);
        Film film = MainActivity.films.get(pos);
        titolo = findViewById(R.id.title);
        descrizione = findViewById(R.id.description);
        banner = findViewById(R.id.banner);
        titolo.setText(film.getTitle());
        descrizione.setText(film.getDescription());
        String url = MainActivity.imagePrefix + film.getBackDropPath();
        Glide.with(this)
                .load(url)
                .into(banner);

    }
}
