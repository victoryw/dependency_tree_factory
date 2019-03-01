package com.victoryw.dependence.tree.story.factory.provider.dependency.api.dto;

import com.victoryw.dependence.tree.story.factory.Fixture;
import com.victoryw.dependence.tree.story.factory.domain.MethodDag;
import com.victoryw.dependence.tree.story.factory.util.AssertGraphHelper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DependencyDagFactoryFacts {

    private static MethodDependencyDto methodDependencyDto;
    private MethodCallDagFactory factory;

    @BeforeAll
    static void setup() {
        methodDependencyDto = Fixture.example();
    }

    @BeforeEach
    void setUp() {
        factory = new MethodCallDagFactory();
    }

    @Test
    void should_generator_dag_with_input() {
        MethodDag graph = factory.create(methodDependencyDto);
        AssertGraphHelper.assertGraphNodeTheSameAsSource(graph, methodDependencyDto.getMethodNodeDtos());
        AssertGraphHelper.assertGraphEdgeTheSameAsSource(graph, methodDependencyDto.getMethodCallDtos());
    }

}

