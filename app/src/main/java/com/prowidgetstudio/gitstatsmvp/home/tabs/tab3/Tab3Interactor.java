package com.prowidgetstudio.gitstatsmvp.home.tabs.tab3;

import com.github.mikephil.charting.data.Entry;
import com.prowidgetstudio.gitstatsmvp.repository.database.Commits;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dzano on 4.12.2018.
 */

public interface Tab3Interactor {

    void timeInMillis(int dan, long lastUpdate, Tab3Interactor.OnStartTimeCalculated onStartTimeCalculated);

    interface OnStartTimeCalculated{
        void onStartTime(long start, long end, int brojDana);
    }

    void calculateData(List<Commits> commitsList, long start, int brojDana, OnDataCalculated onDataCalculated);

    interface OnDataCalculated{
        void onCalculated(ArrayList<Entry> valLine, ArrayList<Entry> valCircle, int max);
    }
}
