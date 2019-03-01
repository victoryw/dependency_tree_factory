package com.victoryw.dependence.tree.story.factory.provider.dependency.api;

import com.victoryw.dependence.tree.story.factory.domain.MethodDag;
import com.victoryw.dependence.tree.story.factory.provider.dependency.api.dto.MethodCallDagFactory;
import com.victoryw.dependence.tree.story.factory.provider.dependency.api.dto.MethodDependencyDto;
import io.vavr.control.Try;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Response;

import java.util.Optional;

class MethodDependencyProvider {

    private final DependencyApiClient apiClient;
    private final MethodCallDagFactory factory;

    MethodDependencyProvider() {
        apiClient = DependencyApiClient.create();
        factory = new MethodCallDagFactory();
    }

    Optional<MethodDag> getMethodDependencies(String className, String methodName) {
        return
                getPartialMethodDependencies(className, methodName).
                        map(factory::create);
    }


    Optional<MethodDependencyDto> getPartialMethodDependencies(String className, String methodName) {
        final Call<MethodDependencyDto> apiCall = apiClient.getMethodDependencies(className, methodName);
        final Response<MethodDependencyDto> response =
                Try.of(apiCall::execute).
                        getOrElseThrow(ex ->
                                {
                                    final Request request = apiCall.request();
                                    return new MethodDependencyFetchException(
                                            request.url().toString(),
                                            request.method(),
                                            ex
                                    );
                                }
                        );

        if (!response.isSuccessful()) {
            final Request request = response.raw().request();
            throw new MethodDependencyFetchException(request.url()
                    .toString(), request.method(),
                    response.code(), response.message());
        }

        if (response.body() == null) {
            return Optional.empty();
        }

        return Optional.of(response.body());
    }

}

