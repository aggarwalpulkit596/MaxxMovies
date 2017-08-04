package com.example.pulkit.finalmovie;

import android.app.Service;
import android.content.Intent;
import android.graphics.Movie;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pulkit.finalmovie.Adapters.CreditAdapter;
import com.example.pulkit.finalmovie.Adapters.MovieAdapter;
import com.example.pulkit.finalmovie.Adapters.TrailersAdapter;
import com.example.pulkit.finalmovie.Database.FavoriteOpenHelper;
import com.example.pulkit.finalmovie.Model.Credit;
import com.example.pulkit.finalmovie.Model.CreditResponse;
import com.example.pulkit.finalmovie.Model.MovieResponse;
import com.example.pulkit.finalmovie.Model.Movies;
import com.example.pulkit.finalmovie.Model.TrailerResponse;
import com.example.pulkit.finalmovie.Model.Trailers;
import com.example.pulkit.finalmovie.Rest.ApiClients;
import com.example.pulkit.finalmovie.Rest.ApiInterface;
import com.example.pulkit.finalmovie.TvFragments.RecyclerItemClickListener;
import com.github.clans.fab.FloatingActionMenu;
import com.github.ivbaranov.mfb.MaterialFavoriteButton;
import com.google.android.youtube.player.YouTubeStandalonePlayer;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    TextView genrelist;
    TextView rating;
    TextView releasedate;
    RecyclerView mRecyclerView, nRecyclerView, pRecyclerview;
    List<Trailers> trailer;
    List<Credit> credit;
    ArrayList<Movies> movie;
    private Movies favorite;
    int movie_id;
    String titlename;
    String date;
    boolean istv;
    private FloatingActionMenu menuRed;
    private com.github.clans.fab.FloatingActionButton fab2;
    private List<FloatingActionMenu> menus = new ArrayList<>();
    FavoriteOpenHelper favoriteOpenHelper = new FavoriteOpenHelper(MovieDetailActivity.this);
    List<Integer> genreIds = new ArrayList<>();
    public HashMap<Integer, String> hmap = new HashMap<>();
    String total;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        hmap.put(28, "Action");
        hmap.put(12, "Adventure");
        hmap.put(16, "Animation");
        hmap.put(35, "Comedy");
        hmap.put(80, "crime");
        hmap.put(99, "Documentary");
        hmap.put(18, "Drama");
        hmap.put(10751, "Family");
        hmap.put(14, "Fantasy");
        hmap.put(36, "History");
        hmap.put(27, "Horror");
        hmap.put(10402, "Music");
        hmap.put(9648, "Mystery");
        hmap.put(10749, "Romance");
        hmap.put(878, "Science Fiction");
        hmap.put(10770, "Tv Movie");
        hmap.put(53, "Thriller");
        hmap.put(10752, "War");
        hmap.put(37, "Western");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        if (getIntent().hasExtra(EXTRA_MOVIE)) {
            mMovie = (Movies) getIntent().getSerializableExtra(EXTRA_MOVIE);
            istv = getIntent().getBooleanExtra("istv", false);

        } else {
            throw new IllegalArgumentException("Detail activity must receive a movie parcelable");
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        menuRed = (FloatingActionMenu) findViewById(R.id.fab);
        fab2 = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.fab2);
        menuRed.setClosedOnTouchOutside(true);
        menus.add(menuRed);
        menuRed.setOnMenuButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (menuRed.isOpened()) {
                    Toast.makeText(MovieDetailActivity.this, menuRed.getMenuButtonLabelText(), Toast.LENGTH_SHORT).show();
                }

                menuRed.toggle(true);
            }
        });
        fab2.setOnClickListener(clickListener);
        genreIds.addAll(mMovie.getGenreIds());
        titlename = (mMovie.getTitle() == null) ? mMovie.getName() : mMovie.getTitle();
        date = (mMovie.getReleaseDate() == null) ? mMovie.getFirstairdate() : mMovie.getReleaseDate();

        toolbarLayout.setTitle(titlename + " (" + date.substring(0, 4) + ")");
        Log.i("TitleorName", titlename + " and" + date);
        backdrop = (ImageView) findViewById(R.id.backdrop);
        title = (TextView) findViewById(R.id.movie_title);
        description = (TextView) findViewById(R.id.movie_description);
        rating = (TextView) findViewById(R.id.movierating1);
        poster = (ImageView) findViewById(R.id.movie_poster);
        genrelist = (TextView) findViewById(R.id.movie_genre);
        releasedate = (TextView) findViewById(R.id.moviesreleasedate);
        for (Integer m : genreIds) {
            hmap.get(m);
            total += hmap.get(m) + " | ";
        }
        releasedate.setText("Release Date :" + mMovie.getReleaseDate());
        rating.setText(mMovie.getVoteAverage().toString() + "");
        genrelist.setText(total.substring(4));
        movie_id = mMovie.getId();
        title.setText(titlename);
        description.setText(mMovie.getDescription());
        Picasso.with(this)
                .load(mMovie.getPoster())
                .into(poster);
        Picasso.with(this)
                .load(mMovie.getBackdrop())
                .into(backdrop);
        fetchtrailers();
        fetchcredits();
