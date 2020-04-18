package com.example.pw.activity;

import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pw.Film;
import com.example.pw.R;
import com.example.pw.ui.RecyclerViewAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    public static ArrayList<Film> films;
    public static ArrayList<Film> toSeeFilm;
    public static final String imagePrefix = "https://image.tmdb.org/t/p/w500";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);

        films = new ArrayList<>();
        getData();
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);


    }
    public void getData(){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://api.themoviedb.org/3/movie/upcoming?api_key=44a49a187a15a8457aeb3e8b876092f8&language=it-IT&page=1";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                 new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {

                            JSONObject obj = new JSONObject(response);
                            JSONArray result = obj.getJSONArray("results");

                            for(int i = 0 ; i < result.length() ; i++){

                                JSONObject data = result.getJSONObject(i);
                                Film film = new Film();
                                film.setTitle(data.getString("original_title"));
                                film.setDescription(data.getString("overview"));
                                film.setImagePath(data.getString("poster_path"));
                                film.setBackDropPath(data.getString("backdrop_path"));
                                MainActivity.films.add(film);
                                Log.d("FILM", film.toString());
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("FILM", error.toString());
            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}
