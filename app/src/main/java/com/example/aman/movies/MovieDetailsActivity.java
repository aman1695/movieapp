package com.example.aman.movies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class MovieDetailsActivity extends AppCompatActivity {
    private ImageView image;
    private TextView title,date,rating,overview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        image = (ImageView) findViewById(R.id.poster);
        title = (TextView) findViewById(R.id.textView2);
        date = (TextView) findViewById(R.id.textView3);
        rating = (TextView) findViewById(R.id.textView4);
        overview = (TextView) findViewById(R.id.textView5);
        MovieDetails Details = (MovieDetails) getIntent().getExtras().getSerializable("MOVIE_DETAILS");
        if(Details != null){
            Glide.with(this).load("https://image.tmdb.org/t/p/w500/"+ Details.getPoster_path()).into(image);
            title.setText(Details.getOriginal_title());
            date.setText(Details.getRelease_date());
            rating.setText(Double.toString(Details.getVote_average()));
            overview.setText(Details.getOverview());
        }
    }
}
