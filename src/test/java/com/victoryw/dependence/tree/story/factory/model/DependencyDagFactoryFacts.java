package com.victoryw.dependence.tree.story.factory.model;

import com.victoryw.dependence.tree.story.factory.provider.dependency.api.dto.MethodDependencyDto;
import com.victoryw.dependence.tree.story.factory.domain.MethodCallDagFactory;
import com.victoryw.dependence.tree.story.factory.domain.MethodDag;
import com.victoryw.dependence.tree.story.factory.domain.MethodVertex;
import com.victoryw.dependence.tree.story.factory.Fixture;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

class DependencyDagFactoryFacts {

    private static MethodDependencyDto methodDependencyDto;

    @BeforeAll
    static void setup() {
        methodDependencyDto = Fixture.example();
    }

    @Test
    void should_generator_dag_with_input() {
        MethodCallDagFactory factory = new MethodCallDagFactory();
        MethodDag graph = factory.create(methodDependencyDto);
        methodDependencyDto.getMethodNodeDtos().forEach(methodNode -> {
            assertThat(methodNode).matches(node -> {
                MethodVertex vertex = graph.getVertexById(node.getId());
                return Objects.equals(vertex.getTitle(), node.getTitle());
            });
        });

        methodDependencyDto.getMethodCallDtos().forEach(methodCall -> {
            assertThat(methodCall).matches(call -> graph.hasEdge(call.getA(), call.getB()));
        });
    }
}

