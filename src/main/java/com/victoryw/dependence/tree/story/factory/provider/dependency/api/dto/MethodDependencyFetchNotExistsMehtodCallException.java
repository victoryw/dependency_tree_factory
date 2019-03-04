package com.victoryw.dependence.tree.story.factory.provider.dependency.api.dto;

public class MethodDependencyFetchNotExistsMehtodCallException extends RuntimeException {
    private final MethodCallDto dto;
    private final boolean isCallerNotExist;
    private final boolean isCalleeNotExist;

    public MethodDependencyFetchNotExistsMehtodCallException(MethodCallDto dto, boolean isCallerNotExist, boolean isCalleeNotExist) {

        this.dto = dto;
        this.isCallerNotExist = isCallerNotExist;
        this.isCalleeNotExist = isCalleeNotExist;
    }
}
