package com.victoryw.dependence.tree.story.factory.provider.dependency.api;

public class MethodDependencyFetchException extends RuntimeException {
    public MethodDependencyFetchException(String message, Throwable cause) {
        super(message, cause);
    }

    public MethodDependencyFetchException(String message) {
        super(message);
    }
}
