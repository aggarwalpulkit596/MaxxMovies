package com.example.pulkit.finalmovie.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pulkit.finalmovie.R;

/**
 * Created by Pulkit on 8/2/2017.
 */

public class dataFragment extends Fragment{
    View view;
    ViewPager viewPager;
    TabLayout tabLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.sample, container, false);

        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        viewPager.setAdapter(new sliderAdapter(getChildFragmentManager()));
        tabLayout = (TabLayout) view.findViewById(R.id.sliding_tabs);
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
            }
        });

        return view;
    }
    private class sliderAdapter extends FragmentPagerAdapter {

        final  String tabs[]={"Top Rated", "Popular","Upcoming Movies","Now Playing","Trailers"};
        public sliderAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0: return new PopularFragment();
                case 1: return new topRatedFragment();
                case 2: return new upcomingFragment();
                case 3: return new nowPlayingFragment();
                case 4: return new contentFragment();
            }

            return new contentFragment();
        }

        @Override
        public int getCount() {
            return 5;
        }
        @Override
        public CharSequence getPageTitle(int position) {
            return tabs[position];
        }
    }
}

