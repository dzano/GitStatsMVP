package com.prowidgetstudio.gitstatsmvp.repository;


import com.prowidgetstudio.gitstatsmvp.repository.database.Commits;
import java.util.List;

/**
 * Created by Dzano on 3.12.2018.
 */

public interface Repository {

    // SharedPreferences    //////////////////////////////////////////////////////////////

    String readString (String naziv);

    void saveString (String naziv, String vrijednost);

    int readInt (String naziv);

    void saveInt (String naziv, int vrijednost);

    long readLong(String naziv);

    void saveLong(String naziv, long vrijednost);

    boolean readBoolean(String naziv);

    void saveBoolean(String naziv, boolean vrijednost);

    String readToken();

    void saveToken(String vrijednost);

    String readReadMe();

    // database     ////////////////////////////////////////////////////////////////

    void appendData(List<Commits> commitsList);

    void deleteData();

    void readDatabaseDay(long start, int tab);

    void readDatabaseMonth(long start, long end, long brojDana);

}
