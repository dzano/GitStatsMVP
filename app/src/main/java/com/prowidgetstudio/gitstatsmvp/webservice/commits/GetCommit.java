package com.prowidgetstudio.gitstatsmvp.webservice.commits;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Dzano on 2.12.2018.
 */

public class GetCommit {

    @SerializedName("committer")
    @Expose
    private GetCommitter committer;

    public GetCommitter getCommitter() {
        return committer;
    }
}
