package com.prowidgetstudio.gitstatsmvp.home.fragments.tab1;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.prowidgetstudio.gitstatsmvp.R;
import com.prowidgetstudio.gitstatsmvp.charts.DayChart;
import com.prowidgetstudio.gitstatsmvp.customViews.OnSwipeTouchListener;
import com.prowidgetstudio.gitstatsmvp.repository.RepositoryImpl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class Tab1 extends Fragment implements Tab1View{

    private Context context;
    private LineChart chart;
    private TextView dayTotal, count, date;
    private ProgressBar progressBar;

    private int dayToShow = 0; // danas

    private Tab1PresenterImpl presenter;


    public static Tab1 newInstance(String param1, String param2) {
        Tab1 fragment = new Tab1();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        RepositoryImpl repository = new RepositoryImpl(prefs, context);
        presenter = new Tab1PresenterImpl(this, new Tab1InteractorImpl(), repository);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_tab1, container, false);
        chart = rootView.findViewById(R.id.chart);
        count = (TextView) rootView.findViewById(R.id.count);
        date = (TextView) rootView.findViewById(R.id.date);
        dayTotal = (TextView) rootView.findViewById(R.id.todayTotal);
        progressBar = (ProgressBar)rootView.findViewById(R.id.progressBar);

        OnSwipeTouchListener onSwipeTouchListener = new OnSwipeTouchListener(context);

        gestureSettings();
        presenter.timeInMillis(dayToShow);

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.registerEventBus();
    }

    @Override
    public void onStop() {
        presenter.unregisterEventBus();
        super.onStop();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onDetach() {
        context = null;
        super.onDetach();
    }

    @Override
    public void isToday(boolean today){

        if(today) dayTotal.setText(R.string.todayTotal);
        else dayTotal.setText(R.string.dayTotal);
    }


    private void gestureSettings() {

        chart.setOnTouchListener(new OnSwipeTouchListener(context) {
            @Override
            public void onSwipeLeft() {

                if(dayToShow >= 1){

                    dayToShow -= 1;
                    presenter.timeInMillis(dayToShow);
                }
            }

            @Override
            public void onSwipeRight() {

                dayToShow += 1;
                presenter.timeInMillis(dayToShow);
            }
        });
    }

    @Override
    public void showChart(ArrayList<Entry> valLine, ArrayList<Entry> valCircle, int max) {

        chart.clear();
        DayChart dayChart = new DayChart(context, chart);
        dayChart.showData(valLine, valCircle, max);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void setTotal(int total, int progress) {

        count.setText(Integer.toString(progress));
        progressBar.setMax(total);
        progressBar.setProgress(progress);
    }

    @Override
    public void showTime(long start){
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("MMMM d");
        String datum = formatter.format(new Date(start));
        datum = datum.substring(0, 1).toUpperCase() + datum.substring(1);
        date.setText(datum);
    }
}


