package com.prowidgetstudio.gitstatsmvp.login;

import com.prowidgetstudio.gitstatsmvp.utils.Errors;
import com.prowidgetstudio.gitstatsmvp.webservice.login.ResponseLogin;
import com.prowidgetstudio.gitstatsmvp.webservice.info.ResponseInfo;
import com.prowidgetstudio.gitstatsmvp.webservice.info.ResponseReadMe;

/**
 * Created by Dzano on 30.11.2018.
 */

public interface LoginInteractor {

    void login(String username, String password, String url,
               ValidationErrorListener validationErrorListener,
               OnLoginFinishedListener loginFinishedListener);

    interface OnLoginFinishedListener {
        void getUserData(ResponseLogin user, String url);
        void errorMsg(Errors code);
    }

    interface ValidationErrorListener {
        void usernameError(Errors code);
        void passwordError(Errors code);
        void urlError(Errors code);
    }

    void getInfo (String token, String url, OnInfoFinishedListener onInfoFinishedListener, OnReadMeFinishedListener onReadMeFinishedListener);

    interface OnInfoFinishedListener {
        void getInfoData(ResponseInfo info);
        void errorMsg();
    }

    void getReadMe(final String token, final String url, final OnReadMeFinishedListener onReadMeFinishedListener);

    interface OnReadMeFinishedListener {
        void getInfoData(ResponseReadMe readMe);
        void errorMsg();
    }

}
