package com.example.movieapp;

import android.graphics.Movie;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    String data;
    RecyclerView recyclerView;
    MovieData movieData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.movielist);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        new MyAsyncTask().execute();

    }

    public String getDataFromApi(String url) throws IOException{

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    public class MyAsyncTask extends AsyncTask{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);

            MovieAdapter movieAdapter = new MovieAdapter(movieData.getResults(),MainActivity.this);
            recyclerView.setAdapter(movieAdapter);
        }

        @Override
        protected Object doInBackground(Object[] objects) {

            try {
                data = getDataFromApi("https://api.themoviedb.org/3/movie/top_rated?api_key=6f77b7fcefa8d0142cadbbc1ef9cfad8&language=en-US&page=1");

                movieData = new Gson().fromJson(data, MovieData.class);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }
}
