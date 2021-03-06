package com.victoryw.dependence.tree.story.factory;

import com.victoryw.dependence.tree.story.factory.domain.MethodCallTreeNode;
import com.victoryw.dependence.tree.story.factory.domain.MethodCallTreeNodeGroup;
import com.victoryw.dependence.tree.story.factory.domain.MethodDependencyCreateService;
import com.victoryw.dependence.tree.story.factory.domain.MethodDependencySplitService;
import com.victoryw.dependence.tree.story.factory.provider.dependency.api.MethodDependencyProviderImplement;
import com.victoryw.dependence.tree.story.factory.provider.jira.api.JiraProviderImplement;

import java.util.Optional;
import java.util.Queue;

public class Application {

    private MethodDependencyCreateService createService;
    private MethodDependencySplitService splitService;
    private JiraProviderImplement jiraProviderImplement;

    public Application() {
        createService = new MethodDependencyCreateService(new MethodDependencyProviderImplement());
        splitService = new MethodDependencySplitService(3);
        jiraProviderImplement = new JiraProviderImplement();
    }

    public Queue<MethodCallTreeNodeGroup> split(String className, String methodName){
        final Optional<MethodCallTreeNode> startTrans = createService.execute(className, methodName);
        final Queue<MethodCallTreeNodeGroup> ad = startTrans.map(callTree -> splitService.execute(callTree))
                .orElseThrow(() -> new RuntimeException("AD"));
        jiraProviderImplement.createIssues(ad);
        return ad;

    }
}
