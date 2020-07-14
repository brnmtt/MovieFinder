package com.example.pw;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pw.database.FilmProvider;
import com.example.pw.database.FilmTableHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Utilities {
    public static int pages=1;
    public static final String url ="https://api.themoviedb.org/3/movie/popular?api_key=44a49a187a15a8457aeb3e8b876092f8&language=it-IT&region=IT&page=";
    public static final String imagePrefix = "https://image.tmdb.org/t/p/w500";
    public static int see = 0; // 0 = visualizzazione normale, 1 = solo film visti, 2 = solo film non visti



    public static void getData(final Context context){
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = Utilities.url+pages;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONObject obj = new JSONObject(response);
                            JSONArray result = obj.getJSONArray("results");
                            Log.d("ciao", obj.getString("page"));
                            int filmCounter = 0;
                            for(int i = 0 ; i < result.length() ; i++){

                                JSONObject data = result.getJSONObject(i);
                                String id = data.getString("id");
                                Cursor cursor = context.getContentResolver().query(FilmProvider.FILMS,null , FilmTableHelper.API_ID + " = " + id ,null,null );
                                if(cursor.getCount()<=0){
                                    filmCounter++;
                                    ContentValues cv = new ContentValues();
                                    cv.put(FilmTableHelper.TITLE, data.getString("title"));
                                    cv.put(FilmTableHelper.DESCRIPTION, data.getString("overview"));
                                    cv.put(FilmTableHelper.IMAGEPATH, data.getString("poster_path"));
                                    cv.put(FilmTableHelper.BACKDROPPATH, data.getString("backdrop_path"));
                                    cv.put(FilmTableHelper.API_ID, id);
                                    cv.put(FilmTableHelper.TO_SEE, "false");
                                    cv.put(FilmTableHelper.SEEN, "false");

                                    context.getContentResolver().insert(FilmProvider.FILMS,cv);
                                }

                            }
                            if(filmCounter < 10){
                                pages++;
                                getData(context);
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
