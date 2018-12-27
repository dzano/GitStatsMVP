package com.prowidgetstudio.gitstatsmvp.repository.asyncTasks;

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

public class ReadDatabaseMonthTask extends AsyncTask<Long, Void, List<Commits>> {

    private Context context;
    private static final String DATABASE_NAME = "commits_db";
    private long start, end, brojDana;

    public ReadDatabaseMonthTask(Context context) {
        this.context = context;
    }

    @Override
    protected List<Commits> doInBackground(Long... longs) {

        start = longs[0];
        end = longs[1];
        brojDana = longs[2];

        CommitDatabase commitDatabase = Room.databaseBuilder(context,
                CommitDatabase.class, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build();

        return commitDatabase.daoAccess().fetchAllCommitsbyCommitDate(start, end);
    }

    @Override
    protected void onPostExecute(List<Commits> commitsList) {

        EventBus.getDefault().postSticky(new ReadDatabaseEventBus(3, commitsList, start, end, (int)brojDana));

    }
}
