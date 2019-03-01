package com.victoryw.dependence.tree.story.factory.provider.dependency.api;

import com.victoryw.dependence.tree.story.factory.domain.MethodDag;
import com.victoryw.dependence.tree.story.factory.provider.dependency.api.dto.MethodDependencyDto;
import okhttp3.Request;
import org.apache.commons.lang3.StringUtils;
import retrofit2.Call;
import retrofit2.Response;

import javax.swing.text.html.Option;
import java.util.Optional;

public class MethodDependencyProvider {

    private DependencyApiClient apiClient;

    public MethodDependencyProvider() {
        apiClient = DependencyApiClient.create();
    }

    public Optional<MethodDag> getMethodDependencies(String className, String methodName) {
        getPartialMethodDependencies(className, methodName);
        return Optional.empty();
    }


    Optional<MethodDependencyDto> getPartialMethodDependencies(String className, String methodName) {
        final Call<MethodDependencyDto> apiCall = apiClient.getMethodDependencies(className, methodName);
        try {
            final Response<MethodDependencyDto> response = apiCall.execute();
            if ( ! response.isSuccessful()) {
                throw createException(response);
            }

            if(response.body() == null) {
                return Optional.empty();
            }

            return Optional.of(response.body());
        } catch (Exception e) {
            throw new MethodDependencyFetchException("fail to fetch method dependencies",e);
        }
    }

    private MethodDependencyFetchException createException(Response<MethodDependencyDto> response) {
        final Request request = response.raw().request();
        return new MethodDependencyFetchException(StringUtils.join(
                "fail to fetch method dependencies,",
                " url is ", request.url(),
                " method is ", request.method(),
                " error code is ", response.code(),
                " message is ", response.message()));
    }
}

