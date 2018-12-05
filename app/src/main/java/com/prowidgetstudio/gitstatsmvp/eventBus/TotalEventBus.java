package com.prowidgetstudio.gitstatsmvp.eventBus;

/**
 * Created by Dzano on 4.12.2018.
 */

public class TotalEventBus {

    private final int total, frag;

    public TotalEventBus(int frag, int total) {
        this.total = total;
        this.frag = frag;
    }

    public int getTotal() {
        return total;
    }

    public int getFrag() {
        return frag;
    }
}
