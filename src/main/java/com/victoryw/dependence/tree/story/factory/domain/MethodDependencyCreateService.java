package com.victoryw.dependence.tree.story.factory.domain;
import java.util.List;
import java.util.Optional;

class MethodDependencyCreateService {
    private final MethodDependencyProvider methodDependencyProvider;

    MethodDependencyCreateService(MethodDependencyProvider methodDependencyProvider) {

        this.methodDependencyProvider = methodDependencyProvider;
    }

    Optional<MethodCallTreeNode> execute(String className, String methodName) {
        final Optional<MethodCallTreeNode> treeMaybe = methodDependencyProvider.getMethodDependencies(className, methodName);
        if(!treeMaybe.isPresent()) {
            throw new MethodCallTreeNotExistsException(className, methodName);
        }

        combineSubTree(treeMaybe.get());

        return treeMaybe;
    }

    private void combineSubTree(MethodCallTreeNode treeNodes) {
        List<MethodCallTreeNode> leafNodes = treeNodes.getLeftNodes();

        leafNodes.forEach(leaf -> {
            MethodVertex data = leaf.data();
            final Optional<MethodCallTreeNode> appendTreeMaybe = methodDependencyProvider.getMethodDependencies(data.getClassName(), data.getMethodName());
            if(! appendTreeMaybe.isPresent()) {
                return;
            }

            MethodCallTreeNode appendTree = appendTreeMaybe.get();

            if(appendTree.isLeaf()) {
                return;
            }

            appendTree.subtrees().forEach(leaf::add);

            combineSubTree(leaf);
        });
    }
}
