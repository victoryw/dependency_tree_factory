package com.victoryw.dependence.tree.story.factory.provider.dependency.api.dto;

import com.victoryw.dependence.tree.story.factory.domain.MethodCallDirectedEdge;
import com.victoryw.dependence.tree.story.factory.domain.MethodCallTreeNode;
import com.victoryw.dependence.tree.story.factory.domain.MethodVertex;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class MethodCallTreeNodeFactory {
    public MethodCallTreeNode create(MethodDependencyDto methodDependencyDto) {
        if (methodDependencyDto.getMethodNodeDtos().isEmpty()
                && methodDependencyDto.getMethodCallDtos().isEmpty()) {
            return null;
        }

        List<MethodVertex> vertexes = methodDependencyDto.getMethodNodeDtos()
                .stream()
                .map((dto) -> new MethodVertex(dto.getId(), dto.getTitle()))
                .collect(Collectors.toList());

        Set<MethodCallDirectedEdge> directedEdgeSet = methodDependencyDto.
                getMethodCallDtos().
                stream().
                map((call) -> createEdge(call, vertexes)).
                collect(Collectors.toSet());


//        reduceCycle(directedEdgeSet);
        MethodCallTreeNode root = createRoot(vertexes, directedEdgeSet);
        fillRoot(root, directedEdgeSet);

        return root;
    }

    private void reduceCycle(Set<MethodCallDirectedEdge> directedEdgeSet){

        Set<Pair<MethodCallDirectedEdge, MethodCallDirectedEdge>> duplicatePairs = new HashSet<>();
        directedEdgeSet.forEach(edge -> {
            final Optional<MethodCallDirectedEdge> duplicate = directedEdgeSet.stream().filter(mark -> mark.getToVertex()
                    .getNodeId()
                    .equals(edge.getFromVertex().getNodeId())).findAny();
            if(duplicate.isPresent()) {
                Pair<MethodCallDirectedEdge, MethodCallDirectedEdge> pair = new ImmutablePair<>(edge, duplicate.get());
                duplicatePairs.add(pair);
            }
        });

        duplicatePairs.forEach(duplicationPair -> {
            final boolean hasOtherCalled = directedEdgeSet.stream().anyMatch(edge ->
                    duplicationPair.getLeft().getToVertex() == edge.getToVertex());
            if(hasOtherCalled) {
                directedEdgeSet.remove(duplicationPair.getRight());
            } else {
                directedEdgeSet.remove(duplicationPair.getLeft());
            }
        });
    }

    private void fillRoot(MethodCallTreeNode root, Set<MethodCallDirectedEdge> directedEdgeSet) {
        final List<MethodCallDirectedEdge> edges = directedEdgeSet.stream().
                filter(edge -> edge.getFromVertex().equals(root.data())).collect(Collectors.toList());
        edges.forEach(root::attachEdge);

        root.subtrees().forEach(subTree -> {
            final MethodCallTreeNode subTree1 = (MethodCallTreeNode) subTree;
            fillRoot(subTree1, directedEdgeSet);
        });
    }

    private MethodCallTreeNode createRoot(List<MethodVertex> vertexes, Set<MethodCallDirectedEdge> directedEdgeSet) {
        final MethodVertex rootMethod = vertexes.stream().filter(methodVertex ->
                directedEdgeSet.stream().noneMatch(edge ->
                        Objects.equals(edge.getToVertex(), methodVertex))).
                findFirst().
                orElseThrow(() -> new MethodCallRootNotFoundException(vertexes, directedEdgeSet));
        return new MethodCallTreeNode(rootMethod);
    }

    private MethodCallDirectedEdge createEdge(MethodCallDto dto, List<MethodVertex> vertexes) {
        String fromId = dto.getA();
        String toId = dto.getB();
        final Optional<MethodVertex> from = getNode(vertexes, fromId);
        final Optional<MethodVertex> to = getNode(vertexes, toId);
        if (!from.isPresent() || !to.isPresent()) {
            throw new MethodDependencyFetchNotExistsMehtodCallException(dto, from.isPresent(), to.isPresent());
        }

        return new MethodCallDirectedEdge(from.get(), to.get());
    }

    private Optional<MethodVertex> getNode(List<MethodVertex> vertexes, String toId) {
        return vertexes.stream()
                .filter(methodVertex -> toId.equals(methodVertex.getNodeId()))
                .findFirst();
    }
}
