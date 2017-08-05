package com.example.pulkit.finalmovie;


import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
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
import com.example.pulkit.finalmovie.Model.AccountResponse;
import com.example.pulkit.finalmovie.Model.Credit;
import com.example.pulkit.finalmovie.Model.CreditResponse;
import com.example.pulkit.finalmovie.Model.MovieResponse;
import com.example.pulkit.finalmovie.Model.Movies;
import com.example.pulkit.finalmovie.Model.PostResponse;
import com.example.pulkit.finalmovie.Model.TrailerResponse;
import com.example.pulkit.finalmovie.Model.Trailers;
import com.example.pulkit.finalmovie.Rest.ApiClients;
import com.example.pulkit.finalmovie.Rest.ApiInterface;
import com.example.pulkit.finalmovie.TvFragments.RecyclerItemClickListener;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.youtube.player.YouTubeStandalonePlayer;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MovieDetailActivity extends AppCompatActivity {
    public static final String EXTRA_MOVIE = "movie";
    private Movies mMovie;
    ImageView backdrop, poster;
    TextView title, description, genrelist, rating, releasedate;
    RecyclerView mRecyclerView, nRecyclerView, pRecyclerview;
    List<Trailers> trailer;
    List<Credit> credit;
    ArrayList<Movies> movie;
    int movie_id;
    String titlename;
    String date;
    boolean istv;
    private FloatingActionMenu menuRed;
    private com.github.clans.fab.FloatingActionButton fab2;
    private com.github.clans.fab.FloatingActionButton fab3;
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
        backdrop = (ImageView) findViewById(R.id.backdrop);
        title = (TextView) findViewById(R.id.movie_title);
        description = (TextView) findViewById(R.id.movie_description);
        rating = (TextView) findViewById(R.id.movierating1);
        poster = (ImageView) findViewById(R.id.movie_poster);
        genrelist = (TextView) findViewById(R.id.movie_genre);
        releasedate = (TextView) findViewById(R.id.moviesreleasedate);
        menuRed = (FloatingActionMenu) findViewById(R.id.fab);
        fab2 = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.fab2);
        com.github.clans.fab.FloatingActionButton fab1 = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.fab1);
        fab3 = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.fab3);

        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        menuRed.setClosedOnTouchOutside(true);
        menus.add(menuRed);
        menuRed.setOnMenuButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuRed.toggle(true);
            }
        });
        fab1.setOnClickListener(clickListener);
        fab2.setOnClickListener(clickListener);
        fab3.setOnClickListener(clickListener);
        genreIds.addAll(mMovie.getGenreIds());
        titlename = (mMovie.getTitle() == null) ? mMovie.getName() : mMovie.getTitle();
        date = (mMovie.getReleaseDate() == null) ? mMovie.getFirstairdate() : mMovie.getReleaseDate();
        toolbarLayout.setTitle(titlename + " (" + date.substring(0, 4) + ")");
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
        fetchreccomendations();
    }

    private void fetchreccomendations() {
        pRecyclerview = (RecyclerView) findViewById(R.id.recommendationRecyclerView);
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(pRecyclerview);
        pRecyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        ApiInterface apiService = ApiClients.getClient().create(ApiInterface.class);
        Call<MovieResponse> call;
        if (istv)
            call = apiService.getSeriesRecommendations(movie_id, ConstantKey.MOVIEDB_API);

        else
            call = apiService.getMovieRecommendations(movie_id, ConstantKey.MOVIEDB_API);
        call.enqueue(new Callback<MovieResponse>() {

            @Override
            public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
                int statusCode = response.code();
                movie = response.body().getResults();
                pRecyclerview.setAdapter(new MovieAdapter(movie, MovieDetailActivity.this));

            }

            @Override
            public void onFailure(@NonNull Call<MovieResponse> call, @NonNull Throwable t) {

            }
        });
        pRecyclerview.addOnItemTouchListener(
                new RecyclerItemClickListener(MovieDetailActivity.this, mRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(MovieDetailActivity.this, MovieDetailActivity.class);
                        intent.putExtra(MovieDetailActivity.EXTRA_MOVIE, movie.get(position));
                        if (istv)
                            intent.putExtra("istv", true);

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
        if (istv)
            call = apiService.getSeriesCredits(movie_id, ConstantKey.MOVIEDB_API);
        else
            call = apiService.getMovieCredits(movie_id, ConstantKey.MOVIEDB_API);
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
            public void onFailure(@NonNull Call<CreditResponse> call, @NonNull Throwable t) {
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
        if (istv)
            call = apiService.getSeriesTrailer(movie_id, ConstantKey.MOVIEDB_API);
        else
            call = apiService.getMovieTrailer(movie_id, ConstantKey.MOVIEDB_API);
        call.enqueue(new Callback<TrailerResponse>() {
            @Override
            public void onResponse(@NonNull Call<TrailerResponse> call, @NonNull Response<TrailerResponse> response) {
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
            public void onFailure(@NonNull Call<TrailerResponse> call, @NonNull Throwable t) {
                Log.d("Error", t.getMessage());

            }
        });
    }

    private void saveFavorite() {
        ApiInterface apiService = ApiClients.getClient().create(ApiInterface.class);
        Call<PostResponse> call = apiService.markFav(ConstantKey.getID(), ConstantKey.MOVIEDB_API, ConstantKey.getSESSION(), istv ? "tv" : "movie", movie_id, true);
        call.enqueue(new Callback<PostResponse>() {
            @Override
            public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {
                Log.i("NowFetchingFavorite", "run: " + response);
                if (response.isSuccessful()) {

                    Log.i("NowFetchingFavorite", "run: " + response);
                }
            }

            @Override
            public void onFailure(Call<PostResponse> call, Throwable t) {

            }
        });

    }

    private void saveWishlist() {
        ApiInterface apiService = ApiClients.getClient().create(ApiInterface.class);
        Call<PostResponse> call = apiService.markWish(ConstantKey.getID(), ConstantKey.MOVIEDB_API, ConstantKey.getSESSION(), istv ? "tv" : "movie", movie_id, true);
        call.enqueue(new Callback<PostResponse>() {
            @Override
            public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {
                if (response.isSuccessful()) {

                    Log.i("NowFetchingWishlist", "run: " + response);
                }
            }

            @Override
            public void onFailure(Call<PostResponse> call, Throwable t) {

            }
        });
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
                case R.id.fab3:
                    if (fab3.getLabelText().equals("Add to Watchlist")) {
                        fab3.setLabelText("Remove From Wacthlist");
                        saveWishlist();
                    } else {
                        fab3.setLabelText("Add to Watchlist");
//                        favoriteOpenHelper.deleteFavorite();
                    }
                    break;
                case R.id.fab1:
                    menuRed.close(true);
                    Toast.makeText(MovieDetailActivity.this, "Screenshot Saved", Toast.LENGTH_SHORT).show();
                    Runnable r = new Runnable() {
                        @Override
                        public void run() {
                            Bitmap bitmap = takeScreenshot();
                            saveBitmap(bitmap);
                        }
                    };
                    Handler h = new Handler();
                    h.postDelayed(r, 500);
                    break;
            }
        }
    };

    public Bitmap takeScreenshot() {
        View rootView = findViewById(android.R.id.content).getRootView();
        rootView.setDrawingCacheEnabled(true);
        return rootView.getDrawingCache();
    }

    public void saveBitmap(Bitmap bitmap) {
        File imagePath = new File(Environment.getExternalStorageDirectory() + "/screenshot.png");
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(imagePath);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            Log.e("GREC", e.getMessage(), e);
        }
        shareImage(imagePath);
    }

    private void shareImage(File file) {
        Uri uri = Uri.fromFile(file);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("image/*");

        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "");
        intent.putExtra(android.content.Intent.EXTRA_TEXT, "");
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        try {
            startActivity(Intent.createChooser(intent, "Share Screenshot"));
        } catch (ActivityNotFoundException e) {
            Toast.makeText(MovieDetailActivity.this, "No App Available", Toast.LENGTH_SHORT).show();
        }
    }


}
