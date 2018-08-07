package com.example.aman.movies;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{
    private ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listview);
        listView.setOnItemClickListener(this);

        new chechConnectinsStatus().execute("https://api.themoviedb.org/3/movie/popular?api_key=f48d9f099da0267c76993f3b43f799f7&language=en-US&page=1");
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent intent = new Intent(this, MovieDetailsActivity.class);
        intent.putExtra("MOVIE_DETAILS", (MovieDetails)parent.getItemAtPosition(position));
        startActivity(intent);
    }

    class chechConnectinsStatus extends AsyncTask<String, Void, String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            URL url= null;
            try {
                url = new URL(params[0]);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            try {
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                   Log.i("Response", String.valueOf(urlConnection.getResponseCode()));
//                return String.valueOf(urlConnection.getResponseCode());

                InputStream inputstream = urlConnection.getInputStream();
                BufferedReader bufferreader = new BufferedReader(new InputStreamReader(inputstream));
                String s = bufferreader.readLine();
                bufferreader.close();
                return s;

            } catch (IOException e) {
                Log.e("Error: ",e.getMessage(), e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {

            super.onPostExecute(s);

            JSONObject jsonobject = null;
            Log.i("result", s);
            try {
                jsonobject = new JSONObject(s);
                ArrayList<MovieDetails> movielist = new ArrayList<>();


                JSONArray jsonArray = jsonobject.getJSONArray("results");
                for (int i=0; i<jsonArray.length(); i++){
                    JSONObject object = jsonArray.getJSONObject(i);
                    MovieDetails movieDetails = new MovieDetails();
                    movieDetails.setOriginal_title(object.getString("original_title"));
                    movieDetails.setOverview(object.getString("overview"));
                    movieDetails.setRelease_date(object.getString("release_date"));
                    movieDetails.setPoster_path(object.getString("poster_path"));
                    movieDetails.setVote_average(object.getDouble("vote_average"));
                    movielist.add(movieDetails);


                }
                MovieArrayAdapter adapter = new MovieArrayAdapter(MainActivity.this,R.layout.movie_list,movielist);
                listView.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

        }

    }


}

