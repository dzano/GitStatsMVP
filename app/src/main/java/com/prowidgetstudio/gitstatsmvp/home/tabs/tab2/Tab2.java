package com.prowidgetstudio.gitstatsmvp.home.tabs.tab2;

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
import com.prowidgetstudio.gitstatsmvp.charts.WeekChart;
import com.prowidgetstudio.gitstatsmvp.customViews.OnSwipeTouchListener;
import com.prowidgetstudio.gitstatsmvp.repository.RepositoryImpl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class Tab2 extends Fragment implements Tab2View {

    private Context context;
    private LineChart chart;
    private TextView count, date;
    private ProgressBar progressBar;

    private int weekToShow = 0; // danas

    private Tab2PresenterImpl presenter;
    

    public static Tab2 newInstance(String param1, String param2) {
        Tab2 fragment = new Tab2();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = getContext();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        RepositoryImpl repository = new RepositoryImpl(prefs, context);
        presenter = new Tab2PresenterImpl(this, new Tab2InteractorImpl(), repository);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_tab2, container, false);
        chart = rootView.findViewById(R.id.chart);
        count = (TextView) rootView.findViewById(R.id.count);
        date = (TextView) rootView.findViewById(R.id.date);
        progressBar = (ProgressBar)rootView.findViewById(R.id.progressBar);

        gestureSettings();
        presenter.timeInMillis(weekToShow);

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


    private void gestureSettings() {

        chart.setOnTouchListener(new OnSwipeTouchListener(context) {
            @Override
            public void onSwipeLeft() {

                if(weekToShow >= 1){

                    weekToShow -= 1;
                    presenter.timeInMillis(weekToShow);
                }
            }

            @Override
            public void onSwipeRight() {

                weekToShow += 1;
                presenter.timeInMillis(weekToShow);
            }
        });
    }

    @Override
    public void showChart(ArrayList<Entry> valLine, ArrayList<Entry> valCircle, int max) {

        chart.clear();
        WeekChart weekChart = new WeekChart(context, chart);
        weekChart.showData(valLine, valCircle, max);
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

