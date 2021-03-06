package com.victoryw.dependence.tree.story.factory.domain;

import com.scalified.tree.TreeNode;
import com.scalified.tree.multinode.LinkedMultiTreeNode;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class MethodCallTreeNode extends LinkedMultiTreeNode<MethodVertex> {
    private final Set<MethodCallDirectedEdge> callDirectedEdge;

    public MethodCallTreeNode(MethodVertex data) {
        super(data);
        this.callDirectedEdge = new HashSet<>();
    }

    public boolean hasSubTree(String nodeId) {
        return subtrees().stream().map(sub -> (MethodCallTreeNode)sub).anyMatch(sub -> sub.data()
                .getNodeId()
                .equals(nodeId));
    }

    public boolean isSameDataId(String nodeId) {
        return data().getNodeId().equals(nodeId);
    }

    public List<MethodVertex> preOrderFetchData() {
        return preOrdered().
                stream().
                map(TreeNode::data).
                distinct().
                collect(Collectors.toList());
    }

    public void attachEdge(MethodCallDirectedEdge callDirectedEdge) {
        this.callDirectedEdge.add(callDirectedEdge);
        this.add(new MethodCallTreeNode(callDirectedEdge.getToVertex()));
    }

    public Optional<MethodCallTreeNode> findByRelation(MethodCallDirectedEdge targetRelation) {
        if (this.callDirectedEdge.contains(targetRelation)) {
            return Optional.of(this);
        }

        if(this.isLeaf()) {
            return Optional.empty();
        }

        return this.subtrees().stream().
                map((subtree) -> {
                    MethodCallTreeNode node = (MethodCallTreeNode) subtree;
                    return node.findByRelation(targetRelation);
                }).
                filter(Optional::isPresent).
                map(Optional::get).findFirst();
    }

    public List<MethodCallTreeNode> getLeftNodes() {
        if(this.isLeaf()) {
            return Collections.singletonList(this);
        }

        return this.subtrees().stream().map(subTree -> {
            MethodCallTreeNode subNode = (MethodCallTreeNode) subTree;
            return subNode.getLeftNodes();
        }).flatMap(Collection::stream).collect(Collectors.toList());
    }

    public int getDepth() throws StackOverflowError {
        if(this.isLeaf()) {
            return 1;
        }

        final int childRenDeps = this.subtrees()
                .stream()
                .map(node -> (MethodCallTreeNode) node)
                .mapToInt(MethodCallTreeNode::getDepth)
                .sum();
        if(this.isRoot()) {
            return childRenDeps;
        }
        return childRenDeps + 1;
    }
}
