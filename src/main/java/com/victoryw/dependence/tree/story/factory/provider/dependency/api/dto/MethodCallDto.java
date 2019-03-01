package com.victoryw.dependence.tree.story.factory.provider.dependency.api.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MethodCallDto implements Serializable
{

    @SerializedName("a")
    @Expose
    private String a;
    @SerializedName("b")
    @Expose
    private String b;
    @SerializedName("labels")
    @Expose
    private List<Object> labels = new ArrayList<Object>();
    private final static long serialVersionUID = 4258651978005943926L;

    public MethodCallDto(String from, String to) {
        this.a = from;
        this.b = to;
    }

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public MethodCallDto withA(String a) {
        this.a = a;
        return this;
    }

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }

    public MethodCallDto withB(String b) {
        this.b = b;
        return this;
    }

    public List<Object> getLabels() {
        return labels;
    }

    public void setLabels(List<Object> labels) {
        this.labels = labels;
    }

    public MethodCallDto withLabels(List<Object> labels) {
        this.labels = labels;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("a", a).append("b", b).append("labels", labels).toString();
    }

}

