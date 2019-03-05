
package com.victoryw.dependence.tree.story.factory.provider.jira.api.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Fields {

    @SerializedName("project")
    @Expose
    private Project project;
    @SerializedName("summary")
    @Expose
    private String summary;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("issuetype")
    @Expose
    private Issuetype issuetype;

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Issuetype getIssuetype() {
        return issuetype;
    }

    public void setIssuetype(Issuetype issuetype) {
        this.issuetype = issuetype;
    }

}
