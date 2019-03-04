package com.victoryw.dependence.tree.story.factory.domain;

import org.apache.commons.collections.CollectionUtils;
import org.jgrapht.graph.SimpleDirectedGraph;

import java.util.List;
import java.util.Objects;
import java.util.Set;
@Deprecated
public class MethodDag  {

    private SimpleDirectedGraph<MethodVertex, MethodCallDirectedEdge> graph;
    private String originId;

    public MethodDag(List<MethodVertex> vertexes, MethodVertex origin) {
        this();
        this.originId = origin.getNodeId();
        vertexes.forEach(methodVertex -> graph.addVertex(methodVertex));
    }

    public MethodDag() {
        graph = new SimpleDirectedGraph<>(MethodCallDirectedEdge.class);
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

    public void setOriginId(String originId) {
        this.originId = originId;
    }

    public MethodVertex getOrigin() {
        return getVertexById(this.originId);
    }

    public Set<MethodVertex> getVertexes() {
        return graph.vertexSet();
    }

    public Set<MethodCallDirectedEdge> getEdges() {
        return graph.edgeSet();
    }

    public void addVertex(MethodVertex vertex) {
        if (!graph.containsVertex(vertex)) {
            graph.addVertex(vertex);
        }
    }

    public boolean isEmpty() {
        return !graph.vertexSet().isEmpty();
    }


    @Override
    public int hashCode() {
        return Objects.hash(this.getEdges(), this.getVertexes());
    }

    @Override
    public boolean equals(Object obj) {
        boolean equalObjects = false;

        if (obj != null && this.getClass() == obj.getClass()) {
            MethodDag typedObject = (MethodDag) obj;
            equalObjects = CollectionUtils.isEqualCollection(this.getVertexes(), typedObject.getVertexes()) &&
                    CollectionUtils.isEqualCollection(this.getEdges(), typedObject.getEdges());
        }
        return equalObjects;
    }
}

