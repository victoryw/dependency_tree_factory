package com.victoryw.dependence.tree.story.factory.provider.util;

import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class BasicAuthenticationInterceptor implements Interceptor {

    private String authToken;


    public BasicAuthenticationInterceptor(String userName, String password) {
        authToken = Credentials.basic(userName,password);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();

        Request.Builder builder = original.newBuilder()
                .header("Authorization", authToken);

        Request request = builder.build();
        return chain.proceed(request);
    }
}
