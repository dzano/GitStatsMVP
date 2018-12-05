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
        client_id = "61ddb79f78cb91c993e6";
        client_secret = "59faaeaec12669af40ed30c18636e22d26b6d6ff";
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


