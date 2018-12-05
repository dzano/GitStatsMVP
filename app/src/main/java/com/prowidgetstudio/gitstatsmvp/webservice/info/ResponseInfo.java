package com.prowidgetstudio.gitstatsmvp.webservice.info;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Dzano on 1.12.2018.
 */

public class ResponseInfo {

    @SerializedName("full_name")
    @Expose
    private String fullName;

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getFullName() {
        return fullName;
    }



    @SerializedName("description")
    @Expose
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
