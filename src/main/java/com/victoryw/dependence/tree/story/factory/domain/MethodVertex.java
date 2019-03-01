package com.victoryw.dependence.tree.story.factory.domain;

public class MethodVertex {
    private String NodeId;
    private String title;

    public String getNodeId() {
        return NodeId;
    }

    public MethodVertex(String nodeId, String title) {
        NodeId = nodeId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}


