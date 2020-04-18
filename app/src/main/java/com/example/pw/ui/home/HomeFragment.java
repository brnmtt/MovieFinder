package com.example.pw.ui.home;

import android.app.DownloadManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pw.Film;
import com.example.pw.R;
import com.example.pw.activity.MainActivity;
import com.example.pw.ui.RecyclerViewAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private RecyclerView list;

    public HomeFragment() {
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        if(MainActivity.films.isEmpty()){
            getData();
        }

        list = root.findViewById(R.id.list);
        list.setLayoutManager(new GridLayoutManager(getActivity(),2));
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(MainActivity.films, getContext());
        list.setAdapter(adapter);

        return root;
    }

    public void getData(){
        RequestQueue queue = Volley.newRequestQueue(getContext());
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
                                film.setTitle(data.getString("title"));
                                film.setDescription(data.getString("overview"));
                                film.setImagePath(data.getString("poster_path"));
                                film.setBackDropPath(data.getString("backdrop_path"));
                                MainActivity.films.add(film);
                                Log.d("FILM", film.toString());
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        list.getAdapter().notifyDataSetChanged();
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
