package com.victoryw.dependence.tree.story.factory.provider.dependency.api;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.google.gson.Gson;
import com.victoryw.dependence.tree.story.factory.Fixture;
import com.victoryw.dependence.tree.story.factory.provider.dependency.api.dto.MethodDependencyDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

class MethodDependencyProviderFacts {

    private MethodDependencyProvider provider;
    private WireMockServer wireMockServer;
    private static MethodDependencyDto source;

    @BeforeEach
    void setUp() {
        provider = new MethodDependencyProvider();
        wireMockServer = new WireMockServer(WireMockConfiguration.options().port(8900));
        wireMockServer.start();
        source = Fixture.example();
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

        Gson gson = new Gson();
        final String body = gson.toJson(source);
        wireMockServer.stubFor(WireMock.get(WireMock.urlEqualTo(methodUrl))
                .willReturn(WireMock.aResponse().withBody(body)));

        final Optional<MethodDependencyDto> methodDepDto = provider.
                getPartialMethodDependencies(className, methodName);

        Assertions.assertThat(methodDepDto.isPresent()).isTrue();
        final MethodDependencyDto methodDependencyDto = methodDepDto.get();
        Assertions.assertThat(methodDependencyDto.getMethodNodeDtos()).hasSameSizeAs(source.getMethodNodeDtos());
        Assertions.assertThat(methodDependencyDto.getMethodCallDtos()).hasSameSizeAs(source.getMethodCallDtos());
    }

}


