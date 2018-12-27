package com.prowidgetstudio.gitstatsmvp.home.fragments.tab1;

import com.github.mikephil.charting.data.Entry;
import com.prowidgetstudio.gitstatsmvp.eventBus.TotalEventBus;
import com.prowidgetstudio.gitstatsmvp.repository.database.Commits;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Dzano on 4.12.2018.
 */

public class Tab1InteractorImpl implements Tab1Interactor {

    @Override
    public void timeInMillis(int dan, long lastUpdate, OnStartTimeCalculated onStartTimeCalculated) {

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        long now = calendar.getTimeInMillis();
        long dayDuration = 86400000;

        // prikazi od dana kad je bio posljednji update podataka
        long daysAgo = 0;
        if(now > lastUpdate){
            long time = now - lastUpdate;
            daysAgo = time / dayDuration + 1 + (long)dan;

        }else
            daysAgo = (long)dan;

        long dayStart = now - (daysAgo * dayDuration);

        // da li prikazati Today ili Day
        boolean today = false;
        if(lastUpdate >= now && daysAgo == 0)
            today = true;

        onStartTimeCalculated.onStartTime(dayStart, today);
    }

    @Override
    public void calculateData(List<Commits> commitsList, long start, OnDataCalculated onDataCalculated) {

        ArrayList<Entry> valLine = new ArrayList<>();    // chart line
        ArrayList<Entry> valCircle = new ArrayList<>();   // chart circles

        // zbir, prosjek i max //////////////////////////////////////
        int total = 0, average = 0, max = 0;

        for (int i = 0; i < commitsList.size(); i++) {

            int count = commitsList.get(i).getBroj();

            total += count;
            if (count > max)
                max = count;
        }

        //prosjek
        if (commitsList.size() != 0) average = total / commitsList.size();

        System.out.println("T " + total + " A " + average + " m " + max);
        // saljem total za Tab1
        EventBus.getDefault().post(new TotalEventBus(1, total));

        // povecavam Y-osu za 30%
        max = max * 13 / 10;
        if (max < 6) max = 6;
        else if (max % 2 != 0) max += 1;

        //###########################################################################
        // podaci za chart

        int previousCount = 0;
        int previousIndex = 0;
        long previousTime = 0;

        for (int i = 0; i < 24; i++) {

            long myStart = start + (long) (3600000 * i);
            long myEnd = myStart + 3600000;
            boolean indexFull = false;

            if (commitsList.size() == 0)
                valLine.add(new Entry(i, 0, myStart));
            else
                for (int z = commitsList.size(); z > 0; z--) {

                    long time = commitsList.get(z - 1).getCommitDate();
                    int count = commitsList.get(z - 1).getBroj();

                    if (time >= myStart && time < myEnd) {

                        valLine.add(new Entry(i, count, time));
                        indexFull = true;

                        if (count > average) {

                            if (previousCount != 0) {

                                if (count >= previousCount) {

                                    previousCount = count;
                                    previousTime = time;
                                    previousIndex = i;

                                    // ako je posljednji unos, upisi vrijednost za circle
                                    if (z == 1 && i == 23) {

                                        valCircle.add(new Entry(previousIndex, previousCount, previousTime));
                                        previousCount = 0;
                                    }

                                } else {

                                    valCircle.add(new Entry(previousIndex, previousCount, previousTime));
                                    previousCount = 0;

                                }

                            } else {

                                previousCount = count;
                                previousTime = time;
                                previousIndex = i;

                                // ako je posljednji unos, upisi vrijednost za circle
                                if ((z == 1 && i == 23)) {

                                    valCircle.add(new Entry(previousIndex, previousCount, previousTime));
                                    previousCount = 0;
                                }
                            }

                        } else {
                            // ako je unos manji od prethodnog, postavi circle na prethodni
                            if (previousCount != 0) {

                                valCircle.add(new Entry(previousIndex, previousCount, previousTime));
                                previousCount = 0;

                            } else if (commitsList.size() == 1) {
                                // ako postoji samo jedan unos, upisi vrijednost za circle
                                valCircle.add(new Entry(i, count, time));
                            }
                        }

                    } else if (z == 1 && !indexFull) {
                        // ako je posljednja vrijednost za ovaj vremenski period, a nema unosa...unesi 0
                        valLine.add(new Entry(i, 0, myStart));
                    }
                }
        }
        onDataCalculated.onCalculated(valLine, valCircle, max);
    }
}
