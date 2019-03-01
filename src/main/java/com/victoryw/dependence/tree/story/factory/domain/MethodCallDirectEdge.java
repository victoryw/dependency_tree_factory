package com.victoryw.dependence.tree.story.factory.domain;

public class MethodCallDirectEdge {
    private final MethodVertex fromVertex;
    private final MethodVertex toVertex;

    public MethodCallDirectEdge(MethodVertex fromVertex, MethodVertex toVertex) {
        this.fromVertex = fromVertex;
        this.toVertex = toVertex;
    }
}
