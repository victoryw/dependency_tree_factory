package com.victoryw.dependence.tree.story.factory.domain;
import java.util.List;
import java.util.Optional;

class MethodDependencyCreateService {
    private final MethodDependencyProvider methodDependencyProvider;

    MethodDependencyCreateService(MethodDependencyProvider methodDependencyProvider) {

        this.methodDependencyProvider = methodDependencyProvider;
    }

    public Optional<MethodCallTreeNode> execute(String className, String methodName) {
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
            final Optional<MethodCallTreeNode> appendTree = methodDependencyProvider.getMethodDependencies(data.getClassName(), data.getMethodName());
            if(! appendTree.isPresent()) {
                return;
            }

            MethodCallTreeNode methodCallTreeNode = appendTree.get();

            if(methodCallTreeNode.isLeaf()) {
                return;
            }

            methodCallTreeNode.subtrees().forEach(leaf::add);

            combineSubTree(leaf);
        });
    }
}
