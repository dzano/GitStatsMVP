package com.prowidgetstudio.gitstatsmvp.webservice.login;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Dzano on 1.12.2018.
 */

public class RequestLogin {

    private String client_id;
    private String client_secret;
    private List<String> scopes;
    private String note;


    public RequestLogin(){

        scopes = Arrays.asList("user:email");
        client_id = "xxx";
        client_secret = "xxx";
        note = "GitStatsMVP";
    }

    public String getClient_id() {
        return client_id;
    }

    public String getClient_secret() {
        return client_secret;
    }

    public List<String> getScopes() {
        return scopes;
    }

    public String getNote() {
        return note;
    }
}


