package com.prowidgetstudio.gitstatsmvp.eventBus;

import com.prowidgetstudio.gitstatsmvp.repository.database.Commits;
import java.util.List;

/**
 * Created by Dzano on 4.12.2018.
 */

public class ReadDatabaseEventBus {

    private final int frag, brojDana;
    private final List<Commits> commitsList;
    private final long start, end;

    public ReadDatabaseEventBus(int frag, List<Commits> commitsList, long start, long end, int brojDana ) {
        this.frag = frag;
        this.commitsList = commitsList;
        this.start = start;
        this.end = end;
        this.brojDana = brojDana;
    }

    public int getFrag() {
        return frag;
    }

    public List<Commits> getCommitsList() {
        return commitsList;
    }

    public long getStart() {
        return start;
    }

    public long getEnd(){
        return end;
    }

    public int getBrojDana(){
        return brojDana;
    }
}
