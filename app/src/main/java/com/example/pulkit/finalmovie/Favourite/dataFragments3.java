package com.example.pulkit.finalmovie.Favourite;

import android.content.Intent;
import android.os.AsyncTask;
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
import com.example.pulkit.finalmovie.Database.FavoriteOpenHelper;
import com.example.pulkit.finalmovie.Model.Movies;
import com.example.pulkit.finalmovie.MovieDetailActivity;
import com.example.pulkit.finalmovie.R;
import com.example.pulkit.finalmovie.TvFragments.RecyclerItemClickListener;
import java.util.ArrayList;


/**
 * Created by Pulkit on 8/4/2017.
 */

public class dataFragments3 extends Fragment {
    RecyclerView mRecyclerView;
    MovieAdapter movieAdapter;
    ArrayList<Movies> mMovies;
    private FavoriteOpenHelper favoriteOpenHelper;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.content, container, false);
        mRecyclerView = rootView.findViewById(R.id.videoRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        movieAdapter = new MovieAdapter(mMovies,getActivity());
        mRecyclerView.setAdapter(movieAdapter);
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
        getAllFavorite();
        return rootView;
    }
    private void getAllFavorite(){
        new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... params){
                mMovies.addAll(favoriteOpenHelper.getAllFavorite());
                return null;
            }
            @Override
            protected void onPostExecute(Void aVoid){
                super.onPostExecute(aVoid);
                movieAdapter.notifyDataSetChanged();
            }
        }.execute();
    }
    }

