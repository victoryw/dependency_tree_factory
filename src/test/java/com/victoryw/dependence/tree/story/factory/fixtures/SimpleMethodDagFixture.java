package com.victoryw.dependence.tree.story.factory.fixtures;

import com.sun.tools.javac.util.List;
import com.victoryw.dependence.tree.story.factory.domain.MethodDag;
import com.victoryw.dependence.tree.story.factory.domain.MethodVertex;

import java.util.UUID;

public class SimpleMethodDagFixture {

    private static MethodVertex top = new MethodVertex("top".toString(), "top");
    private static MethodVertex vertex1 = new MethodVertex("1".toString(), "1");
    private static MethodVertex vertex2 = new MethodVertex("2".toString(), "2");
    private static MethodVertex vertex3 = new MethodVertex("3".toString(), "3");
    private static MethodVertex vertex11 = new MethodVertex("11".toString(), "11");

    public static List<MethodVertex> getLeafNodes() {
        return List.of(vertex3);
    }

    public static MethodDag sample() {
        final List<MethodVertex> list = List.of(top, vertex1, vertex2, vertex3, vertex11);
        final MethodDag methodDag = new MethodDag(list, top);
        methodDag.addEdge(top.getNodeId(), vertex1.getNodeId());
        methodDag.addEdge(top.getNodeId(), vertex2.getNodeId());
        methodDag.addEdge(top.getNodeId(), vertex3.getNodeId());
        methodDag.addEdge(vertex1.getNodeId(), vertex11.getNodeId());
        methodDag.addEdge(vertex2.getNodeId(), vertex11.getNodeId());
        methodDag.addEdge(vertex11.getNodeId(), vertex3.getNodeId());
        return methodDag;
    }
}
