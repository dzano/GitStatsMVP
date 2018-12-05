package com.prowidgetstudio.gitstatsmvp.eventBus;

/**
 * Created by Dzano on 4.12.2018.
 */

public class RefreshTabsEventBus {

    private final String refresh;

    public RefreshTabsEventBus(String refresh) {
        this.refresh = refresh;
    }

    public String getRefresh() {
        return refresh;
    }
}
