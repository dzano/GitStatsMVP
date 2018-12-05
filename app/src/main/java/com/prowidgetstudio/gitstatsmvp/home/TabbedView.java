package com.prowidgetstudio.gitstatsmvp.home;


/**
 * Created by Dzano on 2.12.2018.
 */

public interface TabbedView {

    void showProjectInfo(String name, String description, String readMe);

    void showLoading(int count);

    void hideLoading();

    void noAccess();

    void logOut();
}
