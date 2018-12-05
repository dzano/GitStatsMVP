package com.prowidgetstudio.gitstatsmvp.home.tabs.tab1;

import com.github.mikephil.charting.data.Entry;
import com.prowidgetstudio.gitstatsmvp.eventBus.ReadDatabaseEventBus;
import com.prowidgetstudio.gitstatsmvp.eventBus.RefreshTabsEventBus;
import com.prowidgetstudio.gitstatsmvp.eventBus.TotalEventBus;
import com.prowidgetstudio.gitstatsmvp.repository.Repository;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

/**
 * Created by Dzano on 4.12.2018.
 */

public class Tab1PresenterImpl implements Tab1Presenter {

    Tab1View tab1View;
    Tab1Interactor tab1Interactor;
    Repository repository;

    public Tab1PresenterImpl(Tab1View tab1View, Tab1Interactor tab1Interactor, Repository repository) {
        this.tab1View = tab1View;
        this.tab1Interactor = tab1Interactor;
        this.repository = repository;
    }

    @Override
    public void timeInMillis(int dan) {

        long lastUpdate = repository.readLong("lastUpdate");
        tab1Interactor.timeInMillis(dan, lastUpdate, new Tab1Interactor.OnStartTimeCalculated() {
            @Override
            public void onStartTime(long start, boolean today) {

                repository.readDatabaseDay(start, 1);
                tab1View.isToday(today);
            }
        });
    }

    @Override
    public void registerEventBus() {
        EventBus.getDefault().register(this);
    }

    @Override
    public void unregisterEventBus() {
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = false)
    public void onMessage(ReadDatabaseEventBus event) {

        if (event.getFrag() == 1) {

            tab1View.showTime(event.getStart());

            tab1Interactor.calculateData(event.getCommitsList(), event.getStart(), new Tab1Interactor.OnDataCalculated() {
                @Override
                public void onCalculated(ArrayList<Entry> valLine, ArrayList<Entry> valCircle, int max) {
                    tab1View.showChart(valLine, valCircle, max);
                }
            });
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = false)
    public void onMessage(TotalEventBus event) {

        if (event.getFrag() == 1) {

            tab1View.setTotal(repository.readInt("commitsCount"), event.getTotal());
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = false)
    public void onMessage(RefreshTabsEventBus event) {

        if (event.getRefresh().equals("Refresh")) {
            timeInMillis(0);
        }
    }
}
