package com.victoryw.dependence.tree.story.factory.dependence.service.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

public class MethodNodeDto implements Serializable
{
    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("properties")
    @Expose
    private Properties properties;

    private final static long serialVersionUID = 833545941351843082L;

    public static MethodNodeDto createMethodNode(String nodeId) {
        MethodNodeDto node1 = new MethodNodeDto();
        node1.withId(nodeId);
        return node1;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public MethodNodeDto withId(String id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public MethodNodeDto withTitle(String title) {
        this.title = title;
        return this;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public MethodNodeDto withProperties(Properties properties) {
        this.properties = properties;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id", id).append("title", title).append("properties", properties).toString();
    }

}


