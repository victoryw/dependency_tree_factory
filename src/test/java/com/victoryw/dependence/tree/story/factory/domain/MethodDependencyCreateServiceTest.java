package com.victoryw.dependence.tree.story.factory.domain;

import com.scalified.tree.TreeNode;
import com.scalified.tree.multinode.LinkedMultiTreeNode;
import com.victoryw.dependence.tree.story.factory.fixtures.MethodDependencyDtoFixture;
import com.victoryw.dependence.tree.story.factory.neo4j.DependencyNeo4jRepository;
import com.victoryw.dependence.tree.story.factory.provider.dependency.api.MethodDependencyProviderImplement;
import com.victoryw.dependence.tree.story.factory.provider.dependency.api.dto.MethodDependencyDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class MethodDependencyCreateServiceTest {
    MethodDependencyCreateService createService;
    private MethodDependencyDto depsOfRoot;
    private MethodDependencyDto depsOn111;
    private MethodDependencyDto depsOn112;
    private MethodDependencyDto depsOn1111;
    private DependencyNeo4jRepository dependencyRepository;

    @BeforeEach
    void setup() {
        final MethodDependencyProviderImplement methodDependencyProvider =
                new MethodDependencyProviderImplement();
        dependencyRepository = new DependencyNeo4jRepository();
        createService = new MethodDependencyCreateService(methodDependencyProvider,
                dependencyRepository);

        depsOfRoot = MethodDependencyDtoFixture.root();
        depsOn111 = MethodDependencyDtoFixture.deps1111();
        depsOn112 = MethodDependencyDtoFixture.deps112();
        depsOn1111 = MethodDependencyDtoFixture.deps1111();
    }

    @Test
    void should_create_full_dependency_dap() {
        final String className = "className";
        final String methodName = "methodName";
        final Optional<MethodCallTreeNode> result = createService.execute(className, methodName);

        assertThat(result).isPresent();
        final MethodCallTreeNode root = result.get();

        final int size = depsOfRoot.getMethodCallDtos().size() +
                depsOn111.getMethodCallDtos().size() +
                depsOn112.getMethodCallDtos().size() +
                depsOn1111.getMethodCallDtos().size();
        assertThat(root.postOrdered()).hasSize(size);
    }
}
