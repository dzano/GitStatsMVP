package com.prowidgetstudio.gitstatsmvp.repository.asyncTasks;

import android.os.AsyncTask;
import com.prowidgetstudio.gitstatsmvp.eventBus.ReadDatabaseEventBus;
import com.prowidgetstudio.gitstatsmvp.repository.database.CommitDatabase;
import com.prowidgetstudio.gitstatsmvp.repository.database.Commits;
import org.greenrobot.eventbus.EventBus;
import java.util.List;

/**
 * Created by Dzano on 20.11.2018.
 */

public class ReadDatabaseTask extends AsyncTask<Long, Void, List<Commits>> {

    private CommitDatabase commitDatabase;
    private long start;

    private long time;

    public ReadDatabaseTask(CommitDatabase commitDatabase, int time) {    // 1-day, 2-week, else month
        this.commitDatabase = commitDatabase;
        this.time = time;
    }

    @Override
    protected List<Commits> doInBackground(Long... longs) {

        long end = 0;

        start = longs[0];
        long ONE_DAY_TIME = 86399999;
        long ONE_WEEK_TIME = 86400000 * 7 - 1;

        if(time == 1)
            end = start + ONE_DAY_TIME;
        else if(time==2)
            end = start + ONE_WEEK_TIME;
        else
            end = start + ONE_DAY_TIME * time;

        return commitDatabase.daoAccess().fetchAllCommitsbyCommitDate(start, end);
    }

    @Override
    protected void onPostExecute(List<Commits> commitsList) {

        if(time==1) EventBus.getDefault().postSticky(new ReadDatabaseEventBus(1, commitsList, start, 0, 0));
        else if(time==2) EventBus.getDefault().postSticky(new ReadDatabaseEventBus(2, commitsList, start, 0, 0));
    }
}
