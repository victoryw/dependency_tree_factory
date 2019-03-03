package com.victoryw.dependence.tree.story.factory.provider.dependency.api.dto;

import com.victoryw.dependence.tree.story.factory.fixtures.MethodDependencyDtoFixture;
import com.victoryw.dependence.tree.story.factory.domain.MethodDag;
import com.victoryw.dependence.tree.story.factory.util.AssertDtoToDomainMapperHelper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DependencyDagFactoryFacts {

    private static MethodDependencyDto methodDependencyDto;
    private MethodCallDagFactory factory;

    @BeforeAll
    static void setup() {
        methodDependencyDto = MethodDependencyDtoFixture.sample();
    }

    @BeforeEach
    void setUp() {
        factory = new MethodCallDagFactory();
    }

    @Test
    void should_generator_dag_with_input() {
        MethodDag graph = factory.create(methodDependencyDto);
        AssertDtoToDomainMapperHelper.assertGraphNodeTheSameAsSource(graph, methodDependencyDto.getMethodNodeDtos());
        AssertDtoToDomainMapperHelper.assertGraphEdgeTheSameAsSource(graph, methodDependencyDto.getMethodCallDtos());
    }

}

