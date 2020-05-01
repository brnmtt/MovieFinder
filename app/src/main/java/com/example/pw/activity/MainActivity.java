package com.example.pw.activity;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.example.pw.R;
import com.example.pw.database.FilmProvider;
import com.example.pw.database.FilmTableHelper;
import com.example.pw.ui.home.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;


public class MainActivity extends AppCompatActivity {

    public static  final String url ="https://api.themoviedb.org/3/movie/upcoming?api_key=44a49a187a15a8457aeb3e8b876092f8&language=it-IT&page=";
    public static final String imagePrefix = "https://image.tmdb.org/t/p/w500";
    HomeFragment fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        getData();
        fragment = new HomeFragment();



        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

    }

    public void getData(){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = MainActivity.url+"1";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONObject obj = new JSONObject(response);
                            JSONArray result = obj.getJSONArray("results");

                            for(int i = 0 ; i < result.length() ; i++){

                                JSONObject data = result.getJSONObject(i);
                                String id = data.getString("id");
                                Cursor cursor = getContentResolver().query(FilmProvider.FILMS,null ,FilmTableHelper.API_ID + " = " + id ,null,null );
                                if(cursor.getCount()<=0){
                                    ContentValues cv = new ContentValues();
                                    cv.put(FilmTableHelper.TITLE, data.getString("title"));
                                    cv.put(FilmTableHelper.DESCRIPTION, data.getString("overview"));
                                    cv.put(FilmTableHelper.IMAGEPATH, data.getString("poster_path"));
                                    cv.put(FilmTableHelper.BACKDROPPATH, data.getString("backdrop_path"));
                                    cv.put(FilmTableHelper.API_ID, id);

                                    getContentResolver().insert(FilmProvider.FILMS,cv);
                                }
                                //insert image in db
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

        queue.add(stringRequest);

    }
}
