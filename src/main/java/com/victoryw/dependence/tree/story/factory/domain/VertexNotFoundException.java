package com.victoryw.dependence.tree.story.factory.domain;

public class VertexNotFoundException extends  RuntimeException {
    private final String vertexId;

    public VertexNotFoundException(String vertexId) {
        super(String.format("%s is not found", vertexId));
        this.vertexId = vertexId;
    }

    public String getVertexId() {
        return vertexId;
    }
}
