package com.victoryw.dependence.tree.story.factory.provider.dependency.api;

import org.apache.commons.lang3.StringUtils;

import java.util.Optional;

public class MethodDependencyFetchException extends RuntimeException {

    MethodDependencyFetchException(String url, String method, Throwable ex) {
        super(MethodDependencyFetchExceptionMessageCreator.
                createErrorMessage(url, method),
                ex);
    }

    MethodDependencyFetchException(String method, String url, int code, String message) {
        super(MethodDependencyFetchExceptionMessageCreator.
                createErrorMessage(method,
                        url,
                        message, Optional.of(code)));
    }

    static MethodDependencyFetchException create(String method, String url, int code, String message) {
        return new MethodDependencyFetchException(url, method,code, message);
    }
}

class MethodDependencyFetchExceptionMessageCreator {
    static String createErrorMessage(String url, String method) {
        return createErrorMessage(url, method, "", Optional.empty());
    }

    static String createErrorMessage(String method, String url, String message, Optional<Integer> code) {
        return StringUtils.join(
                "fail to fetch method dependencies,",
                " url is ", url,
                " method is ", method,
                " error code is ", code.map(Object::toString).orElse(""),
                " message is ", message);
    }
}
