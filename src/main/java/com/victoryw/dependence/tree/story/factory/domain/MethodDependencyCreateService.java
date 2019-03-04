package com.victoryw.dependence.tree.story.factory.domain;

import com.scalified.tree.multinode.LinkedMultiTreeNode;

import java.util.Optional;

class MethodDependencyCreateService {
    private final MethodDependencyProvider methodDependencyProvider;
    private final DependencyRepository dependencyRepository;

    MethodDependencyCreateService(MethodDependencyProvider methodDependencyProvider,
                                  DependencyRepository dependencyRepository) {

        this.methodDependencyProvider = methodDependencyProvider;
        this.dependencyRepository = dependencyRepository;
    }

    public Optional<MethodCallTreeNode> execute(String className, String methodName) {
        final Optional<MethodCallTreeNode> methodDependencies = methodDependencyProvider.getMethodDependencies2(className, methodName);
        return methodDependencies;
    }
}
