package com.victoryw.dependence.tree.story.factory.domain;

public class MethodCallDirectedEdge {
    private final MethodVertex fromVertex;
    private final MethodVertex toVertex;

    public MethodCallDirectedEdge(MethodVertex fromVertex, MethodVertex toVertex) {
        this.fromVertex = fromVertex;
        this.toVertex = toVertex;
    }
}
