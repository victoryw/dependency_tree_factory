package com.victoryw.dependence.tree.story.factory.domain;

import java.util.List;
import java.util.Optional;

@Deprecated
public interface DependencyRepository {
    void save(MethodDag justNewDag);

    Optional<MethodDag> load(MethodVertex nodeId);

    List<MethodVertex> fetchLeafNodes(String originId);

    Optional<MethodVertex> loadVertexByTitle(String title);
}
