package com.prowidgetstudio.gitstatsmvp.repository.asyncTasks;

import android.annotation.SuppressLint;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.AsyncTask;

import com.prowidgetstudio.gitstatsmvp.repository.database.CommitDatabase;
import com.prowidgetstudio.gitstatsmvp.repository.database.Commits;

import java.util.List;

/**
 * Created by Dzano on 3.12.2018.
 */

public class UpdateDatabase extends AsyncTask<List<Commits>, Void, String> {

    @SuppressLint("StaticFieldLeak")
    private Context context;
    private static final String DATABASE_NAME = "commits_db";

    public UpdateDatabase(Context context){
        this.context = context.getApplicationContext();
    }

    @Override
    protected String doInBackground(List<Commits>[] lists) {

        CommitDatabase commitDatabase = Room.databaseBuilder(context,
                CommitDatabase.class, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build();

        commitDatabase.daoAccess().insertMultipleCommits(lists[0]);

        return null;
    }
}
