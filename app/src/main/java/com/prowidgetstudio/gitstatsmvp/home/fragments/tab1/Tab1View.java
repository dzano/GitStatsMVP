package com.prowidgetstudio.gitstatsmvp.home.fragments.tab1;

import com.github.mikephil.charting.data.Entry;
import java.util.ArrayList;

/**
 * Created by Dzano on 4.12.2018.
 */

public interface Tab1View {

    void showChart(ArrayList<Entry> valLine, ArrayList<Entry> valCircle, int max);

    void setTotal (int total, int progress);

    void isToday(boolean today);

    void showTime(long start);


}
