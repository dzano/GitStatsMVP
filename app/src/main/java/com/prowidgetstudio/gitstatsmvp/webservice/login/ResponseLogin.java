package com.prowidgetstudio.gitstatsmvp.webservice.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Dzano on 1.12.2018.
 */

public class ResponseLogin {


    @SerializedName("token")
    @Expose
    private String token;

    public String getToken() {
        return token;
    }

}
