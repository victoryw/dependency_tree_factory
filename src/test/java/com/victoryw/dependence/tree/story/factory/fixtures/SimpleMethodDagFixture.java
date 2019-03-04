package com.victoryw.dependence.tree.story.factory.fixtures;

import com.victoryw.dependence.tree.story.factory.domain.MethodDag;
import com.victoryw.dependence.tree.story.factory.domain.MethodVertex;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SimpleMethodDagFixture {

    private static MethodVertex top = new MethodVertex("top", "top");
    private static MethodVertex vertex1 = new MethodVertex("1", "1");
    private static MethodVertex vertex2 = new MethodVertex("2", "2");
    private static MethodVertex vertex3 = new MethodVertex("3", "3");
    private static MethodVertex vertex11 = new MethodVertex("11", "11");

    public static List<MethodVertex> getLeafNodes() {
        return Collections.singletonList(vertex3);
    }

    public static MethodDag sample() {
        final List<MethodVertex> list = Arrays.asList(top, vertex1, vertex2, vertex3, vertex11);
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
