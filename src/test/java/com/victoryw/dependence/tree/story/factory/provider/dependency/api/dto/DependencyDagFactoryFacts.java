package com.victoryw.dependence.tree.story.factory.provider.dependency.api.dto;

import com.victoryw.dependence.tree.story.factory.Fixture;
import com.victoryw.dependence.tree.story.factory.domain.MethodDag;
import com.victoryw.dependence.tree.story.factory.domain.MethodVertex;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Objects;

class DependencyDagFactoryFacts {

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
        methodDependencyDto.getMethodNodeDtos().forEach(methodNode -> {
            Assertions.assertThat(methodNode).matches(node -> {
                MethodVertex vertex = graph.getVertexById(node.getId());
                return Objects.equals(vertex.getTitle(), node.getTitle());
            });
        });

        methodDependencyDto.getMethodCallDtos().forEach(methodCall -> {
            Assertions.assertThat(methodCall).matches(call -> graph.hasEdge(call.getA(), call.getB()));
        });
    }
}

