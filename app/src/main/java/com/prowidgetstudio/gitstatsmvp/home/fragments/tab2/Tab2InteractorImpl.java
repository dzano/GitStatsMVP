package com.prowidgetstudio.gitstatsmvp.home.fragments.tab2;

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

public class Tab2InteractorImpl implements Tab2Interactor {

    @Override
    public void timeInMillis(int week, long lastUpdate, Tab2Interactor.OnStartTimeCalculated onStartTimeCalculated) {

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());

        long now = calendar.getTimeInMillis();
        long weekDuration = 86400000 * 7;

        // prikazi od sedmice kad je bio posljednji update podataka
        long weeksAgo = 0;
        if(now > lastUpdate){
            long time = now - lastUpdate;
            weeksAgo = time / weekDuration + 1 + (long)week;

        }else
            weeksAgo = (long)week;

        long weekStart = now - (weeksAgo * weekDuration);

        onStartTimeCalculated.onStartTime(weekStart);
    }

    @Override
    public void calculateData(List<Commits> commitsList, long start, OnDataCalculated onDataCalculated) {

        ArrayList<Entry> valLine = new ArrayList<>();    // chart line
        ArrayList<Entry> valCircle = new ArrayList<>();   // chart circles

        ArrayList<Long> dailyTime = new ArrayList<>();
        ArrayList<Integer> dailyCount = new ArrayList<>();

        // grupisanje commit-a po danima ////////////////////////////////////////////////////
        // i zbir, prosjek i max //////////////////////////////////////

        int total = 0, average = 0, max = 0, devider = 0;

        for(int i = 0; i < 7; i++){

            long myStart = start + (long)(3600000 *24 * i);
            long myEnd = myStart + (long)(3600000 * 24);
            int totalCount = 0;

            for(int z = commitsList.size(); z > 0; z--){

                int count = commitsList.get(z-1).getBroj();
                long time = commitsList.get(z-1).getCommitDate();

                if(time >= myStart && time < myEnd){

                    totalCount += count;
                    total += count;
                    if(totalCount > max)
                        max = totalCount;

                }
            }

            if(totalCount > 0) {
                devider++;
            }
            dailyTime.add(myStart);
            dailyCount.add(totalCount);
        }
        ///////////////////////////////////////////////////////////////////////////////
        //prosjek
        if (devider != 0)
            average = total / devider;

        // saljem total za Tab2Fragment
        EventBus.getDefault().post(new TotalEventBus(2, total));

        // povecavam Y-osu za 30%
        max = max * 13/10;
        if(max < 6) max = 6;
        else if(max%2 != 0) max += 1;

        //###########################################################################
        // podaci za chart

        int previousCount = 0;
        int previousIndex = 0;
        long previousTime = 0;

        for( int i = 0; i < 7; i++){

            long faktor = 3600000 * 24 * (long)i;
            long dan = 3600000 * 24;
            long myStart = start + faktor;
            long myEnd = myStart + dan;
            boolean indexFull = false;

            for(int z = dailyCount.size(); z > 0; z--){

                long time = dailyTime.get(z-1);
                int count = dailyCount.get(z-1);

                if(time >= myStart && time < myEnd){

                    valLine.add(new Entry(i, count, time));
                    indexFull = true;

                    if(count > average){

                        if(previousCount != 0){

                            if(count >= previousCount){

                                previousCount = count;
                                previousTime = time;
                                previousIndex = i;

                                // ako je posljednji unos, upisi vrijednost za circle
                                if(z == 1 && i == 6){

                                    valCircle.add(new Entry(previousIndex, previousCount, previousTime));
                                    previousCount = 0;
                                }

                            }else{

                                valCircle.add(new Entry(previousIndex, previousCount, previousTime));
                                previousCount = 0;

                            }

                        }else {

                            previousCount = count;
                            previousTime = time;
                            previousIndex = i;

                            // ako je posljednji unos, upisi vrijednost za circle
                            if((z == 1 && i == 6)){

                                valCircle.add(new Entry(previousIndex, previousCount, previousTime));
                                previousCount = 0;
                            }
                        }

                    }else{
                        // ako je unos manji od prethodnog, postavi circle na prethodni
                        if(previousCount != 0){

                            valCircle.add(new Entry(previousIndex, previousCount, previousTime));
                            previousCount = 0;

                        } else if(dailyCount.size()==1){
                            // ako postoji samo jedan unos, upisi vrijednost za circle
                            valCircle.add(new Entry(i, count, time));
                        }
                    }

                }else if(z==1 && !indexFull){
                    // ako je posljednja vrijednost za ovaj vremenski period, a nema unosa...unesi 0
                    valLine.add(new Entry(i, 0, myStart));
                }
            }
        }
        onDataCalculated.onCalculated(valLine, valCircle, max);
    }
}
