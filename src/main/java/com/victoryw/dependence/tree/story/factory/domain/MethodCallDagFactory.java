package com.victoryw.dependence.tree.story.factory.domain;

import com.victoryw.dependence.tree.story.factory.dependence.service.dto.MethodDagDto;

import java.util.List;
import java.util.stream.Collectors;

public class MethodCallDagFactory {
    public MethodDag create(MethodDagDto methodDagDto) {
        List<MethodVertex> vertexes = methodDagDto.getMethodNodeDtos()
                .stream()
                .map((dto) -> new MethodVertex(dto.getId(), dto.getTitle()))
                .collect(Collectors.toList());

        final MethodDag methodDag = new MethodDag(vertexes);

        methodDagDto.getMethodCallDtos().forEach((methodCallDto -> {
            methodDag.addEdge(methodCallDto.getA(), methodCallDto.getB());
        }));

        return methodDag;
    }
}
