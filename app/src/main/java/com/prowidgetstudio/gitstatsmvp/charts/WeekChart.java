package com.prowidgetstudio.gitstatsmvp.charts;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.prowidgetstudio.gitstatsmvp.R;
import com.prowidgetstudio.gitstatsmvp.customViews.CustomMarkerViewWeek;

import java.text.DecimalFormat;
import java.util.ArrayList;

import androidx.core.content.ContextCompat;

/**
 * Created by Dzano on 4.12.2018.
 */

public class WeekChart {

    private Context context;
    private LineChart chart;

    public WeekChart(Context context, LineChart chart) {
        this.context = context;
        this.chart = chart;
    }

    public void showData (ArrayList<Entry> valLine,  ArrayList<Entry> valCircle, int max) {

        // listeners
        //chart.setOnChartValueSelectedListener(this);

        // postavljam markere
        CustomMarkerViewWeek markerView = new CustomMarkerViewWeek(context, R.layout.custom_marker_view);
        chart.setDrawMarkers(true);
        markerView.setChartView(chart);
        chart.setMarker(markerView);
        chart.setTouchEnabled(true);

        chart.getDescription().setEnabled(false);
        chart.setViewPortOffsets(15f, -1f, 15f, 0f);
        chart.setDrawGridBackground(false);
        chart.setDragEnabled(false);
        chart.setScaleEnabled(false);
        chart.setPinchZoom(false);
        chart.getAxisRight().setEnabled(false);

        XAxis xAxis;
        {   // vertikalne
            xAxis = chart.getXAxis();
            xAxis.setDrawLabels(false);
            xAxis.setDrawGridLines(false);
        }

        YAxis yAxis;
        {   // horizontalne
            yAxis = chart.getAxisLeft();
            yAxis.disableAxisLineDashedLine();
            yAxis.setGridColor(Color.parseColor("#E1E1E1"));
            yAxis.setZeroLineColor(Color.parseColor("#E1E1E1"));
            yAxis.removeAllLimitLines();
            yAxis.setAxisMaximum(max);
            yAxis.setAxisMinimum(-(float)max/10);
            yAxis.setDrawLabels(false);
            yAxis.setDrawAxisLine(false);
            yAxis.setLabelCount(4, true);
            yAxis.setDrawZeroLine(true);
        }

        setData(valLine, valCircle);

        chart.animateY(500);

        //ukloni legend i nazive
        chart.getAxisLeft().setDrawLabels(false);
        chart.getAxisRight().setDrawLabels(false);
        chart.getXAxis().setDrawLabels(false);
        chart.getLegend().setEnabled(false);
        // ukloni border
        // chart.setDrawBorders(false);
        // Legend samo nakon setData
        //Legend l = chart.getLegend();
    }

    private void setData( ArrayList<Entry> valLine,  ArrayList<Entry> valCircle) {

        LineDataSet set1, set2;

        if (chart.getData() != null && chart.getData().getDataSetCount() > 0) {

            set1 = (LineDataSet) chart.getData().getDataSetByIndex(0);
            set1.setValues(valLine);
            set1.notifyDataSetChanged();

            set2 = (LineDataSet) chart.getData().getDataSetByIndex(0);
            set2.setValues(valCircle);
            set2.notifyDataSetChanged();

            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();

        } else {

            // chart line
            set1 = new LineDataSet(valLine, "");
            set1.setDrawIcons(false);
            set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            set1.disableDashedLine();
            set1.setColor(Color.parseColor("#FF00C951"));
            set1.setCircleColor(Color.parseColor("#FF00C951"));
            set1.setDrawCircles(false);
            set1.setLineWidth(2f);
            set1.setCircleRadius(3f);
            set1.setCircleHoleRadius(2f);
            set1.setDrawCircleHole(true);
            set1.setCircleHoleColor(Color.WHITE);
            set1.setDrawValues(false);
            set1.setValueTextSize(12f);
            set1.setValueTextColor(Color.parseColor("#FF00C951"));;

            set1.setDrawFilled(true);
            set1.setFillFormatter(new IFillFormatter() {
                @Override
                public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
                    return chart.getAxisLeft().getAxisMinimum();
                }
            });

            // samo za SDK 18 i noviji
            if ( Build.VERSION.SDK_INT >= 18) {
                Drawable drawable = ContextCompat.getDrawable(context, R.drawable.fade_green);
                set1.setFillDrawable(drawable);

            } else {

                set1.setFillColor(R.color.plava_sjena);
            }

            // chart circles
            set2 = new LineDataSet(valCircle, "");
            set2.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            set2.setColor(Color.parseColor("#FFFFFF"));
            set2.setCircleColor(Color.parseColor("#FF00C951"));
            set2.setDrawCircles(true);
            set2.setLineWidth(0f);
            set2.setCircleRadius(3f);
            set2.setCircleHoleRadius(2f);
            set2.setDrawCircleHole(true);
            set2.setCircleHoleColor(Color.WHITE);
            set2.setValueTextSize(12f);
            set2.setValueTextColor(Color.parseColor("#FF00C951"));
            set2.setValueFormatter(new IValueFormatter() {
                @Override
                public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                    DecimalFormat format = new DecimalFormat("###,###,##0");
                    return format.format(value);
                }
            });

            ArrayList<ILineDataSet> dataSets = new ArrayList<>();

            dataSets.add(set2);
            dataSets.add(set1);

            LineData data = new LineData(dataSets);
            chart.setData(data);
        }
    }
}
