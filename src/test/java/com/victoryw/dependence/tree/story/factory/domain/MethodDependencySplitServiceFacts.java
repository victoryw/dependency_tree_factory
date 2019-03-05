package com.victoryw.dependence.tree.story.factory.domain;

import com.scalified.tree.TreeNode;
import com.victoryw.dependence.tree.story.factory.fixtures.MethodCallTreeNodeFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import static org.assertj.core.api.Assertions.assertThat;

class MethodDependencySplitServiceFacts {

    private MethodDependencySplitService service;

    @BeforeEach
    void setUp() {
        service = new MethodDependencySplitService(2);
    }


    @Test
    void should_split_dependencies_by_depth() {
        MethodCallTreeNode tree = MethodCallTreeNodeFixture.sample();
        Queue<MethodCallTreeNodeGroup> subGroup = service.execute(tree);


        assertThat(subGroup).hasSize(5);

        final MethodCallTreeNodeGroup group111 = subGroup.poll();
        final MethodCallTreeNodeGroup group12 = subGroup.poll();
        final MethodCallTreeNodeGroup group11 = subGroup.poll();
        final MethodCallTreeNodeGroup group1314 = subGroup.poll();
        final MethodCallTreeNodeGroup group1 = subGroup.poll();

        assertThat(group111.getNodes()).hasSize(1);//复杂度大的优先
        final MethodCallTreeNode node111 = group111.getNodes().get(0);
        assertThat(node111.data()).isEqualTo(MethodCallTreeNodeFixture.vertex111);
        assertThat(node111.subtrees()).hasSize(1);
        final TreeNode<MethodVertex> node1111 = node111.subtrees().iterator().next();
        assertThat(node1111.data()).isEqualTo(MethodCallTreeNodeFixture.vertex1111);

        assertThat(group12.getNodes()).hasSize(1);//复杂度大的优先
        final MethodCallTreeNode node12 = group12.getNodes().get(0);
        assertThat(node12.data()).isEqualTo(MethodCallTreeNodeFixture.vertex12);
        assertThat(node12.subtrees()).hasSize(1);
        final TreeNode<MethodVertex> node122 = node12.subtrees().iterator().next();
        assertThat(node122.data()).isEqualTo(MethodCallTreeNodeFixture.vertex122);

        assertThat(group11.getNodes()).hasSize(1);//复杂度大的优先 2次求解
        final MethodCallTreeNode node11 = group11.getNodes().get(0);
        final List<TreeNode<MethodVertex>> node11FlatToDeal = new ArrayList<>(node11.postOrdered());
        assertThat(node11FlatToDeal).hasSize(2);
        assertThat(node11FlatToDeal.get(1).data()).isEqualTo(MethodCallTreeNodeFixture.vertex11);
        assertThat(node11FlatToDeal.get(0).data()).isEqualTo(MethodCallTreeNodeFixture.vertex112);

        assertThat(group1314.getNodes()).hasSize(2);//广度大的优先 2次求解
        final MethodCallTreeNode node13 = group1314.getNodes().get(0);
        final MethodCallTreeNode node14 = group1314.getNodes().get(1);
        assertThat(node13.data()).isEqualTo(MethodCallTreeNodeFixture.vertex13);
        assertThat(node13.subtrees()).hasSize(0);
        assertThat(node14.data()).isEqualTo(MethodCallTreeNodeFixture.vertex14);
        assertThat(node14.subtrees()).hasSize(0);

        assertThat(group1.getNodes()).hasSize(1);//复杂度大的优先 2次求解
        final MethodCallTreeNode node1 = group1.getNodes().get(0);
        final List<TreeNode<MethodVertex>> rootNodes = new ArrayList<>(node1.postOrdered());
        assertThat(rootNodes).hasSize(2);
        assertThat(rootNodes.get(0).data()).isEqualTo(MethodCallTreeNodeFixture.vertex15);
        assertThat(rootNodes.get(1).data()).isEqualTo(MethodCallTreeNodeFixture.vertex1);


    }
}

