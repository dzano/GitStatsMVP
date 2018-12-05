package com.prowidgetstudio.gitstatsmvp.repository.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.Relation;
import android.support.annotation.NonNull;

import java.util.List;

/**
 * Created by Dzano on 18.11.2018.
 */

@Entity
public class Commits {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "commitDate")
    private Long commitDate;

    @ColumnInfo(name = "broj")
    private int broj;

    public Commits() {
    }


    public int getBroj() {
        return broj;
    }

    public void setBroj(int broj) {
        this.broj = broj;
    }

    public void setId (int broj) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public Long getCommitDate() {
        return commitDate;
    }

    public void setCommitDate (Long commitDate) {
        this.commitDate = commitDate;
    }




}
