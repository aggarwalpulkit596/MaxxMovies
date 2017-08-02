package com.example.pulkit.finalmovie.TvFragments;

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

import com.example.pulkit.finalmovie.Fragments.PopularFragment;
import com.example.pulkit.finalmovie.Fragments.contentFragment;
import com.example.pulkit.finalmovie.Fragments.dataFragment;
import com.example.pulkit.finalmovie.Fragments.topRatedFragment;
import com.example.pulkit.finalmovie.R;

/**
 * Created by Pulkit on 8/2/2017.
 */

public class dataFragments2 extends Fragment{
    View view;
    ViewPager viewPager;
    TabLayout tabLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.sample, container, false);

        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        viewPager.setAdapter(new dataFragments2.sliderAdapter(getChildFragmentManager()));
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

        final  String tabs[]={"Top Rated", "Popular","On The Air","Trailers"};
        public sliderAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0: return new TvPopularFragment();
                case 1: return new TvTopRatedFragment();
                case 2: return new onTheAirFragment();
                case 3: return new contentFragment();
            }

            return new contentFragment();
        }

        @Override
        public int getCount() {
            return 4;
        }
        @Override
        public CharSequence getPageTitle(int position) {
            return tabs[position];
        }
    }
}
