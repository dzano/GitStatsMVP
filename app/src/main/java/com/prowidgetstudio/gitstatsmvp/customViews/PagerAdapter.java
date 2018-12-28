package com.prowidgetstudio.gitstatsmvp.customViews;


import com.prowidgetstudio.gitstatsmvp.home.fragments.tab1.Tab1;
import com.prowidgetstudio.gitstatsmvp.home.fragments.tab2.Tab2;
import com.prowidgetstudio.gitstatsmvp.home.fragments.tab3.Tab3;

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
                return new Tab1();

            case 1:
                return new Tab2();

            case 2:
                return new Tab3();

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
