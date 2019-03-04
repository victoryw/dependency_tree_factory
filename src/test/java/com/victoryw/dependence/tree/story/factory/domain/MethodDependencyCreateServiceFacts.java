package com.victoryw.dependence.tree.story.factory.domain;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.victoryw.dependence.tree.story.factory.fixtures.MethodDependencyDtoFixture;
import com.victoryw.dependence.tree.story.factory.provider.dependency.api.MethodDependencyProviderImplement;
import com.victoryw.dependence.tree.story.factory.provider.dependency.api.dto.MethodDependencyDto;
import com.victoryw.dependence.tree.story.factory.util.GsonExtension;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class MethodDependencyCreateServiceFacts {
    private MethodDependencyCreateService createService;
    private MethodDependencyDto depsOfRoot;
    private MethodDependencyDto depsOn111;
    private MethodDependencyDto depsOn112;
    private MethodDependencyDto depsOn1111;

    private WireMockServer wireMockServer;

    @BeforeEach
    void setup() {
        final MethodDependencyProviderImplement methodDependencyProvider =
                new MethodDependencyProviderImplement();
        createService = new MethodDependencyCreateService(methodDependencyProvider);

        wireMockServer = new WireMockServer(WireMockConfiguration.options().port(8900));
        wireMockServer.start();

        depsOfRoot = MethodDependencyDtoFixture.root();
        depsOn111 = MethodDependencyDtoFixture.deps111();
        depsOn112 = MethodDependencyDtoFixture.deps112();
        depsOn1111 = MethodDependencyDtoFixture.deps1111();
    }

    @AfterEach
    void tearDown() {
        wireMockServer.stop();
    }

    @Test
    void should_create_full_dependency_dap() {
        stubForDepApi();
//        http://10.127.151.14:8300/method/com.ebao.life.esb.listener.ecommerce.NewbizApproValidator/validatePolicyUpdate/callees
        final String className = "className";
        final String methodName = "methodName";
        final Optional<MethodCallTreeNode> result = createService.execute(className, methodName);

        assertThat(result).isPresent();
        final MethodCallTreeNode root = result.get();

        final int subTreeCount = depsOfRoot.getRelationCount() +
                depsOn111.getRelationCount() +
                depsOn112.getRelationCount() +
                depsOn1111.getRelationCount();
        int rootNums = 1;
        int treeNodeNumber = subTreeCount + rootNums;
        assertThat(root.postOrdered()).hasSize(treeNodeNumber);
    }

    @Test
    @Disabled
    void should_actual_test() {
        final String className = "com.ebao.life.esb.listener.ecommerce.ECommerceImpl";
        final String methodName = "newbizAppro";
        final Optional<MethodCallTreeNode> result = createService.execute(className, methodName);
    }

    private void stubForDepApi() {
        stub("className", "methodName", depsOfRoot);
        stub("className111", "methodName111", depsOn111);
        stub("className112", "methodName112", depsOn112);
        stub("className1111", "methodName1111", depsOn1111);
    }

    private void stub(String className, String methodName, MethodDependencyDto depsOfRoot) {

        final String methodUrl = String.format("/method/%s/%s/callees", className, methodName);
        wireMockServer.stubFor(WireMock.get(WireMock.urlEqualTo(methodUrl))
                .willReturn(WireMock.aResponse().withBody(GsonExtension.toJson(depsOfRoot))));
    }
}
