package com.prowidgetstudio.gitstatsmvp.login;

import com.prowidgetstudio.gitstatsmvp.repository.Repository;
import com.prowidgetstudio.gitstatsmvp.utils.Errors;
import com.prowidgetstudio.gitstatsmvp.webservice.login.ResponseLogin;
import com.prowidgetstudio.gitstatsmvp.webservice.info.ResponseInfo;
import com.prowidgetstudio.gitstatsmvp.webservice.info.ResponseReadMe;


/**
 * Created by Dzano on 1.12.2018.
 */

public class LoginPresenterImpl implements LoginPresenter {

    LoginView loginView;
    LoginInteractor loginInteractor;
    Repository repository;

    public LoginPresenterImpl(LoginView loginView, LoginInteractor loginInteractor, Repository repository) {
        this.loginView = loginView;
        this.loginInteractor = loginInteractor;
        this.repository = repository;
    }
    
    @Override
    public void start(){

        if(repository.readBoolean("loggedin")) {

            if (repository.readReadMe().length() < 1)
                loginInteractor.getReadMe(repository.readToken(),
                        repository.readString("gitURL"),
                        new LoginInteractor.OnReadMeFinishedListener() {
                            @Override
                            public void getInfoData(ResponseReadMe readMe) {
                                repository.saveString("content", readMe.getContent());
                                loginView.loggedIn();
                            }

                            @Override
                            public void errorMsg() {
                                loginView.loggedIn();
                            }
                        });
            else
                loginView.loggedIn();
        }
    }

    @Override
    public void callLogin(String username, String password, String url) {

        loginView.showLoading();

        loginInteractor.login(username, password, url, new LoginInteractor.ValidationErrorListener() {

            @Override
            public void usernameError(Errors code) {
                loginView.hideLoading();
                loginView.setUsernameError(code);
            }

            @Override
            public void passwordError(Errors code) {
                loginView.hideLoading();
                loginView.setPasswordError(code);
            }

            @Override
            public void urlError(Errors code) {
                loginView.hideLoading();
                loginView.setUrlError(code);
            }

        }, new LoginInteractor.OnLoginFinishedListener() {
            @Override
            public void getUserData(ResponseLogin token, String url) {

             //   loginView.hideLoading();
                if (token != null) {
                    getProjectInfo(token.getToken(), url);

                } else {
                    loginView.loginFailure(Errors.USERNAME_PASS_INVALID);
                    loginView.hideLoading();
                }
            }

            @Override
            public void errorMsg(Errors code) {
                loginView.hideLoading();
                loginView.loginFailure(code);
            }
        });
    }

    @Override
    public void getProjectInfo(final String token, final String url) {

        loginInteractor.getInfo(token, url, new LoginInteractor.OnInfoFinishedListener() {
            @Override
            public void getInfoData(ResponseInfo info) {
                System.out.println("Zavrseno info: ");

                repository.saveToken(token);
                repository.saveString("gitURL", url);
                repository.saveBoolean("loggedin", true);
                repository.saveString("fullName", info.getFullName());
                repository.saveString("description", info.getDescription());

            }

            @Override
            public void errorMsg() {
                System.out.println("greska info: ");
                loginView.hideLoading();
                loginView.setUrlError(Errors.WRONG_URL);
            }
        }, new LoginInteractor.OnReadMeFinishedListener() {
            @Override
            public void getInfoData(ResponseReadMe readMe) {

                loginView.hideLoading();
                repository.saveString("content", readMe.getContent());
                loginView.loggedIn();
            }

            @Override
            public void errorMsg() {

                System.out.println("greska readme: ");
                // moguce da ne postoji ReadMe?
                loginView.loggedIn();
            }
        });

    }

}
