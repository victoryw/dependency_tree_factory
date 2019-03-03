package com.victoryw.dependence.tree.story.factory.neo4j;

import com.victoryw.dependence.tree.story.factory.domain.MethodDag;
import com.victoryw.dependence.tree.story.factory.domain.MethodVertex;
import com.victoryw.dependence.tree.story.factory.fixtures.SimpleMethodDagFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.Session;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class DependencyNeo4jRepositoryTest {

    private DependencyNeo4jRepository respository;

    @BeforeEach
    void setUp() {
        cleanDatabase();
        respository = new DependencyNeo4jRepository();
    }

    private void cleanDatabase() {
        final Driver driver = DriverProvider.createDriver();
        try (Session session = driver.session()) {
            session.run("MATCH ()-[r]-() DELETE r");
            session.run("MATCH (n) DELETE n");
        }
    }

    @Test
    void should_create_dependencies_dap() {
        MethodDag dag = SimpleMethodDagFixture.sample();
        respository.save(dag);

        Optional<MethodDag> reloadDag = respository.load(dag.getOriginId());
        assertThat(reloadDag).isPresent();

        assertThat(dag).isEqualTo(reloadDag.get());
    }


    @Test
    void should_fetch_leaf_node() {
        MethodDag dag = SimpleMethodDagFixture.sample();
        respository.save(dag);

        final List<MethodVertex> methodVertices = respository.fetchLeafNodes(dag.getOriginId());

        assertThat(methodVertices).containsExactlyInAnyOrderElementsOf(SimpleMethodDagFixture.getLeafNodes());
    }

}
