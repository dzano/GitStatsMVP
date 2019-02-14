package com.prowidgetstudio.gitstatsmvp.home.fragments.tab3;

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
import com.prowidgetstudio.gitstatsmvp.charts.MonthChart;
import com.prowidgetstudio.gitstatsmvp.customViews.OnSwipeTouchListener;
import com.prowidgetstudio.gitstatsmvp.databinding.FragmentTab3Binding;
import com.prowidgetstudio.gitstatsmvp.home.fragments.databinding.FragmentsData;
import com.prowidgetstudio.gitstatsmvp.repository.RepositoryImpl;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

public class Tab3Fragment extends Fragment implements Tab3View {

    private Context context;
    private LineChart chart;
    private ProgressBar progressBar;
    private FragmentsData fragmentData;

    private int monthToShow = 0; // tekuci mjesec

    private Tab3PresenterImpl presenter;

    public static Tab3Fragment newInstance(String param1, String param2) {
        Tab3Fragment fragment = new Tab3Fragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        RepositoryImpl repository = new RepositoryImpl(prefs, context);
        presenter = new Tab3PresenterImpl(this, new Tab3InteractorImpl(), repository);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FragmentTab3Binding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_tab3, container, false);
        fragmentData = new FragmentsData();
        binding.setBindingData(fragmentData);

        View rootView = binding.getRoot();

        chart = rootView.findViewById(R.id.chart);
        progressBar = (ProgressBar)rootView.findViewById(R.id.progressBar);

        gestureSettings();
        presenter.timeInMillis(monthToShow);

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

                if(monthToShow >= 1){

                    monthToShow -= 1;
                    presenter.timeInMillis(monthToShow);
                }
            }

            @Override
            public void onSwipeRight() {

                monthToShow += 1;
                presenter.timeInMillis(monthToShow);
            }
        });
    }

    @Override
    public void showChart(ArrayList<Entry> valLine, ArrayList<Entry> valCircle, int max) {

        chart.clear();
        MonthChart monthChart = new MonthChart(context, chart);
        monthChart.showData(valLine, valCircle, max);
    }

    @Override
    public void setTotal(int total, int progress) {
        fragmentData.setCountMonth(Integer.toString(progress));
        progressBar.setMax(total);
        progressBar.setProgress(progress);
    }

    @SuppressLint("SimpleDateFormat")
    @Override
    public void showTime(long start, long end) {

        SimpleDateFormat formatter = new SimpleDateFormat("MMMM yyyy");
        String datum = formatter.format(new Date(start));
        datum = datum.substring(0, 1).toUpperCase() + datum.substring(1);
        fragmentData.setDateMonth(datum);

        SimpleDateFormat formatter1 = new SimpleDateFormat("MMMM d");
        datum = formatter1.format(new Date(start));
        datum = datum.substring(0, 1).toUpperCase() + datum.substring(1);
        fragmentData.setFirstDateMonth(datum);

        datum = formatter1.format(new Date(end));
        datum = datum.substring(0, 1).toUpperCase() + datum.substring(1);
        fragmentData.setLastDateMonth(datum);
    }
}


