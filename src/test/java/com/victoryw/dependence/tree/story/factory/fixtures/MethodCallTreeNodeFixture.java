package com.victoryw.dependence.tree.story.factory.fixtures;

import com.victoryw.dependence.tree.story.factory.domain.MethodCallDirectedEdge;
import com.victoryw.dependence.tree.story.factory.domain.MethodCallTreeNode;
import com.victoryw.dependence.tree.story.factory.domain.MethodVertex;

import java.util.UUID;

public class MethodCallTreeNodeFixture {
    public static MethodVertex vertex1 = new MethodVertex(UUID.randomUUID().toString(), "className.methodName");
    public static MethodVertex vertex12 = new MethodVertex(UUID.randomUUID().toString(), "className12.methodName12");
    public static MethodVertex vertex111 = new MethodVertex(UUID.randomUUID().toString(), "className111.methodName111");
    public static MethodVertex vertex11 = new MethodVertex(UUID.randomUUID().toString(), "className11.methodName11");
    public static MethodVertex vertex112 = new MethodVertex(UUID.randomUUID().toString(), "className112.methodName112");
    public static MethodVertex vertex122 = new MethodVertex(UUID.randomUUID().toString(), "className122.methodName112");
    public static MethodVertex vertex1111 = new MethodVertex(UUID.randomUUID().toString(), "className1111.methodName1111");
    static MethodCallDirectedEdge edge1_11 = new MethodCallDirectedEdge(vertex1, vertex11);
    static MethodCallDirectedEdge edge1_12 = new MethodCallDirectedEdge(vertex1, vertex12);
    static MethodCallDirectedEdge edge11_112 = new MethodCallDirectedEdge(vertex11, vertex112);
    static MethodCallDirectedEdge edge11_111 = new MethodCallDirectedEdge(vertex11, vertex111);
    static MethodCallDirectedEdge edge111_1111 = new MethodCallDirectedEdge(vertex111, vertex1111);
    static MethodCallDirectedEdge edge12_122 = new MethodCallDirectedEdge(vertex12, vertex122);

    public static MethodCallTreeNode maxDepth() {
        MethodCallTreeNode root = new MethodCallTreeNode(vertex1);
        root.attachEdge(edge1_11);
        root.attachEdge(edge1_12);

        MethodCallTreeNode node11 = (MethodCallTreeNode)root.find(vertex11);
        node11.attachEdge(edge11_112);
        node11.attachEdge(edge11_111);

        MethodCallTreeNode node111 = (MethodCallTreeNode)root.find(vertex111);
        node111.attachEdge(edge111_1111);

        MethodCallTreeNode node12 = (MethodCallTreeNode)root.find(vertex12);
        node12.attachEdge(edge12_122);

        return root;
    }
}
