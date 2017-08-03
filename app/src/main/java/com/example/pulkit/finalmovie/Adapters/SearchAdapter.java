package com.example.pulkit.finalmovie.Adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;

import com.example.pulkit.finalmovie.Model.Movies;

/**
 * Created by Pulkit on 8/3/2017.
 */

public class SearchAdapter extends ArrayAdapter<Movies>{

    public SearchAdapter(@NonNull Context context, @LayoutRes int resource) {
        super(context, resource);
    }
}
