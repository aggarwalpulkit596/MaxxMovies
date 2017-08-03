package com.example.pulkit.finalmovie;

import android.app.Service;
import android.content.Intent;
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

import com.example.pulkit.finalmovie.Adapters.CreditAdapter;
import com.example.pulkit.finalmovie.Adapters.TrailersAdapter;
import com.example.pulkit.finalmovie.Model.Credit;
import com.example.pulkit.finalmovie.Model.CreditResponse;
import com.example.pulkit.finalmovie.Model.Movies;
import com.example.pulkit.finalmovie.Model.TrailerResponse;
import com.example.pulkit.finalmovie.Model.Trailers;
import com.example.pulkit.finalmovie.Rest.ApiClients;
import com.example.pulkit.finalmovie.Rest.ApiInterface;
import com.github.ivbaranov.mfb.MaterialFavoriteButton;
import com.google.android.youtube.player.YouTubeStandalonePlayer;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
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
    RecyclerView mRecyclerView, nRecyclerView;
    CreditAdapter cadapter;
    List<Trailers> trailer;
    List<Credit> credit;


    MaterialFavoriteButton materialFavoriteButton;
    int movie_id;
    String titlename;
    String date;
    boolean istv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        if (getIntent().hasExtra(EXTRA_MOVIE)) {
            mMovie = (Movies) getIntent().getSerializableExtra(EXTRA_MOVIE);
            istv = getIntent().getBooleanExtra("istv",false);

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
        Log.i("TitleorName", titlename + " and" + date);
        backdrop = (ImageView) findViewById(R.id.backdrop);
        title = (TextView) findViewById(R.id.movie_title);
        description = (TextView) findViewById(R.id.movie_description);
        poster = (ImageView) findViewById(R.id.movie_poster);
        materialFavoriteButton = (MaterialFavoriteButton) findViewById(R.id.favourite_button);


        movie_id = mMovie.getId();
        title.setText(titlename+date.substring(0,4));
        description.setText(mMovie.getDescription());
        Picasso.with(this)
                .load(mMovie.getPoster())
                .into(poster);
        Picasso.with(this)
                .load(mMovie.getBackdrop())
                .into(backdrop);
        fetchtrailers();
        fetchcredits();
    }

    private void fetchcredits() {

        mRecyclerView = (RecyclerView) findViewById(R.id.creditRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        ApiInterface apiService = ApiClients.getClient().create(ApiInterface.class);
        Call<CreditResponse> call;
        if(istv==true) {
            call = apiService.getSeriesCredits(movie_id, ConstantKey.MOVIEDB_API);
        }
        else{
            call = apiService.getMovieCredits(movie_id, ConstantKey.MOVIEDB_API);
        }
        call.enqueue(new Callback<CreditResponse>() {
            @Override
            public void onResponse(Call<CreditResponse> call, Response<CreditResponse> response) {
                Log.d("response code", response.code() + "");
                if (response.isSuccessful()) {
                    credit = response.body().getResults();
                    mRecyclerView.setAdapter(new CreditAdapter(credit, MovieDetailActivity.this));
                }
            }

            @Override
            public void onFailure(Call<CreditResponse> call, Throwable t) {
                Log.d("Error", t.getMessage());


            }
        });
    }

    private void fetchtrailers() {
        nRecyclerView = (RecyclerView) findViewById(R.id.trailerRecyclerView);
        nRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));
        nRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        ApiInterface apiService = ApiClients.getClient().create(ApiInterface.class);
        Call<TrailerResponse> call;
        if(istv==true) {
            call = apiService.getSeriesTrailer(movie_id, ConstantKey.MOVIEDB_API);
        }
        else{
            call = apiService.getMovieTrailer(movie_id, ConstantKey.MOVIEDB_API);
        }
        call.enqueue(new Callback<TrailerResponse>() {
            @Override
            public void onResponse(Call<TrailerResponse> call, Response<TrailerResponse> response) {
                trailer = response.body().getResults();
                nRecyclerView.setAdapter(new TrailersAdapter(MovieDetailActivity.this, trailer, new TrailersAdapter.YoutubePlayClickListerner() {
                    @Override
                    public void onPlayClick(String key) {
                        Intent intent = YouTubeStandalonePlayer.createVideoIntent(MovieDetailActivity.this, ConstantKey.YOUTUBE_API, key);
                        startActivity(intent);
                    }
                }));
                nRecyclerView.scrollToPosition(0);

            }

            @Override
            public void onFailure(Call<TrailerResponse> call, Throwable t) {
                Log.d("Error", t.getMessage());

            }
        });
    }
}
