package com.victoryw.dependence.tree.story.factory.domain;

import com.victoryw.dependence.tree.story.factory.domain.MethodDag;
import com.victoryw.dependence.tree.story.factory.domain.MethodVertex;

import java.util.List;
import java.util.Optional;

public interface DependencyRepository {
    void save(MethodDag justNewDag);

    Optional<MethodDag> load(MethodVertex nodeId);

    List<MethodVertex> fetchLeafNodes(MethodVertex origin);
}
