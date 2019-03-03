package com.victoryw.dependence.tree.story.factory.provider.dependency.api;

import com.sun.xml.internal.ws.util.CompletedFuture;
import com.victoryw.dependence.tree.story.factory.domain.MethodDag;
import com.victoryw.dependence.tree.story.factory.provider.dependency.api.dto.MethodCallDagFactory;
import com.victoryw.dependence.tree.story.factory.provider.dependency.api.dto.MethodDependencyDto;
import io.vavr.control.Try;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Response;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

class MethodDependencyProvider {

    private final DependencyApiClient apiClient;
    private final MethodCallDagFactory factory;

    MethodDependencyProvider() {
        apiClient = DependencyApiClient.create();
        factory = new MethodCallDagFactory();
    }

    Optional<MethodDag> getMethodDependencies(String className, String methodName) {
        final Response<MethodDependencyDto> response = queryForResponse(className, methodName);
        validResponse(response);
        return transformResponse(response);
    }

    private Optional<MethodDag> transformResponse(Response<MethodDependencyDto> response) {
        return Optional
                .ofNullable(response.body())
                .map(factory::create);
    }

    private void validResponse(Response<MethodDependencyDto> response) {
        if (!response.isSuccessful()) {
            final Request request = response.raw().request();
            throw new MethodDependencyFetchException(request.method(), request.url()
                    .toString(),
                    response.code(), response.message());
        }
    }

    private Response<MethodDependencyDto> queryForResponse(String className, String methodName) {
        final Call<MethodDependencyDto> apiCall =
                apiClient.getMethodDependencies(className, methodName);
        final Function<Throwable, MethodDependencyFetchException> createException = ex ->
        {
            final Request request = apiCall.request();
            return new MethodDependencyFetchException(
                    request.url().toString(),
                    request.method(),
                    ex
            );
        };

        return Try.of(apiCall::execute).getOrElseThrow(createException);
    }


}

