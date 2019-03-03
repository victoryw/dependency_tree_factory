package com.victoryw.dependence.tree.story.factory.domain;

import java.util.Objects;

public class MethodVertex {
    private String nodeId;
    private String title;

    public String getNodeId() {
        return nodeId;
    }

    public MethodVertex(String nodeId, String title) {
        this.nodeId = nodeId;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public boolean equals(Object obj) {
        boolean equalObjects = false;

        if (obj != null && this.getClass() == obj.getClass()) {
            MethodVertex typedObject = (MethodVertex) obj;
            equalObjects = Objects.equals(this.getNodeId(), typedObject.getNodeId());
        }
        return equalObjects;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.getNodeId());
    }
}


