package com.prowidgetstudio.gitstatsmvp.webservice.commits;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Dzano on 2.12.2018.
 */

public class GetCommitter {

    @SerializedName("date")
    @Expose
    private String date;

    public String getDate() {
        return date;
    }
}
