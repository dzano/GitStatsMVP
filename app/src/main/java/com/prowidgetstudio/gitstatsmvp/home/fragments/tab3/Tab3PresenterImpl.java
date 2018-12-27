package com.prowidgetstudio.gitstatsmvp.home.fragments.tab3;

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

public class Tab3PresenterImpl implements Tab3Presenter {

    Tab3View tab3View;
    Tab3Interactor tab3Interactor;
    Repository repository;

    public Tab3PresenterImpl(Tab3View tab3View, Tab3Interactor tab3Interactor, Repository repository) {
        this.tab3View = tab3View;
        this.tab3Interactor = tab3Interactor;
        this.repository = repository;
    }

    @Override
    public void timeInMillis(int dan) {

        long lastUpdate = repository.readLong("lastUpdate");
        tab3Interactor.timeInMillis(dan, lastUpdate, new Tab3Interactor.OnStartTimeCalculated() {
            @Override
            public void onStartTime(long start, long end, int brojDana) {

                repository.readDatabaseMonth(start, end, (long)brojDana);
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

        if (event.getFrag() == 3) {

            tab3View.showTime(event.getStart(), event.getEnd());

            tab3Interactor.calculateData(event.getCommitsList(), event.getStart(), (int) event.getBrojDana(), new Tab3Interactor.OnDataCalculated() {
                @Override
                public void onCalculated(ArrayList<Entry> valLine, ArrayList<Entry> valCircle, int max) {

                    tab3View.showChart(valLine, valCircle, max);
                }
            });

        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = false)
    public void onMessage(TotalEventBus event) {

        if (event.getFrag() == 3) {
            tab3View.setTotal(repository.readInt("commitsCount"), event.getTotal());
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = false)
    public void onMessage(RefreshTabsEventBus event) {

        if (event.getRefresh().equals("Refresh")) {
            timeInMillis(0);
        }
    }
}
