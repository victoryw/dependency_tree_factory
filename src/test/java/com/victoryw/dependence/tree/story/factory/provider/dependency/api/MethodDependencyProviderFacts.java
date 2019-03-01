package com.victoryw.dependence.tree.story.factory.provider.dependency.api;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.google.gson.Gson;
import com.victoryw.dependence.tree.story.factory.Fixture;
import com.victoryw.dependence.tree.story.factory.domain.MethodDag;
import com.victoryw.dependence.tree.story.factory.provider.dependency.api.dto.MethodDependencyDto;
import com.victoryw.dependence.tree.story.factory.util.AssertGraphHelper;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

class MethodDependencyProviderFacts {

    private MethodDependencyProvider provider;
    private WireMockServer wireMockServer;
    private static MethodDependencyDto source;
    private String methodReturnJsonBody;

    @BeforeEach
    void setUp() {
        provider = new MethodDependencyProvider();
        wireMockServer = new WireMockServer(WireMockConfiguration.options().port(8900));
        wireMockServer.start();

        source = Fixture.example();

        Gson gson = new Gson();
        methodReturnJsonBody = gson.toJson(source);
    }

    @AfterEach
    void tearDown() {
        wireMockServer.stop();
    }

    @Test
    void should_fetch_method_dependency() {

        final String className = "com.ebao.life.claim.infrastructure.expose.MedicardCaseTransPolicyDAO";
        final String methodName = "batchcreate";

        final String methodUrl = String.format("/method/%s/%s/callees", className, methodName);

        wireMockServer.stubFor(WireMock.get(WireMock.urlEqualTo(methodUrl))
                .willReturn(WireMock.aResponse().withBody(methodReturnJsonBody)));

        final Optional<MethodDependencyDto> methodDepDto = provider.
                getPartialMethodDependencies(className, methodName);

        Assertions.assertThat(methodDepDto).isPresent();
        final MethodDependencyDto methodDependencyDto = methodDepDto.get();
        Assertions.assertThat(methodDependencyDto.getMethodNodeDtos()).hasSameSizeAs(source.getMethodNodeDtos());
        Assertions.assertThat(methodDependencyDto.getMethodCallDtos()).hasSameSizeAs(source.getMethodCallDtos());
    }


    @Test
    void should_fetch_method_dependency_to_model() {

        final String className = "com.ebao.life.claim.infrastructure.expose.MedicardCaseTransPolicyDAO";
        final String methodName = "batchcreate";

        final String methodUrl = String.format("/method/%s/%s/callees", className, methodName);

        wireMockServer.stubFor(WireMock.get(WireMock.urlEqualTo(methodUrl))
                .willReturn(WireMock.aResponse().withBody(methodReturnJsonBody)));

        final Optional<MethodDag> methodDepsResult = provider.getMethodDependencies(className, methodName);

        Assertions.assertThat(methodDepsResult).isPresent();
        final MethodDag methodDepsDag = methodDepsResult.get();
        AssertGraphHelper.assertGraphNodeTheSameAsSource(methodDepsDag, source.getMethodNodeDtos());
        AssertGraphHelper.assertGraphEdgeTheSameAsSource(methodDepsDag, source.getMethodCallDtos());
    }

}


