package com.prowidgetstudio.gitstatsmvp.home.tabs.tab2;

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

public class Tab2PresenterImpl implements Tab2Presenter {

    Tab2View tab2View;
    Tab2Interactor tab2Interactor;
    Repository repository;

    public Tab2PresenterImpl(Tab2View tab2View, Tab2Interactor tab2Interactor, Repository repository) {
        this.tab2View = tab2View;
        this.tab2Interactor = tab2Interactor;
        this.repository = repository;
    }

    @Override
    public void timeInMillis(int dan) {

        long lastUpdate = repository.readLong("lastUpdate");
        tab2Interactor.timeInMillis(dan, lastUpdate, new Tab2Interactor.OnStartTimeCalculated() {
            @Override
            public void onStartTime(long start) {

                repository.readDatabaseDay(start, 2);
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

        if (event.getFrag() == 2) {

            tab2View.showTime(event.getStart());

            tab2Interactor.calculateData(event.getCommitsList(), event.getStart(), new Tab2Interactor.OnDataCalculated() {
                @Override
                public void onCalculated(ArrayList<Entry> valLine, ArrayList<Entry> valCircle, int max) {
                    tab2View.showChart(valLine, valCircle, max);
                }
            });
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = false)
    public void onMessage(TotalEventBus event) {

        if (event.getFrag() == 2) {

            tab2View.setTotal(repository.readInt("commitsCount"), event.getTotal());
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = false)
    public void onMessage(RefreshTabsEventBus event) {

        if (event.getRefresh().equals("Refresh")) {
            timeInMillis(0);
        }
    }
}
