package com.prowidgetstudio.gitstatsmvp.repository.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

/**
 * Created by Dzano on 18.11.2018.
 */

@Database(entities = {Commits.class}, version = 1, exportSchema = false)
public abstract class CommitDatabase extends RoomDatabase {

    public abstract DaoAccess daoAccess() ;
    private static CommitDatabase INSTANCE;

    static CommitDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (CommitDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE =
                            Room.databaseBuilder(context.getApplicationContext(),
                                    CommitDatabase.class, "commits_db")
                                    .build();

                }
            }
        }
        return INSTANCE;
    }
}
