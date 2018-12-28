package com.prowidgetstudio.gitstatsmvp.repository;


import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

import com.prowidgetstudio.gitstatsmvp.repository.asyncTasks.DeleteDatabaseTask;
import com.prowidgetstudio.gitstatsmvp.repository.asyncTasks.ReadDatabaseTask;
import com.prowidgetstudio.gitstatsmvp.repository.asyncTasks.ReadDatabaseMonthTask;
import com.prowidgetstudio.gitstatsmvp.repository.asyncTasks.UpdateDatabaseTask;
import com.prowidgetstudio.gitstatsmvp.repository.database.Commits;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by Dzano on 3.12.2018.
 */

public class RepositoryImpl implements Repository {

    private SharedPreferences prefs;
    private Context context;

    public RepositoryImpl(SharedPreferences prefs, Context context) {

        this.prefs = prefs;
        this.context = context;
    }

    @Override
    public String readString(String naziv) {
        return prefs.getString(naziv, "");
    }

    @Override
    public void saveString(String naziv, String vrijednost) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(naziv, vrijednost);
        editor.apply();
    }

    @Override
    public int readInt(String naziv) {
        return prefs.getInt(naziv, 0);
    }

    @Override
    public void saveInt(String naziv, int vrijednost) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(naziv, vrijednost);
        editor.apply();
    }

    @Override
    public long readLong(String naziv) {
        return prefs.getLong(naziv, 0);
    }

    @Override
    public void saveLong(String naziv, long vrijednost) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putLong(naziv, vrijednost);
        editor.apply();
    }

    @Override
    public boolean readBoolean(String naziv) {
        return prefs.getBoolean(naziv, false);
    }

    @Override
    public void saveBoolean(String naziv, boolean vrijednost) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(naziv, vrijednost);
        editor.apply();
    }

    @Override
    public String readToken() {
        try {
            return new String((Base64.decode((prefs.getString("token", "")), Base64.DEFAULT)), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }
    }

    @Override
    public void saveToken(String vrijednost) {
        SharedPreferences.Editor editor = prefs.edit();
        vrijednost = Base64.encodeToString((vrijednost).getBytes(), Base64.DEFAULT);
        editor.putString("token", vrijednost);
        editor.apply();
    }

    @Override
    public String readReadMe() {
        try {
            return new String((Base64.decode((prefs.getString("content", "")), Base64.DEFAULT)), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }
    }

    @Override
    public void appendData(List<Commits> commitsList) {

        UpdateDatabaseTask updateDatabaseTask = new UpdateDatabaseTask(context);
        //noinspection unchecked
        updateDatabaseTask.execute(commitsList);
    }

    @Override
    public void deleteData() {

        DeleteDatabaseTask deleteDatabaseTask = new DeleteDatabaseTask(context);
        //noinspection unchecked
        deleteDatabaseTask.execute();
    }

    @Override
    public void readDatabaseDay(long start, int tab){

        ReadDatabaseTask asyncTask = new ReadDatabaseTask(context, tab);
        asyncTask.execute(start);
    }

    @Override
    public void readDatabaseMonth(long start, long end, long brojDana){

        ReadDatabaseMonthTask asyncTask = new ReadDatabaseMonthTask(context);
        asyncTask.execute(start, end, brojDana);
    }
}
