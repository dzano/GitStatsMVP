package com.prowidgetstudio.gitstatsmvp.login;

import com.prowidgetstudio.gitstatsmvp.utils.Errors;
import com.prowidgetstudio.gitstatsmvp.utils.Utils;
import com.prowidgetstudio.gitstatsmvp.webservice.login.RequestLogin;
import com.prowidgetstudio.gitstatsmvp.webservice.login.ResponseLogin;
import com.prowidgetstudio.gitstatsmvp.webservice.ServiceInterface;
import com.prowidgetstudio.gitstatsmvp.webservice.ServiceWrapper;
import com.prowidgetstudio.gitstatsmvp.webservice.info.ResponseInfo;
import com.prowidgetstudio.gitstatsmvp.webservice.info.ResponseReadMe;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Dzano on 1.12.2018.
 */

public class LoginInteractorImpl implements LoginInteractor{

    @Override
    public void login(String username,
                      String password,
                      final String url,
                      ValidationErrorListener validationErrorListener,
                      final OnLoginFinishedListener loginFinishedListener) {

        if (isDataValid(username, password, url, validationErrorListener)) {

            ServiceInterface serviceInterface = ServiceWrapper.createService(ServiceInterface.class, username, password);
            Call<ResponseLogin> call = serviceInterface.getUserToken(new RequestLogin());
            call.enqueue(new Callback<ResponseLogin>() {
                @Override
                public void onResponse(Call<ResponseLogin> call, Response<ResponseLogin> response) {
                    if (response.isSuccessful()) {

                        loginFinishedListener.getUserData(response.body(), url);

                    } else {

                        loginFinishedListener.errorMsg(Errors.USERNAME_PASS_INVALID);
                        System.out.println("ODGOVOR: " + response.toString());
                    }

                }

                @Override
                public void onFailure(Call<ResponseLogin> call, Throwable t) {

                    loginFinishedListener.errorMsg(Errors.LOGIN_FAILED);
                }
            });
        }

    }

    private boolean isDataValid(String username, String password, String url, ValidationErrorListener validationErrorListener) {

        if (username.length() < 1) {
            validationErrorListener.usernameError(Errors.ENTER_USERNAME);
            return false;
        } else if (password.length() < 1) {
            validationErrorListener.passwordError(Errors.ENTER_PASSWORD);
            return false;
        } else if (url.length() < 1) {
            validationErrorListener.urlError(Errors.ENTER_URL);
            return false;
        }else if (!Utils.isValidGitURL(url)) {
            validationErrorListener.urlError(Errors.WRONG_URL);
            return false;
        }else {
            return true;
        }
    }

    @Override
    public void getInfo(final String token, final String url,
                        final OnInfoFinishedListener onInfoFinishedListener,
                        final OnReadMeFinishedListener onReadMeFinishedListener) {

        ServiceInterface serviceInterface = ServiceWrapper.createService(ServiceInterface.class, token);
        Call<ResponseInfo> call = serviceInterface.getInfo(url);
        call.enqueue(new Callback<ResponseInfo>() {
            @Override
            public void onResponse(Call<ResponseInfo> call, Response<ResponseInfo> response) {
                if (response.isSuccessful()) {

                    onInfoFinishedListener.getInfoData(response.body());
                    getReadMe(token, url, onReadMeFinishedListener);

                } else {

                    onInfoFinishedListener.errorMsg();
                    System.out.println("ODGOVOR: " + response.toString());
                }

            }

            @Override
            public void onFailure(Call<ResponseInfo> call, Throwable t) {

                onInfoFinishedListener.errorMsg();
            }

        });
    }

    @Override
    public void getReadMe(final String token, final String url, final OnReadMeFinishedListener onReadMeFinishedListener){

        final ServiceInterface serviceInterface = ServiceWrapper.createService(ServiceInterface.class, token);
        Call<ResponseReadMe> call = serviceInterface.getReadMe(url);
        call.enqueue(new Callback<ResponseReadMe>() {
            @Override
            public void onResponse(Call<ResponseReadMe> call, Response<ResponseReadMe> response) {
                if (response.isSuccessful()) {

                    onReadMeFinishedListener.getInfoData(response.body());


                } else {

                    onReadMeFinishedListener.errorMsg();
                    System.out.println("ODGOVOR: " + response.toString());
                }

            }

            @Override
            public void onFailure(Call<ResponseReadMe> call, Throwable t) {

                onReadMeFinishedListener.errorMsg();
            }

        });

    }


}
