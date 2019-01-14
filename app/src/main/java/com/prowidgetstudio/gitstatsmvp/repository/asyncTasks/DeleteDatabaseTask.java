package com.prowidgetstudio.gitstatsmvp.repository.asyncTasks;

import android.os.AsyncTask;
import com.prowidgetstudio.gitstatsmvp.repository.database.CommitDatabase;
import java.io.File;


/**
 * Created by Dzano on 3.12.2018.
 */

public class DeleteDatabaseTask extends AsyncTask<Void, Void, Void> {


    private CommitDatabase commitDatabase;
    private File databases;

    public DeleteDatabaseTask(CommitDatabase commitDatabase, File databases) {
        this.commitDatabase = commitDatabase;
        this.databases = databases;
    }

    @Override
    protected Void doInBackground(Void... voids) {

        commitDatabase.daoAccess().deleteAll();

        String databaseStr = "commits_db";
        File db = new File(databases, databaseStr);
        if (db.delete())
            System.out.println("Database deleted");
        else
            System.out.println("Failed to delete database");

        File journal = new File(databases, databaseStr + "-journal");
        if (journal.exists()) {
            if (journal.delete())
                System.out.println("Database journal deleted");
            else
                System.out.println("Failed to delete database journal");
        }
        return null;
    }
}
