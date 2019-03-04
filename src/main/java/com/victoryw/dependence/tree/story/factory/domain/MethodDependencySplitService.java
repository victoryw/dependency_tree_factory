package com.victoryw.dependence.tree.story.factory.domain;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

class MethodDependencySplitService {

    private final int maxDepthLimit;

    MethodDependencySplitService(int maxDepthLimit) {

        this.maxDepthLimit = maxDepthLimit;
    }

    Queue<MethodCallTreeNodeGroup> execute(MethodCallTreeNode tree) {
        Queue<MethodCallTreeNodeGroup> methodCallTreeNodes = new LinkedList<>();

        splitNode(tree, methodCallTreeNodes);

        return methodCallTreeNodes;
    }

    private void splitNode(MethodCallTreeNode node, Queue<MethodCallTreeNodeGroup> methodCallTreeNodes) {
        final int depth = node.getDepth();
        if(depth <= maxDepthLimit) {
            methodCallTreeNodes.add(new MethodCallTreeNodeGroup(node));
        }
        else {
            reduceNodeDeps(node, methodCallTreeNodes);
            splitNode(node, methodCallTreeNodes);

        }
    }

    private void reduceNodeDeps(MethodCallTreeNode node, Queue<MethodCallTreeNodeGroup> methodCallTreeNodes) {
        if(node.getDepth() <= maxDepthLimit) {
            if(node.parent() != null) {
                node.parent().remove(node);
            }
            methodCallTreeNodes.add(new MethodCallTreeNodeGroup(node));
            return;
        }
        final List<MethodCallTreeNode> exceeds = node.subtrees()
                .stream().map(tree ->(MethodCallTreeNode)tree)
                .filter(leaf -> leaf.getDepth() >= maxDepthLimit)
                .collect(Collectors.toList());
        if(!exceeds.isEmpty()) {
            exceeds.forEach(node1 -> reduceNodeDeps(node1, methodCallTreeNodes));
            return;
        }

//        final List<MethodCallTreeNode> sortedLeaf = node.getLeftNodes()
//                .stream()
//                .sorted(Comparator.comparingInt(MethodCallTreeNode::getDepth))
//                .collect(Collectors.toList());
    }
}
