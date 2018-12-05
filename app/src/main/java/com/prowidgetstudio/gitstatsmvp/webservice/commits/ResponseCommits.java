package com.prowidgetstudio.gitstatsmvp.webservice.commits;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Dzano on 2.12.2018.
 */

public class ResponseCommits {

    @SerializedName("sha")
    @Expose
    private String sha;

    public String getSha() {
        return sha;
    }

    //////////////////////////////////////////////////////

    @SerializedName("date")
    @Expose
    private String date;

    public String getDate() {
        return date;
    }


    //////////////////////////////////////////////////////////

    @SerializedName("commit")
    @Expose
    private GetCommit commit;

    public GetCommit getCommit() {
        return commit;
    }


    //////////////////////////////////////////////////////////

}
