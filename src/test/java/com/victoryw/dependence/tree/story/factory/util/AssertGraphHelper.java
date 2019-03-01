package com.victoryw.dependence.tree.story.factory.util;

import com.victoryw.dependence.tree.story.factory.domain.MethodDag;
import com.victoryw.dependence.tree.story.factory.domain.MethodVertex;
import com.victoryw.dependence.tree.story.factory.provider.dependency.api.dto.MethodCallDto;
import com.victoryw.dependence.tree.story.factory.provider.dependency.api.dto.MethodNodeDto;
import org.assertj.core.api.Assertions;

import java.util.List;
import java.util.Objects;

public class AssertGraphHelper {
    public static void assertGraphNodeTheSameAsSource(MethodDag graph, List<MethodNodeDto> methodNodeDtos) {
        methodNodeDtos.forEach(methodNode -> {
            Assertions.assertThat(methodNode).matches(node -> {
                MethodVertex vertex = graph.getVertexById(node.getId());
                return Objects.equals(vertex.getTitle(), node.getTitle());
            });
        });
    }

    public static void assertGraphEdgeTheSameAsSource(MethodDag graph, List<MethodCallDto> methodCallDtos) {
        methodCallDtos.forEach(methodCall -> {
            Assertions.assertThat(methodCall).matches(call -> graph.hasEdge(call.getA(), call.getB()));
        });
    }
}
