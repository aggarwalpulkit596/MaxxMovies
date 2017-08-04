package com.example.pulkit.finalmovie.Adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pulkit.finalmovie.ConstantKey;
import com.example.pulkit.finalmovie.Model.MovieResponse;
import com.example.pulkit.finalmovie.Model.Movies;
import com.example.pulkit.finalmovie.R;
import com.example.pulkit.finalmovie.Rest.ApiClients;
import com.example.pulkit.finalmovie.Rest.ApiInterface;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Pulkit on 8/3/2017.
 */

public class SearchAdapter extends ArrayAdapter<Movies>{

    private ArrayList<Movies> mMovieList;

    public SearchAdapter(@NonNull Context context, ArrayList<Movies> mMovieList) {
        super(context, R.layout.movie_row, mMovieList);
        this.mMovieList = mMovieList;
    }

    @Override
    public int getCount() {
        Log.i("TAG", "getCount:"+mMovieList.size());
        return mMovieList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.i("TAG", "getView: Called");
        Movies movies = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.movie_row, parent, false);
        }
        TextView txtCustomer = convertView.findViewById(R.id.tvCustomer);
        ImageView ivCustomerImage = convertView.findViewById(R.id.ivCustomerImage);
        txtCustomer.setText(movies.getTitle()+" ("+movies.getReleaseDate().substring(0,4)+")");        Picasso.with(getContext())
                .load(movies.getPoster())
                .placeholder(R.color.colorAccent)
                .into(ivCustomerImage);
        // Now assign alternate color for rows
        if (position % 2 == 0)
            convertView.setBackgroundColor(getContext().getColor(R.color.odd));
        else
            convertView.setBackgroundColor(getContext().getColor(R.color.even));

        return convertView;
    }


}
