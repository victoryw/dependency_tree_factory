package com.victoryw.dependence.tree.story.factory.provider.dependency.api;

import org.apache.commons.lang3.StringUtils;

import java.util.Optional;

class MethodDependencyFetchException extends RuntimeException {

    MethodDependencyFetchException(String url, String method, Throwable ex) {
        super(MethodDependencyFetchExceptionMessageCreator.
                        createErrorMessage(url,
                                method,
                                Optional.empty(),
                                ""),
                ex);
    }

    MethodDependencyFetchException(String url, String method, int code, String message) {
        super(MethodDependencyFetchExceptionMessageCreator.
                createErrorMessage(method,
                        url,
                        Optional.of(code), message));
    }

    static class MethodDependencyFetchExceptionMessageCreator {
        static String createErrorMessage(String method, String url,
                                         Optional<Integer> code, String message) {
            return StringUtils.join(
                    "fail to fetch method dependencies,",
                    " url is ", url,
                    " method is ", method,
                    " error code is ", code.map(Object::toString).orElse(""),
                    " message is ", message);
        }
    }
}


