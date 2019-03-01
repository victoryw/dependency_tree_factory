package com.victoryw.dependence.tree.story.factory;

import com.sun.tools.javac.util.List;
import com.victoryw.dependence.tree.story.factory.provider.dependency.api.dto.MethodCallDto;
import com.victoryw.dependence.tree.story.factory.provider.dependency.api.dto.MethodDependencyDto;
import com.victoryw.dependence.tree.story.factory.provider.dependency.api.dto.MethodNodeDto;

import java.util.UUID;

public class Fixture {
    public static MethodDependencyDto example() {
        final MethodNodeDto node1 = MethodNodeDto.createMethodNode(UUID.randomUUID().toString());
        final MethodNodeDto node11 = MethodNodeDto.createMethodNode(UUID.randomUUID().toString());
        final MethodNodeDto node12 = MethodNodeDto.createMethodNode(UUID.randomUUID().toString());
        final MethodNodeDto node111 = MethodNodeDto.createMethodNode(UUID.randomUUID().toString());
        final MethodNodeDto node112 = MethodNodeDto.createMethodNode(UUID.randomUUID().toString());
        final MethodCallDto edge11 = new MethodCallDto(node1.getId(), node11.getId());
        final MethodCallDto edge12 = new MethodCallDto(node1.getId(), node12.getId());
        final MethodCallDto edge111 = new MethodCallDto(node11.getId(), node111.getId());
        final MethodCallDto edge112 = new MethodCallDto(node11.getId(), node112.getId());
        final MethodCallDto edge122 = new MethodCallDto(node12.getId(), node112.getId());
        return new MethodDependencyDto().
                withNodes(List.of(node1, node11, node12, node111, node112)).
                withEdges(List.of(edge11, edge12, edge111, edge112, edge122));
    }
}
