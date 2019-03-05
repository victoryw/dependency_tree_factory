
package com.victoryw.dependence.tree.story.factory.provider.jira.api.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JiraCardDto {

    @SerializedName("fields")
    @Expose
    private Fields fields;

    public Fields getFields() {
        return fields;
    }

    public void setFields(Fields fields) {
        this.fields = fields;
    }

}
