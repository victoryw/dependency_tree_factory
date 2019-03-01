package com.victoryw.dependence.tree.story.factory.model;

import com.sun.tools.javac.util.List;
import com.victoryw.dependence.tree.story.factory.dependence.service.dto.MethodCallDto;
import com.victoryw.dependence.tree.story.factory.dependence.service.dto.MethodDagDto;
import com.victoryw.dependence.tree.story.factory.dependence.service.dto.MethodNodeDto;
import com.victoryw.dependence.tree.story.factory.domain.MethodCallDagFactory;
import com.victoryw.dependence.tree.story.factory.domain.MethodDag;
import com.victoryw.dependence.tree.story.factory.domain.MethodVertex;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Objects;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class DependencyDagFactoryFacts {

    private static MethodDagDto methodDagDto;

    @BeforeAll
    static void setup() {
        methodDagDto = create();
    }

    @Test
    void should_generator_dag_with_input() {
        MethodCallDagFactory factory = new MethodCallDagFactory();
        MethodDag graph = factory.create(methodDagDto);
        methodDagDto.getMethodNodeDtos().forEach( methodNode -> {
            assertThat(methodNode).matches(node -> {
                MethodVertex vertex = graph.getVertexById(node.getId());
                return Objects.equals(vertex.getTitle(), node.getTitle());
            });
        });

        methodDagDto.getMethodCallDtos().forEach(methodCall -> {
            assertThat(methodCall).matches(call -> graph.hasEdge(call.getA(), call.getB()));
        });
    }

    static MethodDagDto create() {
        final MethodNodeDto node1 = MethodNodeDto.createMethodNode(UUID.randomUUID().toString());
        final MethodNodeDto node11 = MethodNodeDto.createMethodNode(UUID.randomUUID().toString());
        final MethodNodeDto node12 = MethodNodeDto.createMethodNode(UUID.randomUUID().toString());
        final MethodNodeDto node111 = MethodNodeDto.createMethodNode(UUID.randomUUID().toString());
        final MethodNodeDto node112 = MethodNodeDto.createMethodNode(UUID.randomUUID().toString());
        final MethodCallDto edge11 = new MethodCallDto(node1.getId(), node11.getId());
        final MethodCallDto edge12 = new MethodCallDto(node1.getId(), node12.getId());
        final MethodCallDto edge111 = new MethodCallDto(node11.getId(), node111.getId());
        final MethodCallDto edge112 = new MethodCallDto(node11.getId(), node112.getId());
        final MethodCallDto edge122 = new MethodCallDto(node12.getId(), node112.getId());
        return new MethodDagDto().
                withNodes(List.of(node1, node11, node12, node111, node112)).
                withEdges(List.of(edge11, edge12, edge111, edge112, edge122));
    }
}

