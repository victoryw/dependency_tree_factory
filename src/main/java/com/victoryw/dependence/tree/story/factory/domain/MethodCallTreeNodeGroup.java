package com.victoryw.dependence.tree.story.factory.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MethodCallTreeNodeGroup {
    private final MethodCallTreeNode splitFrom;
    private List<MethodCallTreeNode> nodes = new ArrayList<>();

    public MethodCallTreeNodeGroup(MethodCallTreeNode splitFrom) {
        this.splitFrom = splitFrom;
    }

    public void attach(MethodCallTreeNode... methodVertexTreeNodes) {
        nodes.addAll(Arrays.asList(methodVertexTreeNodes));
    }

    public List<MethodCallTreeNode> getNodes() {
        return nodes;
    }

    public MethodCallTreeNode getSplitFrom() {
        return splitFrom;
    }
}
