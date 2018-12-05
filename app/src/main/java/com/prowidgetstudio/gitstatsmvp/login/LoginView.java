package com.prowidgetstudio.gitstatsmvp.login;

import com.prowidgetstudio.gitstatsmvp.utils.Errors;
import com.prowidgetstudio.gitstatsmvp.webservice.login.ResponseLogin;
import com.prowidgetstudio.gitstatsmvp.webservice.info.ResponseInfo;
import com.prowidgetstudio.gitstatsmvp.webservice.info.ResponseReadMe;

/**
 * Created by Dzano on 30.11.2018.
 */

public interface LoginView {

    void showLoading();

    void hideLoading();

    void setUsernameError(Errors code);

    void setPasswordError(Errors code);

    void setUrlError(Errors code);

    void loginFailure(Errors code);

    void loggedIn();

}
