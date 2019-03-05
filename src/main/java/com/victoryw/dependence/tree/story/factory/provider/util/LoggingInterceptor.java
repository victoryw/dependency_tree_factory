package com.victoryw.dependence.tree.story.factory.provider.util;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class LoggingInterceptor implements Interceptor {
    private static Logger logger = LoggerFactory.getLogger(LoggingInterceptor.class);
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        logger.info(String.format("Sending request %s on %s", request.url(), chain.connection()));
        Response response = chain.proceed(request);
        return response;
    }
}
