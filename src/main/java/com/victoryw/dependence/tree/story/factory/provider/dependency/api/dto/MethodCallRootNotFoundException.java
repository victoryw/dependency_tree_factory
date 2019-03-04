package com.victoryw.dependence.tree.story.factory.provider.dependency.api.dto;

import com.victoryw.dependence.tree.story.factory.domain.MethodCallDirectedEdge;
import com.victoryw.dependence.tree.story.factory.domain.MethodVertex;

import java.util.List;
import java.util.Set;

public class MethodCallRootNotFoundException extends RuntimeException {
    private final List<MethodVertex> vertexes;
    private final Set<MethodCallDirectedEdge> directedEdgeSet;

    public MethodCallRootNotFoundException(List<MethodVertex> vertexes, Set<MethodCallDirectedEdge> directedEdgeSet) {
        this.vertexes = vertexes;
        this.directedEdgeSet = directedEdgeSet;
    }
}
