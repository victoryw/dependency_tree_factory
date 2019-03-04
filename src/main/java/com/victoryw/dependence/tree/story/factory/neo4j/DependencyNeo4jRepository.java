package com.victoryw.dependence.tree.story.factory.neo4j;

import com.victoryw.dependence.tree.story.factory.domain.DependencyRepository;
import com.victoryw.dependence.tree.story.factory.domain.MethodDag;
import com.victoryw.dependence.tree.story.factory.domain.MethodVertex;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.Value;
import org.neo4j.driver.v1.types.Node;
import org.neo4j.driver.v1.types.Relationship;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.neo4j.driver.v1.Values.parameters;

public class DependencyNeo4jRepository implements DependencyRepository {

    private Driver driver;
    private static String nodeIdPropertyName = "id";
    private static String nodeTitlePropertyName = "title";
    private static String relationshipFromPropertyName = "from";
    private static String relationshipToPropertyName = "to";

    public DependencyNeo4jRepository() {
        driver = DriverProvider.createDriver();
    }

    @Override
    public void save(MethodDag justNewDag) {
        String methodLabel = ":Method";
        final String statementTemplate = "CREATE (" + methodLabel + " {" + nodeIdPropertyName + ": $id, " + nodeTitlePropertyName + ":$title})";
        try (final Session session = driver.session()) {
            justNewDag.getVertexes().forEach(vertex -> {
                session.run(statementTemplate,
                        parameters(
                                nodeIdPropertyName, vertex.getNodeId(),
                                nodeTitlePropertyName, vertex.getTitle()));
            });

            justNewDag.getEdges().forEach(edge -> {
                String createRelationTemplateCommand = "MATCH (from:Method), (to:Method) WHERE from.id = $fromId AND to.id = $toId " +
                        "CREATE (from)-[:Callee {" + relationshipFromPropertyName + ":$fromId, " + relationshipToPropertyName + ":$toId}]->(to)";
                session.run(createRelationTemplateCommand,
                        parameters("fromId", edge.getFromVertex().getNodeId()
                                , "toId", edge.getToVertex().getNodeId()));
            });
        }


    }

    @Override
    public Optional<MethodDag> load(MethodVertex nodeId) {
        String loadAllNodeQuery = "MATCH (root:Method {id: $nodeId})-[r*]->(child:Method) " +
                "UNWIND r as flat " +
                "RETURN startNode(flat) AS from, flat AS edge, endNode(flat) AS to";

        MethodDag dag = new MethodDag();
        dag.setOriginId(nodeId.getNodeId());
        try (Session session = driver.session()) {
            final StatementResult result = session.run(loadAllNodeQuery,
                    parameters("nodeId", nodeId.getNodeId()));

            result.stream().forEach(record -> {
                final MethodVertex from = convertToVertex(record, "from");
                final MethodVertex to = convertToVertex(record, "to");
                dag.addVertex(from);
                dag.addVertex(to);

                final Value edge = record.get("edge");
                final Relationship relationship = edge.asRelationship();
                final String fromId = relationship.get(relationshipFromPropertyName).asString();
                final String toId = relationship.get(relationshipToPropertyName).asString();
                dag.addEdge(fromId, toId);
            });
        }

        if (!dag.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(dag);

    }

    @Override
    public List<MethodVertex> fetchLeafNodes(String originId) {
        String fetchLeafNodesStr = "match (n)<-[r*]-(top:Method {id:$originId}) " +
                "where NOT (n)-->() " +
                "return distinct n;";

        try (Session session = driver.session()) {
            final StatementResult result = session.run(fetchLeafNodesStr,
                    parameters("originId", originId));
            return result.stream()
                    .map((record) -> convertToVertex(record, "n"))
                    .collect(Collectors.toList());
        }
    }

    @Override
    public Optional<MethodVertex> loadVertexByTitle(String title) {
        String queryCommand = "MATCH (root:Method {title: $title}) RETURN root";

        try (Session session = driver.session()) {
            final StatementResult vertexResult = session.run(queryCommand,
                    parameters("title", title));
            if (! vertexResult.hasNext()) {
                return Optional.empty();
            }
            final Record single = vertexResult.single();
            return Optional.of(convertToVertex(single, "root"));
        }
    }

    private MethodVertex convertToVertex(Record record, String key) {
        final Value from = record.get(key);
        final Node node = from.asNode();
        final String id = node.get(nodeIdPropertyName).asString();
        final String title = node.get(nodeTitlePropertyName).asString();
        return new MethodVertex(id, title);
    }
}
