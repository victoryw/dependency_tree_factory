package com.victoryw.dependence.tree.story.factory.domain;

class MethodCallTreeNotExistsException extends RuntimeException {
    private final String className;
    private final String methodName;

    MethodCallTreeNotExistsException(String className, String methodName) {

        this.className = className;
        this.methodName = methodName;
    }
}
