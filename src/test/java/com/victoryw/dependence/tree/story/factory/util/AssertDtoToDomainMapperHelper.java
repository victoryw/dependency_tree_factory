package com.victoryw.dependence.tree.story.factory.util;

import com.scalified.tree.TreeNode;
import com.victoryw.dependence.tree.story.factory.domain.MethodCallTreeNode;
import com.victoryw.dependence.tree.story.factory.domain.MethodDag;
import com.victoryw.dependence.tree.story.factory.domain.MethodVertex;
import com.victoryw.dependence.tree.story.factory.provider.dependency.api.dto.MethodCallDto;
import com.victoryw.dependence.tree.story.factory.provider.dependency.api.dto.MethodNodeDto;
import org.assertj.core.api.Assertions;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class AssertDtoToDomainMapperHelper {
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

    public static void assertGraphNodeTheSameAsSource2(MethodCallTreeNode tree, List<MethodNodeDto> methodNodeDtos) {
        final List<MethodVertex> MethodVertexInTrees = tree.preOrderFetchData();

        methodNodeDtos.forEach(methodNode -> {
            Assertions.assertThat(methodNode).matches(node ->
                    MethodVertexInTrees.stream().anyMatch(vertex ->
                            vertex.getTitle().equals(node.getTitle())
                                    && vertex.getTitle().endsWith(node.getTitle())));
        });
    }

    public static void assertGraphEdgeTheSameAsSource2(MethodCallTreeNode tree, List<MethodCallDto> methodCallDtos) {
        final Collection<TreeNode<MethodVertex>> treeNodes = tree.preOrdered();
        methodCallDtos.forEach(methodCall -> {
            Assertions.assertThat(methodCall).matches(call -> treeNodes.stream().anyMatch(node -> {
                final MethodCallTreeNode callTreeNode = (MethodCallTreeNode)node;
                return callTreeNode.isSameDataId(methodCall.getA())
                        && callTreeNode.hasSubTree(methodCall.getB());
            }));
        });
    }
}
