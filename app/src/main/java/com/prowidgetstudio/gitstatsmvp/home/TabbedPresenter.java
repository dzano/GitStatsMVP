package com.prowidgetstudio.gitstatsmvp.home;

/**
 * Created by Dzano on 2.12.2018.
 */

public interface TabbedPresenter {

    void downloadData(boolean firstRun);

    void getInfoData();

    void logOut();

    void stopDownload();
}
