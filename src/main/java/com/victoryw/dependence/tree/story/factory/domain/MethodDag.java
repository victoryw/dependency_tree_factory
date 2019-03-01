package com.victoryw.dependence.tree.story.factory.domain;

import org.jgrapht.graph.SimpleDirectedGraph;

import java.util.List;

public class MethodDag  {

    private SimpleDirectedGraph<MethodVertex, MethodCallDirectedEdge> graph;

    public MethodDag(List<MethodVertex> vertexes) {
        graph = new SimpleDirectedGraph<>(MethodCallDirectedEdge.class);
        vertexes.forEach(methodVertex -> graph.addVertex(methodVertex));
    }

    public void addEdge(String fromId, String toId) {
        MethodVertex fromVertex = getVertexById(fromId);
        MethodVertex toVertex = getVertexById(toId);
        MethodCallDirectedEdge edge = new MethodCallDirectedEdge(fromVertex, toVertex);
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

