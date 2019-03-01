package com.victoryw.dependence.tree.story.factory.domain;

import org.jgrapht.graph.SimpleDirectedGraph;

import java.util.List;

public class MethodDag  {

    private SimpleDirectedGraph<MethodVertex, MethodCallDirectEdge> graph;

    public MethodDag(List<MethodVertex> vertexes) {
        graph = new SimpleDirectedGraph<>(MethodCallDirectEdge.class);
        vertexes.forEach(methodVertex -> graph.addVertex(methodVertex));
    }

    public void addEdge(String fromId, String toId) {
        MethodVertex fromVertex = getVertexById(fromId);
        MethodVertex toVertex = getVertexById(toId);
        MethodCallDirectEdge edge = new MethodCallDirectEdge(fromVertex, toVertex);
        graph.addEdge(fromVertex, toVertex, edge);
    }

    public MethodVertex getVertexById(String vertexId) {
        return graph.vertexSet()
                .stream()
                .filter(vertex -> vertex.getNodeId().equals(vertexId))
                .findFirst()
                .orElseThrow(() -> new VertexNotFoundException(vertexId));
    }

    public boolean hasEdge(String from, String to) {
        MethodVertex fromVertex = getVertexById(from);
        MethodVertex toVertex = getVertexById(to);
        return graph.containsEdge(fromVertex, toVertex);
    }
}

