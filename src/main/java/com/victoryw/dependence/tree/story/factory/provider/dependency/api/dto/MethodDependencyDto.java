package com.victoryw.dependence.tree.story.factory.provider.dependency.api.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MethodDependencyDto implements Serializable
{

    @SerializedName("nodes")
    @Expose
    private List<MethodNodeDto> methodNodeDtos = new ArrayList<MethodNodeDto>();
    @SerializedName("edges")
    @Expose
    private List<MethodCallDto> methodCallDtos = new ArrayList<MethodCallDto>();
    private final static long serialVersionUID = -4832634467726450742L;

    public int getRelationCount() {
        return getMethodCallDtos().size();
    }

    public List<MethodNodeDto> getMethodNodeDtos() {
        return methodNodeDtos;
    }

    public void setMethodNodeDtos(List<MethodNodeDto> methodNodeDtos) {
        this.methodNodeDtos = methodNodeDtos;
    }

    public MethodDependencyDto withNodes(List<MethodNodeDto> methodNodeDtos) {
        this.methodNodeDtos = methodNodeDtos;
        return this;
    }

    public List<MethodCallDto> getMethodCallDtos() {
        return methodCallDtos;
    }

    public void setMethodCallDtos(List<MethodCallDto> methodCallDtos) {
        this.methodCallDtos = methodCallDtos;
    }

    public MethodDependencyDto withEdges(List<MethodCallDto> methodCallDtos) {
        this.methodCallDtos = methodCallDtos;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("nodes", methodNodeDtos).append("edges", methodCallDtos).toString();
    }

}
