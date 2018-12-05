package com.prowidgetstudio.gitstatsmvp.customViews;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;
import com.prowidgetstudio.gitstatsmvp.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Dzano on 25.11.2018.
 */


@SuppressLint("ViewConstructor")
public class CustomMarkerView extends MarkerView {

    public final TextView markerText;

    public CustomMarkerView(Context context, int layoutResource) {
        super(context, layoutResource);

        markerText = findViewById(R.id.markerText);
    }


    @Override
    public void refreshContent(Entry e, Highlight highlight) {

        int red = (int)e.getX();

        Object a = e.getData();
        Long vrijeme = Long.parseLong(a.toString());

        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("H");
        String datum = formatter.format(new Date(vrijeme));

        int sat = Integer.parseInt(datum);
        String doDatum = "";
        if(sat == 23)
            doDatum = "0";
        else
            doDatum = Integer.toString(sat+1);

        int broj = (int) e.getY();
        datum = datum + " - " + doDatum + "hrs\nCommits: " + Integer.toString(broj);

        if(red < 2)
            markerText.setBackgroundResource(R.drawable.bubble_left);
        else if(red > 21)
            markerText.setBackgroundResource(R.drawable.bubble_right);
        else
            markerText.setBackgroundResource(R.drawable.bubble);

        markerText.setText(datum);

        super.refreshContent(e, highlight);
    }

    @Override
    public MPPointF getOffset() {
        return new MPPointF(-(getWidth() / 2), -getHeight());
    }
}