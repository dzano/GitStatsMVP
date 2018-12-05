package com.prowidgetstudio.gitstatsmvp.repository.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;


@Dao
public interface DaoAccess {

    @Insert
    void insertOnlySingleCommit(Commits commits);

    @Insert
    void insertMultipleCommits(List<Commits> commitsList);

    @Query("SELECT * FROM Commits WHERE commitDate = :commitDate")
    Commits fetchOneCommitsbyCommitDate(long commitDate);

    @Query("SELECT id, commitDate, broj FROM Commits WHERE commitDate >= :start AND commitDate <= :end")
    List<Commits> fetchAllCommitsbyCommitDate(long start, long end);

    @Update
    void updateCommit(Commits commits);

    @Delete
    void deleteCommit(Commits commits);

    @Query("DELETE FROM Commits")
    void deleteAll();

}
