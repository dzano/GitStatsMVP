package com.prowidgetstudio.gitstatsmvp.repository.asyncTasks;

import android.annotation.SuppressLint;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.AsyncTask;

import com.prowidgetstudio.gitstatsmvp.repository.database.CommitDatabase;
import java.io.File;

/**
 * Created by Dzano on 3.12.2018.
 */

public class DeleteDatabaseTask extends AsyncTask {

    @SuppressLint("StaticFieldLeak")
    private Context context;
    private static final String DATABASE_NAME = "commits_db";

    public DeleteDatabaseTask(Context context) {
        this.context = context.getApplicationContext();
    }

    @Override
    protected String doInBackground(Object[] objects) {

        CommitDatabase commitDatabase = Room.databaseBuilder(context,
                CommitDatabase.class, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build();

        commitDatabase.daoAccess().deleteAll();

        File databases = new File(context.getApplicationInfo().dataDir + "/databases");
        File db = new File(databases, DATABASE_NAME);
        if (db.delete())
            System.out.println("Database deleted");
        else
            System.out.println("Failed to delete database");

        File journal = new File(databases, DATABASE_NAME + "-journal");
        if (journal.exists()) {
            if (journal.delete())
                System.out.println("Database journal deleted");
            else
                System.out.println("Failed to delete database journal");
        }
        return null;
    }
}
