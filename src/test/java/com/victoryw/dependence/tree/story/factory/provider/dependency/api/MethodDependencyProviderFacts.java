package com.victoryw.dependence.tree.story.factory.provider.dependency.api;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.victoryw.dependence.tree.story.factory.domain.MethodCallTreeNode;
import com.victoryw.dependence.tree.story.factory.fixtures.MethodDependencyDtoFixture;
import com.victoryw.dependence.tree.story.factory.provider.dependency.api.dto.MethodDependencyDto;
import com.victoryw.dependence.tree.story.factory.util.AssertDtoToDomainMapperHelper;
import com.victoryw.dependence.tree.story.factory.util.GsonExtension;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

class MethodDependencyProviderFacts {

    private MethodDependencyProviderImplement provider;
    private WireMockServer wireMockServer;
    private static MethodDependencyDto source;
    private String methodReturnJsonBody;

    @BeforeEach
    void setUp() {
        provider = new MethodDependencyProviderImplement();
        wireMockServer = new WireMockServer(WireMockConfiguration.options().port(8900));
        wireMockServer.start();

        source = MethodDependencyDtoFixture.root();
        this.methodReturnJsonBody = GsonExtension.toJson(MethodDependencyProviderFacts.source);
    }

    @AfterEach
    void tearDown() {
        wireMockServer.stop();
    }


    @Test
    void should_fetch_method_dependency_to_tree() {

        final String className = "className";
        final String methodName = "methodName";

        final String methodUrl = String.format("/method/%s/%s/callees", className, methodName);

        wireMockServer.stubFor(WireMock.get(WireMock.urlEqualTo(methodUrl))
                .willReturn(WireMock.aResponse().withBody(methodReturnJsonBody)));

        final Optional<MethodCallTreeNode> methodDepsResult = provider.getMethodDependencies(className, methodName);

        Assertions.assertThat(methodDepsResult).isPresent();
        final MethodCallTreeNode tree = methodDepsResult.get();
        AssertDtoToDomainMapperHelper.assertGraphNodeTheSameAsSource2(tree, source.getMethodNodeDtos());
        AssertDtoToDomainMapperHelper.assertGraphEdgeTheSameAsSource2(tree, source.getMethodCallDtos());
    }

}


