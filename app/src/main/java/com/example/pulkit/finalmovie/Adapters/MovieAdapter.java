package com.example.pulkit.finalmovie.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.pulkit.finalmovie.Model.Movies;
import com.example.pulkit.finalmovie.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Pulkit on 8/2/2017.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private ArrayList<Movies> mMovieList;
    private Context mContext;

    public MovieAdapter(ArrayList<Movies> mMovieList, Context mContext) {
        this.mMovieList = mMovieList;
        this.mContext = mContext;
    }


    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        LinearLayout moviesLayout;
        TextView movieTitle;
        TextView data;
        TextView movieDescription;
        TextView rating;
        ImageView movieImage;

        public MovieViewHolder(View itemView) {
            super(itemView);
            movieImage = itemView.findViewById(R.id.imageView);
            moviesLayout = itemView.findViewById(R.id.movies_layout);
            movieTitle = itemView.findViewById(R.id.movietitle);
            data = itemView.findViewById(R.id.moviesubtitle);
            movieDescription = itemView.findViewById(R.id.moviedescription);
            rating = itemView.findViewById(R.id.movierating);

        }
    }

    @Override
    public MovieAdapter.MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.linear_list_item_movies, parent
                , false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieAdapter.MovieViewHolder holder, int position) {

        Movies movies = mMovieList.get(position);
        holder.movieTitle.setText(movies.getTitle()+" ("+movies.getReleaseDate().substring(0,4)+")");
        holder.data.setText("#"+(position+1));
        holder.movieDescription.setText(movies.getDescription());
        holder.rating.setText(movies.getVoteAverage().toString());

        Picasso.with(mContext)
                .load(movies.getPoster())
                .placeholder(R.color.colorAccent)
                .into(holder.movieImage);

    }

    @Override
    public int getItemCount() {
        return (mMovieList == null) ? 0 : mMovieList.size();
    }
}
