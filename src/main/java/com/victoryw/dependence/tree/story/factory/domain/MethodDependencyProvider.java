package com.victoryw.dependence.tree.story.factory.domain;

import com.victoryw.dependence.tree.story.factory.domain.MethodDag;

import java.util.Optional;

public interface MethodDependencyProvider {
    Optional<MethodCallTreeNode> getMethodDependencies(String className, String methodName);

}
