package com.prowidgetstudio.gitstatsmvp.customViews;


import com.prowidgetstudio.gitstatsmvp.home.fragments.tab1.Tab1Fragment;
import com.prowidgetstudio.gitstatsmvp.home.fragments.tab2.Tab2Fragment;
import com.prowidgetstudio.gitstatsmvp.home.fragments.tab3.Tab3Fragment;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

/**
 * Created by Dzano on 17.11.2018.
 */

public class PagerAdapter extends FragmentStatePagerAdapter {

    private int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {

            case 0:
                return new Tab1Fragment();

            case 1:
                return new Tab2Fragment();

            case 2:
                return new Tab3Fragment();

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
