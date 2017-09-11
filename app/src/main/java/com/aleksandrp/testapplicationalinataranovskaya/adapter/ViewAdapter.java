package com.aleksandrp.testapplicationalinataranovskaya.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.aleksandrp.testapplicationalinataranovskaya.fragment.FavoriteListMovesFragment;
import com.aleksandrp.testapplicationalinataranovskaya.fragment.PopularListMovesFragment;

/**
 * Created by AleksandrP on 11.09.2017.
 */

public class ViewAdapter extends FragmentPagerAdapter {

    private int numberOfTabs;

    public ViewAdapter(FragmentManager fm) {
        super(fm);
        this.numberOfTabs = 2;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new PopularListMovesFragment();
            case 1:
                return new FavoriteListMovesFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }
}
