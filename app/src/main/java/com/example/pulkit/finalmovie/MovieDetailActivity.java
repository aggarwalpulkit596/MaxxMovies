package com.example.pulkit.finalmovie;

import android.app.Service;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pulkit.finalmovie.Adapters.TrailersAdapter;
import com.example.pulkit.finalmovie.Model.Movies;
import com.example.pulkit.finalmovie.Model.TrailerResponse;
import com.example.pulkit.finalmovie.Model.Trailers;
import com.example.pulkit.finalmovie.Rest.ApiClients;
import com.example.pulkit.finalmovie.Rest.ApiInterface;
import com.github.ivbaranov.mfb.MaterialFavoriteButton;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MovieDetailActivity extends AppCompatActivity {
    public static final String EXTRA_MOVIE = "movie";
    private Movies mMovie;
    ImageView backdrop;
    ImageView poster;
    TextView title;
    TextView description;
    RecyclerView mRecyclerView;
    TrailersAdapter adapter;
    List<Trailers> mTrailers;
    MaterialFavoriteButton materialFavoriteButton;
    int movie_id;
    String titlename;
    String date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        if (getIntent().hasExtra(EXTRA_MOVIE)) {
            mMovie = getIntent().getParcelableExtra(EXTRA_MOVIE);
        } else {
            throw new IllegalArgumentException("Detail activity must receive a movie parcelable");
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        titlename = (mMovie.getTitle() == null) ? mMovie.getName() : mMovie.getTitle();
        date = (mMovie.getReleaseDate() == null) ? mMovie.getFirstairdate() : mMovie.getReleaseDate();

        toolbarLayout.setTitle(titlename);
        Log.i("TitleorName",titlename+" and" + date);
        backdrop = (ImageView) findViewById(R.id.backdrop);
        title = (TextView) findViewById(R.id.movie_title);
        description = (TextView) findViewById(R.id.movie_description);
        poster = (ImageView) findViewById(R.id.movie_poster);
        materialFavoriteButton = (MaterialFavoriteButton) findViewById(R.id.favourite_button);


        movie_id = mMovie.getId();
        title.setText(titlename);
        description.setText(mMovie.getDescription());
        Picasso.with(this)
                .load(mMovie.getPoster())
                .into(poster);
        Picasso.with(this)
                .load(mMovie.getBackdrop())
                .into(backdrop);
//        fetchtrailers();
    }

    private void fetchtrailers() {
        adapter = new TrailersAdapter(this, mTrailers);

        mRecyclerView = (RecyclerView) findViewById(R.id.trailerRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        ApiInterface apiService = ApiClients.getClient().create(ApiInterface.class);
        Call<TrailerResponse> call = apiService.getMovieTrailer(movie_id, ConstantKey.MOVIEDB_API);
        call.enqueue(new Callback<TrailerResponse>() {
            @Override
            public void onResponse(Call<TrailerResponse> call, Response<TrailerResponse> response) {
                List<Trailers> trailer = response.body().getResults();
                mRecyclerView.setAdapter(new TrailersAdapter(getApplicationContext(), trailer));
            }

            @Override
            public void onFailure(Call<TrailerResponse> call, Throwable t) {
                Log.d("Error", t.getMessage());

            }
        });
    }
}