//        if(istv=)
        fetchreccomendations();
    }

    private void fetchreccomendations() {
        pRecyclerview = (RecyclerView) findViewById(R.id.recommendationRecyclerView);
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(pRecyclerview);
        pRecyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        ApiInterface apiService = ApiClients.getClient().create(ApiInterface.class);
        Call<MovieResponse> call;
        if (istv == true)
            call = apiService.getSeriesRecommendations(movie_id, ConstantKey.MOVIEDB_API);
        else
            call = apiService.getMovieRecommendations(movie_id, ConstantKey.MOVIEDB_API);

        call.enqueue(new Callback<MovieResponse>() {

            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                int statusCode = response.code();
                movie = response.body().getResults();
                pRecyclerview.setAdapter(new MovieAdapter(movie, MovieDetailActivity.this));

            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {

            }
        });
        pRecyclerview.addOnItemTouchListener(
                new RecyclerItemClickListener(MovieDetailActivity.this, mRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(MovieDetailActivity.this,MovieDetailActivity.class);
                        intent.putExtra(MovieDetailActivity.EXTRA_MOVIE, movie.get(position));
                        intent.putExtra("istv",true);
                        startActivity(intent);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );
    }

    private void fetchcredits() {

        mRecyclerView = (RecyclerView) findViewById(R.id.creditRecyclerView);
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(mRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        ApiInterface apiService = ApiClients.getClient().create(ApiInterface.class);
        Call<CreditResponse> call;
        if (istv == true) {
            call = apiService.getSeriesCredits(movie_id, ConstantKey.MOVIEDB_API);
        } else {
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
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(nRecyclerView);
        nRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        nRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));
        ApiInterface apiService = ApiClients.getClient().create(ApiInterface.class);
        Call<TrailerResponse> call;
        if (istv == true) {
            call = apiService.getSeriesTrailer(movie_id, ConstantKey.MOVIEDB_API);
        } else {
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

    private void saveFavorite() {
        favorite = new Movies();
        favorite.setId(movie_id);
        favorite.setTitle(titlename);
        favorite.setPoster(mMovie.getPoster());

        favorite.setVoteAverage(mMovie.getVoteAverage());
        favorite.setDescription(mMovie.getDescription());

        favoriteOpenHelper.addFavorite(favorite);
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.fab2:
                    if (fab2.getLabelText().equals("Add to Favorites")) {
                        fab2.setLabelText("Remove From Favorites");
                        saveFavorite();
                    } else {
                        fab2.setLabelText("Add to Favorites");
//                        favoriteOpenHelper.deleteFavorite();

                    }

                    break;

            }
        }
    };

}
