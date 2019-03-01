package com.victoryw.dependence.tree.story.factory.provider.dependency.api.dto;

import com.victoryw.dependence.tree.story.factory.domain.MethodDag;
import com.victoryw.dependence.tree.story.factory.domain.MethodVertex;
import com.victoryw.dependence.tree.story.factory.provider.dependency.api.dto.MethodDependencyDto;

import java.util.List;
import java.util.stream.Collectors;

public class MethodCallDagFactory {
    public MethodDag create(MethodDependencyDto methodDependencyDto) {
        List<MethodVertex> vertexes = methodDependencyDto.getMethodNodeDtos()
                .stream()
                .map((dto) -> new MethodVertex(dto.getId(), dto.getTitle()))
                .collect(Collectors.toList());

        final MethodDag methodDag = new MethodDag(vertexes);

        methodDependencyDto.getMethodCallDtos().forEach((methodCallDto -> {
            methodDag.addEdge(methodCallDto.getA(), methodCallDto.getB());
        }));

        return methodDag;
    }
}
