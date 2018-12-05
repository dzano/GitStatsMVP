package com.prowidgetstudio.gitstatsmvp.webservice.info;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Dzano on 2.12.2018.
 */

public class ResponseReadMe {


    @SerializedName("content")
    @Expose
    private String content;

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

}
