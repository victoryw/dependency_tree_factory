package com.victoryw.dependence.tree.story.factory.domain;

import java.util.Objects;

public class MethodCallDirectedEdge {
    private final MethodVertex fromVertex;
    private final MethodVertex toVertex;

    public MethodCallDirectedEdge(MethodVertex fromVertex, MethodVertex toVertex) {
        this.fromVertex = fromVertex;
        this.toVertex = toVertex;
    }

    public MethodVertex getFromVertex() {
        return fromVertex;
    }

    public MethodVertex getToVertex() {
        return toVertex;
    }

    @Override
    public boolean equals(Object obj) {
        boolean equalObjects = false;

        if (obj != null && this.getClass() == obj.getClass()) {
            MethodCallDirectedEdge typedObject = (MethodCallDirectedEdge) obj;
            equalObjects = Objects.equals(this.fromVertex, typedObject.fromVertex)
                    && Objects.equals(this.toVertex, typedObject.toVertex);
        }
        return equalObjects;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.fromVertex, this.toVertex);
    }
}
