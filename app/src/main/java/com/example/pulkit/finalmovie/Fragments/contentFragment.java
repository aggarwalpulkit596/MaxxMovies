package com.example.pulkit.finalmovie.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pulkit.finalmovie.R;
import com.example.pulkit.finalmovie.Adapters.TrailersAdapter;

/**
 * Created by Pulkit on 8/2/2017.
 */

public class contentFragment extends Fragment {

    RecyclerView mRecyclerView;
    TrailersAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.content,container,false);
//        mRecyclerView = rootView.findViewById(R.id.videoRecyclerView);
//        adapter=new TrailersAdapter(getActivity(), mTrailers);
//        mRecyclerView.setAdapter(adapter);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,true));
//        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        return rootView;
    }
}