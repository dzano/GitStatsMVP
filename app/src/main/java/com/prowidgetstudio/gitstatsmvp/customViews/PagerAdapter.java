package com.prowidgetstudio.gitstatsmvp.customViews;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.prowidgetstudio.gitstatsmvp.home.tabs.tab1.Tab1;
import com.prowidgetstudio.gitstatsmvp.home.tabs.tab2.Tab2;
import com.prowidgetstudio.gitstatsmvp.home.tabs.tab3.Tab3;

/**
 * Created by Dzano on 17.11.2018.
 */

public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {

            case 0:
                Tab1 tab1 = new Tab1();
                return tab1;
            case 1:
                Tab2 tab2 = new Tab2();
                return tab2;
            case 2:
                Tab3 tab3 = new Tab3();
                return tab3;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
