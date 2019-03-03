package com.victoryw.dependence.tree.story.factory.fixtures;

import com.sun.tools.javac.util.List;
import com.victoryw.dependence.tree.story.factory.domain.MethodDag;
import com.victoryw.dependence.tree.story.factory.domain.MethodVertex;

import java.util.UUID;

public class MethodDagFixture {
    public static MethodDag sample() {
        MethodVertex top = new MethodVertex(UUID.randomUUID().toString(), "top");
        MethodVertex vertex1 = new MethodVertex(UUID.randomUUID().toString(), "1");
        MethodVertex vertex2 = new MethodVertex(UUID.randomUUID().toString(), "2");
        MethodVertex vertex3 = new MethodVertex(UUID.randomUUID().toString(), "3");
        MethodVertex vertex11 = new MethodVertex(UUID.randomUUID().toString(), "11");
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
