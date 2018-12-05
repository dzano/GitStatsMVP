package com.prowidgetstudio.gitstatsmvp.repository.asyncTasks;

import android.annotation.SuppressLint;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.AsyncTask;

import com.prowidgetstudio.gitstatsmvp.eventBus.ReadDatabaseEventBus;
import com.prowidgetstudio.gitstatsmvp.repository.database.CommitDatabase;
import com.prowidgetstudio.gitstatsmvp.repository.database.Commits;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by Dzano on 20.11.2018.
 */

public class ReadDatabase extends AsyncTask<Long, Void, List<Commits>> {

    @SuppressLint("StaticFieldLeak")
    private Context context;
    private static final String DATABASE_NAME = "commits_db";
    private long start, end;

    private long time;

    public ReadDatabase(Context context, int time) {    // 1-day, 2-week, else month
        this.context = context.getApplicationContext();
        this.time = time;
    }

    @Override
    protected List<Commits> doInBackground(Long... longs) {

        start = longs[0];
        long ONE_DAY_TIME = 86399999;
        long ONE_WEEK_TIME = 86400000 * 7 - 1;

        if(time == 1)   end = start + ONE_DAY_TIME;
        else if(time==2) end = start + ONE_WEEK_TIME;
        else end = start + ONE_DAY_TIME * time;

        CommitDatabase commitDatabase = Room.databaseBuilder(context,
                CommitDatabase.class, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build();

        return commitDatabase.daoAccess().fetchAllCommitsbyCommitDate(start, end);
    }

    @Override
    protected void onPostExecute(List<Commits> commitsList) {

        if(time==1) EventBus.getDefault().postSticky(new ReadDatabaseEventBus(1, commitsList, start, 0, 0));
        else if(time==2) EventBus.getDefault().postSticky(new ReadDatabaseEventBus(2, commitsList, start, 0, 0));
    }
}
