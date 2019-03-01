package com.victoryw.dependence.tree.story.factory.provider.dependency.api;

import com.victoryw.dependence.tree.story.factory.provider.dependency.api.dto.MethodDependencyDto;
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
        final String baseUrl = "http://localhost:8900";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(DependencyApiClient.class);
    }
}
