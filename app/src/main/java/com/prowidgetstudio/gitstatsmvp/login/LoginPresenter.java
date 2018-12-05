package com.prowidgetstudio.gitstatsmvp.login;

/**
 * Created by Dzano on 30.11.2018.
 */

public interface LoginPresenter {

    void start();

    void callLogin(String username, String password, String url);

    void getProjectInfo(String token, String url);

}
