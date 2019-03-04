package com.victoryw.dependence.tree.story.factory.fixtures;

import com.victoryw.dependence.tree.story.factory.provider.dependency.api.dto.MethodCallDto;
import com.victoryw.dependence.tree.story.factory.provider.dependency.api.dto.MethodDependencyDto;
import com.victoryw.dependence.tree.story.factory.provider.dependency.api.dto.MethodNodeDto;

import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;

public class MethodDependencyDtoFixture {

    private static MethodNodeDto node1 = MethodNodeDto.createMethodNode(UUID.randomUUID().toString(),"className.methodName");
    private static MethodNodeDto node12 = MethodNodeDto.createMethodNode(UUID.randomUUID().toString(), "className12.methodName12");
    private static MethodNodeDto node111 = MethodNodeDto.createMethodNode(UUID.randomUUID().toString(), "className111.methodName111");
    private static MethodNodeDto node11 = MethodNodeDto.createMethodNode(UUID.randomUUID().toString(),"className11.methodName11");
    private static MethodNodeDto node112 = MethodNodeDto.createMethodNode(UUID.randomUUID().toString(), "className112.methodName112");
    private static MethodCallDto edge11 = new MethodCallDto(node1.getId(), node11.getId());
    private static MethodCallDto edge12 = new MethodCallDto(node1.getId(), node12.getId());
    private static MethodCallDto edge111 = new MethodCallDto(node11.getId(), node111.getId());
    private static MethodCallDto edge112 = new MethodCallDto(node11.getId(), node112.getId());
    private static MethodCallDto edge122 = new MethodCallDto(node12.getId(), node112.getId());

    private static MethodNodeDto node1111 = MethodNodeDto.createMethodNode(UUID.randomUUID().toString(), "className1111.methodName1111");
    private static MethodCallDto edge1111 = new MethodCallDto(node111.getId(), node1111.getId());

    public static MethodDependencyDto root() {
        return new MethodDependencyDto().
                withNodes(Arrays.asList(node1, node11, node12, node111, node112)).
                withEdges(Arrays.asList(edge11, edge12, edge111, edge112, edge122));
    }

    public static MethodDependencyDto deps112() {
        return new MethodDependencyDto();
    }

    public static MethodDependencyDto deps111() {
        return new MethodDependencyDto().
                withNodes(Arrays.asList(node111, node1111)).
                withEdges(Collections.singletonList(edge1111));
    }

    public static MethodDependencyDto deps1111() {
        return new MethodDependencyDto().withNodes(Collections.singletonList(node1111));
    }

}
