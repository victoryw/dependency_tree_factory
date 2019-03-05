package com.victoryw.dependence.tree.story.factory.provider.dependency.api;

import com.victoryw.dependence.tree.story.factory.provider.dependency.api.dto.MethodDependencyDto;
import com.victoryw.dependence.tree.story.factory.provider.util.LoggingInterceptor;
import com.victoryw.dependence.tree.story.factory.provider.util.RetrofitBuilder;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface DependencyApiClient {
    @GET("method/{class}/{method}/callees")
    Call<MethodDependencyDto> getMethodDependencies(
            @Path("class") String className,
            @Path("method") String methodName);

    static DependencyApiClient create() {
//        final String baseUrl = "http://localhost:8900";
        final String baseUrl = "http://10.127.151.14:8900/";
        Retrofit retrofit = RetrofitBuilder.createRetrofit(baseUrl);
        return retrofit.create(DependencyApiClient.class);
    }
}

