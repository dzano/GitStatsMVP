package com.prowidgetstudio.gitstatsmvp.home.fragments.tab2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.prowidgetstudio.gitstatsmvp.R;
import com.prowidgetstudio.gitstatsmvp.charts.WeekChart;
import com.prowidgetstudio.gitstatsmvp.customViews.OnSwipeTouchListener;
import com.prowidgetstudio.gitstatsmvp.databinding.FragmentTab2Binding;
import com.prowidgetstudio.gitstatsmvp.home.fragments.databinding.FragmentsData;
import com.prowidgetstudio.gitstatsmvp.repository.RepositoryImpl;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;


public class Tab2Fragment extends Fragment implements Tab2View {

    private Context context;
    private LineChart chart;
    private ProgressBar progressBar;
    private FragmentsData fragmentData;

    private int weekToShow = 0; // danas

    private Tab2PresenterImpl presenter;
    

    public static Tab2Fragment newInstance(String param1, String param2) {
        Tab2Fragment fragment = new Tab2Fragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        RepositoryImpl repository = new RepositoryImpl(prefs, context);
        presenter = new Tab2PresenterImpl(this, new Tab2InteractorImpl(), repository);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FragmentTab2Binding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_tab2, container, false);
        fragmentData = new FragmentsData();
        binding.setBindingData(fragmentData);

        View rootView = binding.getRoot();

        chart = rootView.findViewById(R.id.chart);
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

    @Override
    public void setTotal(int total, int progress) {
        fragmentData.setCountWeek(Integer.toString(progress));
        progressBar.setMax(total);
        progressBar.setProgress(progress);
    }

    @Override
    public void showTime(long start){
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat formatter = new SimpleDateFormat("MMMM d");
        String datum = formatter.format(new Date(start));
        datum = datum.substring(0, 1).toUpperCase() + datum.substring(1);
        fragmentData.setDateWeek(datum);
    }
}

