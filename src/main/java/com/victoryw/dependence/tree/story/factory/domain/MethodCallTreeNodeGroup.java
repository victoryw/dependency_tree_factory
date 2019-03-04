package com.victoryw.dependence.tree.story.factory.domain;

import com.scalified.tree.TreeNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MethodCallTreeNodeGroup {
    private List<MethodCallTreeNode> nodes = new ArrayList<>();

    public MethodCallTreeNodeGroup(MethodCallTreeNode... methodVertexTreeNodes) {
        nodes.addAll(Arrays.asList(methodVertexTreeNodes));
    }

    public List<MethodCallTreeNode> getNodes() {
        return nodes;
    }
}
