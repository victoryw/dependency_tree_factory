package com.victoryw.dependence.tree.story.factory.util;

import com.google.gson.Gson;
import com.victoryw.dependence.tree.story.factory.provider.dependency.api.dto.MethodDependencyDto;

public class GsonExtension {
    public static String toJson(MethodDependencyDto source) {
        Gson gson = new Gson();
        return gson.toJson(source);
    }
}
