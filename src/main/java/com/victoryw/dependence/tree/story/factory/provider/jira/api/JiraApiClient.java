package com.victoryw.dependence.tree.story.factory.provider.jira.api;

import com.victoryw.dependence.tree.story.factory.provider.jira.api.dto.JiraCardDto;
import com.victoryw.dependence.tree.story.factory.provider.util.BasicAuthenticationInterceptor;
import com.victoryw.dependence.tree.story.factory.provider.util.RetrofitBuilder;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface JiraApiClient {
    @POST("/issue")
    @Headers("content-type:application/json; charset=utf-8")
    Call createIssues(@Body JiraCardDto cardDto);

    static JiraApiClient create() {
//        final String baseUrl = "http://localhost:8900";
        final String baseUrl = "http://10.135.102.62:8080/rest/api/2";
        Retrofit retrofit = RetrofitBuilder.createRetrofit(baseUrl,
                new BasicAuthenticationInterceptor(
                        "benewu",
                        "111111"));

        return retrofit.create(JiraApiClient.class);
    }

}
