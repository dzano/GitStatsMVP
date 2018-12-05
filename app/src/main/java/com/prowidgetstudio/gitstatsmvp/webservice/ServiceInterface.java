package com.prowidgetstudio.gitstatsmvp.webservice;


import com.prowidgetstudio.gitstatsmvp.webservice.commits.ResponseCommits;
import com.prowidgetstudio.gitstatsmvp.webservice.info.ResponseInfo;
import com.prowidgetstudio.gitstatsmvp.webservice.info.ResponseReadMe;
import com.prowidgetstudio.gitstatsmvp.webservice.login.RequestLogin;
import com.prowidgetstudio.gitstatsmvp.webservice.login.ResponseLogin;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;


/**
 * Created by Dzano on 1.12.2018.
 */

public interface ServiceInterface {

    @POST("/authorizations")
    Call <ResponseLogin> getUserToken(@Body RequestLogin body);

    @GET("repos/{url}")
    Call<ResponseInfo> getInfo(@Path(value = "url", encoded = true) String url);

    @GET("repos/{url}/readme")
    Call<ResponseReadMe> getReadMe(@Path(value = "url", encoded = true) String url);

    @GET("repos/{url}/commits?per_page=100")
    Call<ArrayList<ResponseCommits>> getCommits(@Path(value = "url", encoded = true) String url);

    @GET("repos/{url}/commits?per_page=100")
    Call<ArrayList<ResponseCommits>> getCommitsAgain(@Path(value = "url", encoded = true) String url, @Query(value = "sha", encoded = true) String sha);
}
