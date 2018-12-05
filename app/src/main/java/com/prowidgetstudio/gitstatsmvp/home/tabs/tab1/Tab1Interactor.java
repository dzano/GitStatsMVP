package com.prowidgetstudio.gitstatsmvp.home.tabs.tab1;

import com.github.mikephil.charting.data.Entry;
import com.prowidgetstudio.gitstatsmvp.repository.database.Commits;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dzano on 4.12.2018.
 */

public interface Tab1Interactor {


    void timeInMillis(int dan, long lastUpdate, OnStartTimeCalculated onStartTimeCalculated);

    interface OnStartTimeCalculated{
        void onStartTime(long start, boolean today);
    }

    void calculateData(List<Commits> commitsList, long start, OnDataCalculated onDataCalculated);

    interface OnDataCalculated{
        void onCalculated(ArrayList<Entry> valLine, ArrayList<Entry> valCircle, int max);
    }
}
