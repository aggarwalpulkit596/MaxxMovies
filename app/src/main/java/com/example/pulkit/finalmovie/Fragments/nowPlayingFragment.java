package com.example.pulkit.finalmovie.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.pulkit.finalmovie.Adapters.MovieAdapter;
import com.example.pulkit.finalmovie.ConstantKey;
import com.example.pulkit.finalmovie.Model.MovieResponse;
import com.example.pulkit.finalmovie.Model.Movies;
import com.example.pulkit.finalmovie.MovieDetailActivity;
import com.example.pulkit.finalmovie.R;
import com.example.pulkit.finalmovie.Rest.ApiClients;
import com.example.pulkit.finalmovie.Rest.ApiInterface;
import com.example.pulkit.finalmovie.TvFragments.RecyclerItemClickListener;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Pulkit on 8/3/2017.
 */

public class nowPlayingFragment extends Fragment {
    RecyclerView mRecyclerView;
    ArrayList<Movies> mMovies;
    MovieAdapter mAdapter;
    LinearLayoutManager layoutManager;
    boolean loadingMore = false;
    static int i = 2;
    private int lastVisibleItemId;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.content, container, false);
        mRecyclerView = rootView.findViewById(R.id.videoRecyclerView);
        layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayout.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        fetchdata(1, 0);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                lastVisibleItemId = layoutManager.findLastVisibleItemPosition();

                if (lastVisibleItemId == mMovies.size() - 1 && !loadingMore)
                    loadMoreData(i++);
            }
        });
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), mRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(getContext(), MovieDetailActivity.class);
                        intent.putExtra(MovieDetailActivity.EXTRA_MOVIE, mMovies.get(position));
                        intent.putExtra("istv", false);
                        startActivity(intent);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );


        return rootView;
    }

    private void loadMoreData(int i) {
        loadingMore = true;
        fetchdata(i, lastVisibleItemId);
    }

    private void fetchdata(int page, final int last) {
        ApiInterface apiService = ApiClients.getClient().create(ApiInterface.class);
        Call<MovieResponse> call = apiService.getNowPlayingMovies(ConstantKey.MOVIEDB_API, page);
        call.enqueue(new Callback<MovieResponse>() {

            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                int statusCode = response.code();
                Log.i("ResponseCode", "onResponse: " + statusCode);
                if (response.isSuccessful()) {
                    ArrayList<Movies> nMovies;
                    nMovies = response.body().getResults();
                    if (loadingMore) {
                        mMovies.addAll(nMovies);
                        mRecyclerView.scrollToPosition(last);
                    } else
                        mMovies = nMovies;
                    mRecyclerView.setAdapter(mAdapter = new MovieAdapter(mMovies, getContext()));
                    mAdapter.notifyDataSetChanged();
                    loadingMore = false;
                }

            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {

            }
        });
    }

}

