package com.victoryw.dependence.tree.story.factory.domain;

import com.scalified.tree.TreeNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

public class MethodDependencySplitService {

    private final int maxDepthLimit;

    public MethodDependencySplitService(int maxDepthLimit) {

        this.maxDepthLimit = maxDepthLimit;
    }

    public Queue<MethodCallTreeNodeGroup> execute(MethodCallTreeNode tree) {
        Queue<MethodCallTreeNodeGroup> methodCallTreeNodes = new LinkedList<>();

        splitRoot(tree, methodCallTreeNodes);

        return methodCallTreeNodes;
    }

    private void splitRoot(MethodCallTreeNode node, Queue<MethodCallTreeNodeGroup> methodCallTreeNodes) {
        final int depth = node.getDepth();
        if (depth <= maxDepthLimit) {
            //use the root as target
            final MethodCallTreeNodeGroup rootGroup = new MethodCallTreeNodeGroup(node);
            rootGroup.attach(node);
            methodCallTreeNodes.add(rootGroup);
        } else {
            reduceNodeDeps(node, methodCallTreeNodes);
            splitRoot(node, methodCallTreeNodes);

        }
    }

    private void reduceNodeDeps(MethodCallTreeNode node, Queue<MethodCallTreeNodeGroup> methodCallTreeNodes) {
        //
        final boolean isSelfFitLimit = node.getDepth() <= maxDepthLimit;
        if (isSelfFitLimit) {
            attachGroup(methodCallTreeNodes, node);
            return;
        }
        final List<MethodCallTreeNode> exceeds = node.subtrees()
                .stream().map(tree -> (MethodCallTreeNode) tree)
                .filter(leaf -> leaf.getDepth() >= maxDepthLimit)
                .collect(Collectors.toList());
        final boolean existExceedLimit = exceeds.isEmpty();
        if (!existExceedLimit) {
            exceeds.forEach(node1 -> reduceNodeDeps(node1, methodCallTreeNodes));
            return;
        }

        //按照广度分组，直到省下的节点+1 <= limit
        final LinkedList<MethodCallTreeNode> breadthLeaves = node.subtrees()
                .stream()
                .map((treeNode) -> (MethodCallTreeNode)treeNode)
                .sorted(Comparator.comparingInt(MethodCallTreeNode::getDepth))
                .collect(Collectors.toCollection(LinkedList::new));

        List<MethodCallTreeNode> parks = new ArrayList<>();
        int currentTotalDeps = 0;
        while (true) {
            final MethodCallTreeNode toSplitNode = breadthLeaves.poll();
            if (toSplitNode == null) {
                return;
            }
            final int nextTotalDeps = currentTotalDeps + toSplitNode.getDepth();
            //如果加入下一个阶段的复杂度超限了，则当前已经分配的为一组，可能存在工作量不满足的
            if (nextTotalDeps == maxDepthLimit) {
                parks.add(toSplitNode);
                attachGroup(methodCallTreeNodes, parks.toArray(new MethodCallTreeNode[0]));
                parks.clear();
                currentTotalDeps = 0;
            } else if (nextTotalDeps > maxDepthLimit) {
                attachGroup(methodCallTreeNodes, parks.toArray(new MethodCallTreeNode[0]));
                parks.clear();
                currentTotalDeps = 0;
                parks.add(toSplitNode);
            } else {
                parks.add(toSplitNode);
                currentTotalDeps = currentTotalDeps + toSplitNode.getDepth();
            }

        }

    }

    private void attachGroup(Queue<MethodCallTreeNodeGroup> methodCallTreeNodes, MethodCallTreeNode... nodes) {
        final List<MethodCallTreeNode> toDeals = Arrays.asList(nodes);
        //all the nodes same as parent
        final MethodCallTreeNode parent = (MethodCallTreeNode)toDeals.get(0).parent();
        final MethodCallTreeNodeGroup groupSelf = new MethodCallTreeNodeGroup(parent);
        toDeals.forEach(node -> {
            if (node.parent() != null) {
                node.parent().remove(node);
            }
            groupSelf.attach(node);
        });
        methodCallTreeNodes.add(groupSelf);
    }
}
