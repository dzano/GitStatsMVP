package com.prowidgetstudio.gitstatsmvp.repository.asyncTasks;

import android.os.AsyncTask;
import com.prowidgetstudio.gitstatsmvp.repository.database.CommitDatabase;
import com.prowidgetstudio.gitstatsmvp.repository.database.Commits;
import java.util.List;

/**
 * Created by Dzano on 3.12.2018.
 */

public class UpdateDatabaseTask extends AsyncTask<List<Commits>, Void, Void> {

    private CommitDatabase commitDatabase;

    public UpdateDatabaseTask(CommitDatabase commitDatabase){
        this.commitDatabase = commitDatabase;
    }

    @Override
    protected Void doInBackground(List<Commits>[] lists) {

        commitDatabase.daoAccess().insertMultipleCommits(lists[0]);

        return null;
    }
}
