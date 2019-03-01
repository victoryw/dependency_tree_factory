package com.victoryw.dependence.tree.story.factory.provider.dependency.api.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

public class Properties implements Serializable
{

    @SerializedName("callers")
    @Expose
    private String callers;
    @SerializedName("callees")
    @Expose
    private String callees;
    @SerializedName("color")
    @Expose
    private String color;
    private final static long serialVersionUID = 3512522601654285677L;

    public String getCallers() {
        return callers;
    }

    public void setCallers(String callers) {
        this.callers = callers;
    }

    public Properties withCallers(String callers) {
        this.callers = callers;
        return this;
    }

    public String getCallees() {
        return callees;
    }

    public void setCallees(String callees) {
        this.callees = callees;
    }

    public Properties withCallees(String callees) {
        this.callees = callees;
        return this;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Properties withColor(String color) {
        this.color = color;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("callers", callers).append("callees", callees).append("color", color).toString();
    }

}
