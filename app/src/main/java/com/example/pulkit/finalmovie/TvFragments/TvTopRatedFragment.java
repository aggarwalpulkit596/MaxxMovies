package com.example.pulkit.finalmovie.TvFragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pulkit.finalmovie.Adapters.MovieAdapter;
import com.example.pulkit.finalmovie.ConstantKey;
import com.example.pulkit.finalmovie.Model.MovieResponse;
import com.example.pulkit.finalmovie.Model.Movies;
import com.example.pulkit.finalmovie.MovieDetailActivity;
import com.example.pulkit.finalmovie.R;
import com.example.pulkit.finalmovie.Rest.ApiClients;
import com.example.pulkit.finalmovie.Rest.ApiInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Pulkit on 8/2/2017.
 */

public class TvTopRatedFragment extends Fragment {
    RecyclerView mRecyclerView;
    ArrayList<Movies> mMovies;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.content, container, false);
        mRecyclerView = rootView.findViewById(R.id.videoRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), mRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(getContext(),MovieDetailActivity.class);
                        intent.putExtra(MovieDetailActivity.EXTRA_MOVIE, mMovies.get(position));
                        intent.putExtra("istv",true);
                        startActivity(intent);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );
        ApiInterface apiService = ApiClients.getClient().create(ApiInterface.class);
        Call<MovieResponse> call = apiService.getTopRatedSeries(ConstantKey.MOVIEDB_API,1);
        call.enqueue(new Callback<MovieResponse>() {

            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                int statusCode = response.code();
                mMovies = response.body().getResults();
                mRecyclerView.setAdapter(new MovieAdapter(mMovies, getContext()));

            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {

            }
        });
        return rootView;
    }
}