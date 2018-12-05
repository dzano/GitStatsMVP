package com.prowidgetstudio.gitstatsmvp.home.tabs.tab2;

import com.github.mikephil.charting.data.Entry;
import com.prowidgetstudio.gitstatsmvp.repository.database.Commits;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dzano on 4.12.2018.
 */

public interface Tab2Interactor {

    void timeInMillis(int dan, long lastUpdate, Tab2Interactor.OnStartTimeCalculated onStartTimeCalculated);

    interface OnStartTimeCalculated{
        void onStartTime(long start);
    }

    void calculateData(List<Commits> commitsList, long start, OnDataCalculated onDataCalculated);

    interface OnDataCalculated{
        void onCalculated(ArrayList<Entry> valLine, ArrayList<Entry> valCircle, int max);
    }
}
