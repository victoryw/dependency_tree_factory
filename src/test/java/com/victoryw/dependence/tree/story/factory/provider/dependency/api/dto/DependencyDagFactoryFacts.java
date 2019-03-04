package com.victoryw.dependence.tree.story.factory.provider.dependency.api.dto;

import com.victoryw.dependence.tree.story.factory.domain.MethodCallTreeNode;
import com.victoryw.dependence.tree.story.factory.fixtures.MethodDependencyDtoFixture;
import com.victoryw.dependence.tree.story.factory.util.AssertDtoToDomainMapperHelper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DependencyDagFactoryFacts {

    private static MethodDependencyDto methodDependencyDto;
    private MethodCallTreeNodeFactory factory;

    @BeforeAll
    static void setup() {
        methodDependencyDto = MethodDependencyDtoFixture.root();
    }

    @BeforeEach
    void setUp() {
        factory = new MethodCallTreeNodeFactory();
    }


    @Test
    void should_generator_tree_with_input() {
        final MethodCallTreeNode root = factory.create(methodDependencyDto);
        AssertDtoToDomainMapperHelper.assertGraphNodeTheSameAsSource2(root, methodDependencyDto.getMethodNodeDtos());
        AssertDtoToDomainMapperHelper.assertGraphEdgeTheSameAsSource2(root, methodDependencyDto.getMethodCallDtos());
    }

}

